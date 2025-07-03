package org.umm.paisesycapitalesbackend.utils.dto.response;

import org.umm.paisesycapitalesbackend.structure.model.enums.GameStatus;

public class GameResponse {
    private Long id;
    private GameStatus status;
    private Integer totalRounds;

    public GameResponse(Long id, GameStatus status, Integer totalRounds) {
        this.id = id;
        this.status = status;
        this.totalRounds = totalRounds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;
    }
}
