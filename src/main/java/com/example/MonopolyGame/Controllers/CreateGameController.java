package com.example.MonopolyGame.Controllers;

//controller
import com.example.MonopolyGame.Models.Players;
import com.example.MonopolyGame.Service.GameService;
import com.example.MonopolyGame.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monopoly_game")
public class CreateGameController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public String createGame() {
        Players[] players = playerService.createPlayers();
        gameService.resetGamePoints();
        return "Game created and started with players:\n" + players[0] + "\n" + players[1]+ "\n";
    }
}