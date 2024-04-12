package com.example.MonopolyGame.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_points")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class GamePoint {
    @Id
    private int id;
    private String place;
    private int buyPrice;
    private int rentPrice;

    private int purchasedBy;
}
