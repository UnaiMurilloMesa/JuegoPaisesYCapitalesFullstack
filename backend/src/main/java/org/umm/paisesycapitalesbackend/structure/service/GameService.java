package org.umm.paisesycapitalesbackend.structure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.umm.paisesycapitalesbackend.structure.model.Game;
import org.umm.paisesycapitalesbackend.structure.model.enums.GameStatus;
import org.umm.paisesycapitalesbackend.structure.repository.CountryRepository;
import org.umm.paisesycapitalesbackend.structure.repository.GameRepository;
import org.umm.paisesycapitalesbackend.utils.dto.request.GameStartRequest;
import org.umm.paisesycapitalesbackend.utils.dto.response.GameResponse;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CountryRepository countryRepository;

    public GameResponse startGame(GameStartRequest gameStartRequest) {
        Game game = new Game();
        game.setLanguage(gameStartRequest.getLanguage());
        game.setContinent(gameStartRequest.getContinent());
        game.setTotalRounds(gameStartRequest.getTotalRounds());
        game.setCurrentRound(0);
        game.setCorrectAnswers(0);
        game.setIncorrectAnswers(0);
        game.setStatus(GameStatus.STARTED);

        game = gameRepository.save(game);

        return new GameResponse(game.getId(), game.getStatus(), game.getTotalRounds());
    }
}
