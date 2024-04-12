package com.example.MonopolyGame.Controllers;

import com.example.MonopolyGame.Service.RollDiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roll-die")
public class RollDiceController {

    private final RollDiceService rollDiceService;

    @Autowired
    public RollDiceController(RollDiceService rollDiceService) {
        this.rollDiceService = rollDiceService;
    }

    @PostMapping("/p1")
    public String rollDieAndLandOnPlaceForPlayer1() {
        rollDiceService.checkStop();
        return rollDiceService.rollDieAndLandOnPlace(1);
    }

    @PostMapping("/p2")
    public String rollDieAndLandOnPlaceForPlayer2() {
        rollDiceService.checkStop();
        return rollDiceService.rollDieAndLandOnPlace(2);
    }
}
