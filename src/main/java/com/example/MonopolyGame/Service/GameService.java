package com.example.MonopolyGame.Service;

import com.example.MonopolyGame.Repositories.GamePointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GamePointsRepository gamePointsRepository;

    public void resetGamePoints() {
        for(int i = 1; i<=10; i++) {
            gamePointsRepository.getById(i).setPurchasedBy(0);
        }
    }
}
