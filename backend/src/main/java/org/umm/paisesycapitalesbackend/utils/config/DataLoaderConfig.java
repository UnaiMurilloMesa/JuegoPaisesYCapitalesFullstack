package org.umm.paisesycapitalesbackend.utils.config;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.umm.paisesycapitalesbackend.structure.model.Country;
import org.umm.paisesycapitalesbackend.structure.repository.CountryRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoaderConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderConfig.class);
    private static final String COUNTRIES_JSON_PATH = "countries.json";

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) {
        if (shouldLoadData()) {
            loadCountriesFromJson();
        } else {
            logger.info("Countries data already exists in database. Skipping data load.");
            logDatabaseStats();
        }
    }

    private boolean shouldLoadData() {
        long countryCount = countryRepository.count();
        logger.info("Found {} countries in database", countryCount);
        return countryCount == 0;
    }

    private void loadCountriesFromJson() {
        logger.info("Loading countries data from JSON file: {}", COUNTRIES_JSON_PATH);

        try {
            List<Country> countries = readCountriesFromFile();
            validateCountriesData(countries);
            saveCountriesToDatabase(countries);
            logLoadingSuccess(countries.size());
            logDatabaseStats();

        } catch (IOException e) {
            logger.error("Failed to read countries JSON file: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to load countries data from JSON file", e);
        } catch (Exception e) {
            logger.error("Unexpected error while loading countries: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load countries data", e);
        }
    }

    private List<Country> readCountriesFromFile() throws IOException {
        ClassPathResource resource = new ClassPathResource(COUNTRIES_JSON_PATH);

        if (!resource.exists()) {
            throw new IOException("Countries JSON file not found at: " + COUNTRIES_JSON_PATH);
        }

        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode countriesNode = rootNode.get("countries");

            if (countriesNode == null || !countriesNode.isArray()) {
                throw new IOException("'countries' array not found or invalid in JSON file");
            }

            return objectMapper.readerFor(new TypeReference<List<Country>>() {}).readValue(countriesNode);
        }
    }

    private void validateCountriesData(List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            throw new IllegalArgumentException("Countries list is empty or null");
        }

        logger.info("Validating {} countries from JSON", countries.size());

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);
            try {
                validateCountry(country, i);
            } catch (Exception e) {
                logger.error("Validation failed for country at index {}: {}", i, e.getMessage());
                throw new IllegalArgumentException("Invalid country data at index " + i, e);
            }
        }

        logger.info("All countries validated successfully");
    }

    private void validateCountry(Country country, int index) {
        if (country == null) {
            throw new IllegalArgumentException("Country is null");
        }

        if (isBlank(country.getNameEn()) || isBlank(country.getNameEs())) {
            throw new IllegalArgumentException("Country name is missing");
        }

        if (isBlank(country.getCode2()) || isBlank(country.getCode3())) {
            throw new IllegalArgumentException("Country code is missing for country: " + country.getNameEn());
        }

        if (country.getKm2() == null) {
            throw new IllegalArgumentException("Invalid area for country: " + country.getNameEn());
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void saveCountriesToDatabase(List<Country> countries) {
        logger.info("Saving {} countries to database", countries.size());

        try {
            List<Country> savedCountries = countryRepository.saveAll(countries);
            logger.info("Successfully saved {} countries to database", savedCountries.size());

        } catch (Exception e) {
            logger.error("Failed to save countries to database: {}", e.getMessage(), e);
            throw new RuntimeException("Database save operation failed", e);
        }
    }

    private void logLoadingSuccess(int count) {
        logger.info("Countries data loaded successfully!");
        logger.info("Total countries loaded: {}", count);
    }

    private void logDatabaseStats() {
        try {
            long totalCountries = countryRepository.count();
            List<String> continents = countryRepository.findDistinctContinentsEn();

            logger.info("Database Statistics:");
            logger.info("   - Total countries: {}", totalCountries);
            logger.info("   - Continents: {}", continents.size());

            for (String continent : continents) {
                long count = countryRepository.countByContinentEnIgnoreCase(continent);
                logger.info("   - {}: {} countries", continent, count);
            }

        } catch (Exception e) {
            logger.warn("Could not retrieve database statistics: {}", e.getMessage());
        }
    }
}
