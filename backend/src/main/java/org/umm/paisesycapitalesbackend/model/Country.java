package org.umm.paisesycapitalesbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@Entity
@Table(name = "countries", indexes = {
        @Index(name = "idx_continent_en", columnList = "continent_en"),
        @Index(name = "idx_continent_es", columnList = "continent_es")
})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "English name is required")
    @Size(max = 100, message = "English name must not exceed 100 characters")
    @Column(name = "name_en", nullable = false, length = 100)
    @JsonProperty("name_en")
    private String nameEn;

    @NotBlank(message = "Spanish name is required")
    @Size(max = 100, message = "Spanish name must not exceed 100 characters")
    @Column(name = "name_es", nullable = false, length = 100)
    @JsonProperty("name_es")
    private String nameEs;

    @NotBlank(message = "English continent is required")
    @Size(max = 50, message = "English continent must not exceed 50 characters")
    @Column(name = "continent_en", nullable = false, length = 50)
    @JsonProperty("continent_en")
    private String continentEn;

    @NotBlank(message = "Spanish continent is required")
    @Size(max = 50, message = "Spanish continent must not exceed 50 characters")
    @Column(name = "continent_es", nullable = false, length = 50)
    @JsonProperty("continent_es")
    private String continentEs;

    @NotBlank(message = "English capital is required")
    @Size(max = 100, message = "English capital must not exceed 100 characters")
    @Column(name = "capital_en", nullable = false, length = 100)
    @JsonProperty("capital_en")
    private String capitalEn;

    @NotBlank(message = "Spanish capital is required")
    @Size(max = 100, message = "Spanish capital must not exceed 100 characters")
    @Column(name = "capital_es", nullable = false, length = 100)
    @JsonProperty("capital_es")
    private String capitalEs;

    @NotBlank(message = "Dial code is required")
    @Size(max = 10, message = "Dial code must not exceed 10 characters")
    @Column(name = "dial_code", nullable = false, length = 10)
    @JsonProperty("dial_code")
    private String dialCode;

    @NotBlank(message = "Country code 2 is required")
    @Size(min = 2, max = 2, message = "Country code 2 must be exactly 2 characters")
    @Column(name = "code_2", nullable = false, length = 2)
    @JsonProperty("code_2")
    private String code2;

    @NotBlank(message = "Country code 3 is required")
    @Size(min = 3, max = 3, message = "Country code 3 must be exactly 3 characters")
    @Column(name = "code_3", nullable = false, length = 3)
    @JsonProperty("code_3")
    private String code3;

    @NotBlank(message = "TLD is required")
    @Size(max = 10, message = "TLD must not exceed 10 characters")
    @Column(name = "tld", nullable = false, length = 10)
    @JsonProperty("tld")
    private String tld;

    @NotNull(message = "Area in km2 is required")
    @Positive(message = "Area in km2 must be positive")
    @Column(name = "km2", nullable = false)
    @JsonProperty("km2")
    private Double km2;

    @NotBlank(message = "Emoji is required")
    @Size(max = 20, message = "Emoji must not exceed 20 characters")
    @Column(name = "emoji", nullable = false, length = 20)
    @JsonProperty("emoji")
    private String emoji;

    public Country() {}

    public Country(String nameEn, String nameEs, String continentEn, String continentEs,
                   String capitalEn, String capitalEs, String dialCode, String code2,
                   String code3, String tld, Double km2, String emoji) {
        this.nameEn = nameEn;
        this.nameEs = nameEs;
        this.continentEn = continentEn;
        this.continentEs = continentEs;
        this.capitalEn = capitalEn;
        this.capitalEs = capitalEs;
        this.dialCode = dialCode;
        this.code2 = code2;
        this.code3 = code3;
        this.tld = tld;
        this.km2 = km2;
        this.emoji = emoji;
    }

    public String getName(String language) {
        return "es".equalsIgnoreCase(language) ? nameEs : nameEn;
    }

    public String getCapital(String language) {
        return "es".equalsIgnoreCase(language) ? capitalEs : capitalEn;
    }

    public String getContinent(String language) {
        return "es".equalsIgnoreCase(language) ? continentEs : continentEn;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameEs() { return nameEs; }
    public void setNameEs(String nameEs) { this.nameEs = nameEs; }

    public String getContinentEn() { return continentEn; }
    public void setContinentEn(String continentEn) { this.continentEn = continentEn; }

    public String getContinentEs() { return continentEs; }
    public void setContinentEs(String continentEs) { this.continentEs = continentEs; }

    public String getCapitalEn() { return capitalEn; }
    public void setCapitalEn(String capitalEn) { this.capitalEn = capitalEn; }

    public String getCapitalEs() { return capitalEs; }
    public void setCapitalEs(String capitalEs) { this.capitalEs = capitalEs; }

    public String getDialCode() { return dialCode; }
    public void setDialCode(String dialCode) { this.dialCode = dialCode; }

    public String getCode2() { return code2; }
    public void setCode2(String code2) { this.code2 = code2; }

    public String getCode3() { return code3; }
    public void setCode3(String code3) { this.code3 = code3; }

    public String getTld() { return tld; }
    public void setTld(String tld) { this.tld = tld; }

    public Double getKm2() { return km2; }
    public void setKm2(Double km2) { this.km2 = km2; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code2, country.code2) &&
                Objects.equals(code3, country.code3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code2, code3);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameEs='" + nameEs + '\'' +
                ", code2='" + code2 + '\'' +
                ", code3='" + code3 + '\'' +
                '}';
    }
}
