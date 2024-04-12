package com.example.MonopolyGame.Repositories;


import com.example.MonopolyGame.Models.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PlayersRepository extends JpaRepository<Players, Integer> {
}
