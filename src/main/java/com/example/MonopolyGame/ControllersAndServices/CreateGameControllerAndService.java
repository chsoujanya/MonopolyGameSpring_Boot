package com.example.MonopolyGame.ControllersAndServices;


import com.example.MonopolyGame.Models.Players;
import com.example.MonopolyGame.Repositories.GamePointsRepository;
import com.example.MonopolyGame.Repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/monopoly_game")

public class CreateGameControllerAndService {


    @Autowired

    PlayersRepository playersRepository;


    @Autowired
    GamePointsRepository gamePointsRepository;



    @PostMapping("/create")

    public String createGame()
    {

        System.out.println("Players Table created");
        playersRepository.deleteAll();
        Players newPlayer1 = new Players(1, 1000, 0 , 0, 0);
        Players newPlayer2 = new Players(2, 1000, 0, 0, 0);

        for(int i = 1; i<=10; i++)
        {
            gamePointsRepository.getById(i).setPurchasedBy(0);
        }




        playersRepository.save(newPlayer1);
        playersRepository.save(newPlayer2);

        return "Game created and started with players:\n" + newPlayer1 +"\n" + newPlayer2 + "\n";

    }
}
