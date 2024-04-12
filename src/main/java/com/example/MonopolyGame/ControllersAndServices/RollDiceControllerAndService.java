package com.example.MonopolyGame.ControllersAndServices;


import com.example.MonopolyGame.Models.GamePoint;
import com.example.MonopolyGame.Models.Players;
import com.example.MonopolyGame.Repositories.GamePointsRepository;
import com.example.MonopolyGame.Repositories.PlayersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/roll-die")
@AllArgsConstructor
@NoArgsConstructor


public class RollDiceControllerAndService {

    private int currentPlayer = 1;





    @Autowired
    private GamePointsRepository gamePointRepository;

    @Autowired
    public PlayersRepository playersRepository;

    @PostMapping("/p1")
    public String rollDieAndLandOnPlaceForPlayer1() {
       checkStop();




        return rollDieAndLandOnPlace(1);
    }

    @PostMapping("/p2")
    public String rollDieAndLandOnPlaceForPlayer2() {
        checkStop();

        return rollDieAndLandOnPlace(2);
    }

    int check = 0;

    public String rollDieAndLandOnPlace(int playerId) {

        // Roll two dice
        int dice1 = rollDice();
        int dice2 = rollDice();


        int totalDiceValue = dice1 + dice2;

        // Update player position based on dice roll
        Players updatedPlayer = updatePlayerPosition(playerId, totalDiceValue);



        // Switch players after each turn
        currentPlayer = (currentPlayer == 1) ? 2 : 1;


         //amount updated
         String msg = updateAmount(playerId, updatedPlayer.getPosition());
        String winnerMsg = checkWinner();




        return "Player " + playerId + " rolled dice: " + dice1 + " and " + dice2 +
                ".\n"+ "Player moved to property : " + updatedPlayer.getPosition()+".\n"+
                msg+"\n"+winnerMsg;
    }

    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1; // Generates random number between 1 and 6
    }

    private Players updatePlayerPosition(int playerNumber, int diceValue) {


        Players player = playersRepository.findById(playerNumber).orElse(null);

        int current_position = player.getPosition();
        if (player != null) {
            int currentPosition = player.getPosition();
            int newPosition = (currentPosition + diceValue) % 10;
            if(newPosition == 0)
            {
                newPosition = 1;
            }
            player.setPosition(newPosition);
            incrementTurn(playerNumber);
            if(current_position > newPosition )
            {
                player.setMoney(player.getMoney() + 200);
            }
            playersRepository.save(player);
            return player;
        }
        return null;
    }

    private void incrementTurn(int player_id)
    {
        Players current_player = playersRepository.getById(player_id);
        int currentTurn = playersRepository.getById(player_id).getTurn();
        int turn = currentTurn + 1;
        current_player.setTurn(turn);
        playersRepository.save(current_player);
    }

    private String updateAmount(int player_id, int place_id)
    {
        Players current_player = playersRepository.getById(player_id);


        GamePoint place_landed = gamePointRepository.getById(place_id);

        String msg = "";


        int purchasedById = place_landed.getPurchasedBy();
        if(player_id != purchasedById)

        {
            if(place_landed.getPurchasedBy()!=0)
            {
                //current player landed on a property purchased by another player and deduct the rent
                current_player.setMoney(current_player.getMoney() - place_landed.getRentPrice());
                Players another_player = playersRepository.getById(purchasedById);
                another_player.setMoney(another_player.getMoney()+place_landed.getRentPrice());
                playersRepository.save(another_player);

                msg += "Player paid rent of amount : "+place_landed.getRentPrice() + ".\n";
                msg += "Current amount with the player is : " + current_player.getMoney() + ".\n";

                if(current_player.getMoney() < 0)
                {
                    msg+="Player has no money left.\n"+"Player "+another_player.getId()+" Won";

                }

            }
            else
            {
                //player should purchase it
                current_player.setMoney(current_player.getMoney() - place_landed.getBuyPrice());
                place_landed.setPurchasedBy(current_player.getId());
                msg += "Player purchased property of amount : "+place_landed.getBuyPrice() + ".\n";
                msg += "Current amount with the player is : " + current_player.getMoney() + ".\n";

            }

            playersRepository.save(current_player);

        }
        else
        {
            msg+="The property is owned by the player already.";
        }

        String msg1 = checkWinner();
        return msg + msg1;
    }

    public String checkWinner()
    {

        List<Players> players = playersRepository.findAll();
        if(players.get(0).getTurn() + players.get(1).getTurn() >= 50)
        {

            if(players.get(0).getMoney() > players.get(1).getMoney())
            {
                check = 1;
                players.get(0).setWin(1);
                playersRepository.saveAll(players);

                return "Player 1 is the winner with the amount "+players.get(0).getMoney();
            }
            else
            {
                check = 1;
                players.get(1).setWin(1);
                playersRepository.saveAll(players);
                return "Player 2 is the winner with the amount "+players.get(1).getMoney();
            }


        }

        return "";




    }

    public void checkStop()
    {

        List<Players> players = playersRepository.findAll();
        if(players.get(0).getTurn() + players.get(1).getTurn() >=  50)
        {
            System.exit(0);
        }

    }

}
