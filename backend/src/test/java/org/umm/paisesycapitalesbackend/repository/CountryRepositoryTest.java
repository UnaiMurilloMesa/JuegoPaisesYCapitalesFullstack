package org.umm.paisesycapitalesbackend.repository;

import org.umm.paisesycapitalesbackend.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Country Repository Tests")
class CountryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CountryRepository countryRepository;

    private Country spain;
    private Country france;
    private Country germany;
    private Country argentina;

    @BeforeEach
    void setUp() {
        spain = new Country(
                "Spain", "España",
                "Europe", "Europa",
                "Madrid", "Madrid",
                "+34", "ES", "ESP",
                ".es", 505990.0, "🇪🇸"
        );

        france = new Country(
                "France", "Francia",
                "Europe", "Europa",
                "Paris", "París",
                "+33", "FR", "FRA",
                ".fr", 643801.0, "🇫🇷"
        );

        germany = new Country(
                "Germany", "Alemania",
                "Europe", "Europa",
                "Berlin", "Berlín",
                "+49", "DE", "DEU",
                ".de", 357114.0, "🇩🇪"
        );

        argentina = new Country(
                "Argentina", "Argentina",
                "South America", "América del Sur",
                "Buenos Aires", "Buenos Aires",
                "+54", "AR", "ARG",
                ".ar", 2780400.0, "🇦🇷"
        );

        entityManager.persistAndFlush(spain);
        entityManager.persistAndFlush(france);
        entityManager.persistAndFlush(germany);
        entityManager.persistAndFlush(argentina);
    }

    @Test
    @DisplayName("Should find country by 2-letter code")
    void shouldFindCountryByCode2() {
        Optional<Country> result = countryRepository.findByCode2("ES");

        assertThat(result).isPresent();
        assertThat(result.get().getNameEn()).isEqualTo("Spain");
        assertThat(result.get().getCode2()).isEqualTo("ES");
    }

    @Test
    @DisplayName("Should find country by 3-letter code")
    void shouldFindCountryByCode3() {
        Optional<Country> result = countryRepository.findByCode3("FRA");

        assertThat(result).isPresent();
        assertThat(result.get().getNameEn()).isEqualTo("France");
        assertThat(result.get().getCode3()).isEqualTo("FRA");
    }

    @Test
    @DisplayName("Should return empty when country code not found")
    void shouldReturnEmptyWhenCountryCodeNotFound() {
        Optional<Country> result = countryRepository.findByCode2("XX");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find countries by continent in English")
    void shouldFindCountriesByContinentEn() {
        List<Country> europeanCountries = countryRepository.findByContinentEnIgnoreCase("Europe");

        assertThat(europeanCountries).hasSize(3);
        assertThat(europeanCountries)
                .extracting(Country::getNameEn)
                .containsExactlyInAnyOrder("Spain", "France", "Germany");
    }

    @Test
    @DisplayName("Should find countries by continent in Spanish")
    void shouldFindCountriesByContinentEs() {
        List<Country> europeanCountries = countryRepository.findByContinentEsIgnoreCase("europa");

        assertThat(europeanCountries).hasSize(3);
        assertThat(europeanCountries)
                .extracting(Country::getNameEs)
                .containsExactlyInAnyOrder("España", "Francia", "Alemania");
    }

    @Test
    @DisplayName("Should find random countries")
    void shouldFindRandomCountries() {
        List<Country> randomCountries = countryRepository.findRandomCountries(2);

        assertThat(randomCountries).hasSize(2);
        assertThat(randomCountries).allMatch(country -> country.getId() != null);
    }

    @Test
    @DisplayName("Should find random countries by continent")
    void shouldFindRandomCountriesByContinent() {
        List<Country> randomEuropeanCountries = countryRepository.findRandomCountriesByContinent("Europe", 2);

        assertThat(randomEuropeanCountries).hasSize(2);
        assertThat(randomEuropeanCountries)
                .allMatch(country -> "Europe".equals(country.getContinentEn()));
    }

    @Test
    @DisplayName("Should find country by capital ignoring case and spaces - English")
    void shouldFindCountryByCapitalIgnoreCaseAndSpacesEn() {
        List<Country> countries = countryRepository.findByCapitalEnIgnoreCaseAndSpaces("buenos aires");

        assertThat(countries).hasSize(1);
        assertThat(countries.get(0).getNameEn()).isEqualTo("Argentina");
    }

    @Test
    @DisplayName("Should find country by capital ignoring case and spaces - Spanish")
    void shouldFindCountryByCapitalIgnoreCaseAndSpacesEs() {
        List<Country> countries = countryRepository.findByCapitalEsIgnoreCaseAndSpaces("PARÍS");

        assertThat(countries).hasSize(1);
        assertThat(countries.get(0).getNameEs()).isEqualTo("Francia");
    }

    @Test
    @DisplayName("Should get distinct continents in English")
    void shouldGetDistinctContinentsEn() {
        List<String> continents = countryRepository.findDistinctContinentsEn();

        assertThat(continents).hasSize(2);
        assertThat(continents).containsExactlyInAnyOrder("Europe", "South America");
    }

    @Test
    @DisplayName("Should get distinct continents in Spanish")
    void shouldGetDistinctContinentsEs() {
        List<String> continents = countryRepository.findDistinctContinentsEs();

        assertThat(continents).hasSize(2);
        assertThat(continents).containsExactlyInAnyOrder("Europa", "América del Sur");
    }

    @Test
    @DisplayName("Should count countries by continent")
    void shouldCountCountriesByContinent() {
        long europeanCount = countryRepository.countByContinentEnIgnoreCase("Europe");
        long southAmericanCount = countryRepository.countByContinentEnIgnoreCase("South America");

        assertThat(europeanCount).isEqualTo(3);
        assertThat(southAmericanCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Should check if capital exists")
    void shouldCheckIfCapitalExists() {
        assertThat(countryRepository.existsByCapitalEnIgnoreCase("Madrid")).isTrue();
        assertThat(countryRepository.existsByCapitalEsIgnoreCase("París")).isTrue();
        assertThat(countryRepository.existsByCapitalEnIgnoreCase("NonExistentCity")).isFalse();
    }

    @Test
    @DisplayName("Should handle case insensitive continent search")
    void shouldHandleCaseInsensitiveContinentSearch() {
        List<Country> countries1 = countryRepository.findByContinentEnIgnoreCase("EUROPE");
        List<Country> countries2 = countryRepository.findByContinentEnIgnoreCase("europe");
        List<Country> countries3 = countryRepository.findByContinentEnIgnoreCase("Europe");

        assertThat(countries1).hasSize(3);
        assertThat(countries2).hasSize(3);
        assertThat(countries3).hasSize(3);

        assertThat(countries1).containsExactlyInAnyOrderElementsOf(countries2);
        assertThat(countries2).containsExactlyInAnyOrderElementsOf(countries3);
    }
}