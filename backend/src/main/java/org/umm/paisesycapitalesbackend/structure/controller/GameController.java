package org.umm.paisesycapitalesbackend.structure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umm.paisesycapitalesbackend.structure.service.GameService;
import org.umm.paisesycapitalesbackend.utils.dto.request.GameStartRequest;
import org.umm.paisesycapitalesbackend.utils.dto.response.GameResponse;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(@RequestBody GameStartRequest gameStartRequest) {
        GameResponse gameResponse = gameService.startGame(gameStartRequest);
        return new ResponseEntity<>(gameResponse, HttpStatus.OK);
    }
}
