package org.umm.paisesycapitalesbackend.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.umm.paisesycapitalesbackend.structure.model.Country;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    // Buscar por código de país
    Optional<Country> findByCode2(String code2);
    Optional<Country> findByCode3(String code3);

    // Filtros por continente
    List<Country> findByContinentEnIgnoreCase(String continent);
    List<Country> findByContinentEsIgnoreCase(String continent);

    // Consultas para el juego - obtener países aleatorios
    @Query(value = "SELECT * FROM countries WHERE continent_en = :continent ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true)
    List<Country> findRandomCountriesByContinent(@Param("continent") String continent,
                                                 @Param("limit") int limit);

    @Query(value = "SELECT * FROM countries ORDER BY RANDOM() LIMIT :limit",
            nativeQuery = true)
    List<Country> findRandomCountries(@Param("limit") int limit);

    // Búsquedas case-insensitive para validación de respuestas
    @Query("SELECT c FROM Country c WHERE " +
            "LOWER(REPLACE(REPLACE(c.capitalEn, ' ', ''), '-', '')) = LOWER(REPLACE(REPLACE(:capital, ' ', ''), '-', ''))")
    List<Country> findByCapitalEnIgnoreCaseAndSpaces(@Param("capital") String capital);

    @Query("SELECT c FROM Country c WHERE " +
            "LOWER(REPLACE(REPLACE(c.capitalEs, ' ', ''), '-', '')) = LOWER(REPLACE(REPLACE(:capital, ' ', ''), '-', ''))")
    List<Country> findByCapitalEsIgnoreCaseAndSpaces(@Param("capital") String capital);

    // Obtener continentes únicos
    @Query("SELECT DISTINCT c.continentEn FROM Country c ORDER BY c.continentEn")
    List<String> findDistinctContinentsEn();

    @Query("SELECT DISTINCT c.continentEs FROM Country c ORDER BY c.continentEs")
    List<String> findDistinctContinentsEs();

    // Contar países por continente
    long countByContinentEnIgnoreCase(String continent);
    long countByContinentEsIgnoreCase(String continent);

    // Verificar si existe un país con cierta capital
    boolean existsByCapitalEnIgnoreCase(String capital);
    boolean existsByCapitalEsIgnoreCase(String capital);
}
