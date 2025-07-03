package org.umm.paisesycapitalesbackend.utils.dto.request;

import org.umm.paisesycapitalesbackend.structure.model.enums.Continent;
import org.umm.paisesycapitalesbackend.structure.model.enums.Language;

public class GameStartRequest {
    private Language language;
    private Continent continent;
    private int totalRounds;

    public Language getLanguage() {
        return language;
    }

    public Continent getContinent() {
        return continent;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }
}
