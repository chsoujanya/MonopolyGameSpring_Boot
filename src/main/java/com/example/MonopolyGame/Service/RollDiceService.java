package com.example.MonopolyGame.Service;
//service
import com.example.MonopolyGame.Models.GamePoint;
import com.example.MonopolyGame.Models.Players;
import com.example.MonopolyGame.Repositories.GamePointsRepository;
import com.example.MonopolyGame.Repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RollDiceService {

    private final PlayersRepository playersRepository;
    private final GamePointsRepository gamePointsRepository;

    @Autowired
    public RollDiceService(PlayersRepository playersRepository, GamePointsRepository gamePointsRepository) {
        this.playersRepository = playersRepository;
        this.gamePointsRepository = gamePointsRepository;
    }

    public String rollDieAndLandOnPlace(int playerId) {
        // Roll two dice
        int dice1 = rollDice();
        int dice2 = rollDice();
        int totalDiceValue = dice1 + dice2;
        // Update player position based on dice roll
        Players updatedPlayer = updatePlayerPosition(playerId, totalDiceValue);
        String msg = updateAmount(playerId, updatedPlayer.getPosition());
        String winnerMsg = checkWinner();
        return "Player " + playerId + " rolled dice: " + dice1 + " and " + dice2 +
                ".\n" + "Player moved to property : " + updatedPlayer.getPosition() + ".\n" +
                msg + "\n" + winnerMsg;
    }

    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1; // Generates random number between 1 and 6
    }

    private Players updatePlayerPosition(int playerNumber, int diceValue) {
        Players player = playersRepository.findById(playerNumber).orElse(null);
        if (player != null)
        {
            int currentPosition = player.getPosition();
            int newPosition = (currentPosition + diceValue) % 10;
            if (newPosition == 0)
            {
                newPosition = 1;
            }
            player.setPosition(newPosition);
            incrementTurn(playerNumber);
            // If the player crosses start again
            if (currentPosition > newPosition) {
                player.setMoney(player.getMoney() + 200);
            }
            playersRepository.save(player);
            return player;
        }
        return null;
    }

    private void incrementTurn(int playerId) {
        Players currentPlayer = playersRepository.getById(playerId);
        int currentTurn = currentPlayer.getTurn();
        currentPlayer.setTurn(currentTurn + 1);
        playersRepository.save(currentPlayer);
    }

    private String updateAmount(int playerId, int placeId) {
        Players currentPlayer = playersRepository.getById(playerId);
        GamePoint placeLanded = gamePointsRepository.getById(placeId);
        String msg = "";
        int purchasedById = placeLanded.getPurchasedBy();
        // checking if the property belong to the player
        if (playerId != purchasedById)
        {
            if (placeLanded.getPurchasedBy() != 0)
            {
                //current player landed on a property purchased by another player and deduct the rent
                currentPlayer.setMoney(currentPlayer.getMoney() - placeLanded.getRentPrice());
                Players anotherPlayer = playersRepository.getById(purchasedById);
                anotherPlayer.setMoney(anotherPlayer.getMoney() + placeLanded.getRentPrice());
                playersRepository.save(anotherPlayer);
                msg += "Player paid rent of amount : " + placeLanded.getRentPrice() + ".\n";
                msg += "Current amount with the player is : " + currentPlayer.getMoney() + ".\n";
                if (currentPlayer.getMoney() < 0)
                {
                    msg += "Player has no money left.\n" + "Player " + anotherPlayer.getId() + " Won";
                }
            }

            else
            {
                //player should purchase it
                currentPlayer.setMoney(currentPlayer.getMoney() - placeLanded.getBuyPrice());
                placeLanded.setPurchasedBy(currentPlayer.getId());
                msg += "Player purchased property of amount : " + placeLanded.getBuyPrice() + ".\n";
                msg += "Current amount with the player is : " + currentPlayer.getMoney() + ".\n";
            }
            playersRepository.save(currentPlayer);
        }
        else
        {
            msg += "The property is owned by the player already.";
        }

        return msg;
    }

    private String checkWinner() {
        List<Players> players = playersRepository.findAll();
        if (players.get(0).getTurn() + players.get(1).getTurn() >= 50)
        {
            if (players.get(0).getMoney() > players.get(1).getMoney())
            {
                players.get(0).setWin(1);
                playersRepository.saveAll(players);
                return "Player 1 is the winner with the amount " + players.get(0).getMoney();
            }
            else
            {
                players.get(1).setWin(1);
                playersRepository.saveAll(players);
                return "Player 2 is the winner with the amount " + players.get(1).getMoney();
            }
        }
        return "";
    }

    public void checkStop()
    {
        List<Players> players = playersRepository.findAll();
        if (players.get(0).getTurn() + players.get(1).getTurn() >= 50) {
            System.exit(0);
        }
    }
}
