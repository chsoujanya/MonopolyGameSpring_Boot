package com.example.MonopolyGame.Repositories;

import com.example.MonopolyGame.Models.GamePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface GamePointsRepository extends JpaRepository<GamePoint, Integer> {
}
