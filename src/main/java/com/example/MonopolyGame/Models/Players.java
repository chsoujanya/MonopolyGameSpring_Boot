package com.example.MonopolyGame.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Entity
@Table( name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Data


public class Players {

    @Id
    private int id;
    private int money;
    private int position;
    private int turn;

    private int win;
}
