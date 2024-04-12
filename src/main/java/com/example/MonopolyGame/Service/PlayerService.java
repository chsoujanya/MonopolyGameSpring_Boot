package com.example.MonopolyGame.Service;

import com.example.MonopolyGame.Models.Players;
import com.example.MonopolyGame.Repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayersRepository playersRepository;

    public Players[] createPlayers() {
        System.out.println("Players Table created");
        playersRepository.deleteAll();
        Players newPlayer1 = new Players(1, 1000, 0 , 0, 0);
        Players newPlayer2 = new Players(2, 1000, 0, 0, 0);
        playersRepository.save(newPlayer1);
        playersRepository.save(newPlayer2);
        return new Players[]{newPlayer1, newPlayer2};
    }
}
