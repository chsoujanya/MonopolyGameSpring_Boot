package com.example.MonopolyGame.Service;
//service
import com.example.MonopolyGame.Models.GamePoint;
import com.example.MonopolyGame.Repositories.GamePointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePointService {

    @Autowired
    private GamePointsRepository gamePointsRepository;

    public GamePoint getGamePointById(int id) {
        return gamePointsRepository.getById(id);
    }

    public void updateGamePoint(GamePoint gamePoint) {
        gamePointsRepository.save(gamePoint);
    }
}

