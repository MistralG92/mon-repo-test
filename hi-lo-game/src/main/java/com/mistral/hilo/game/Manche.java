package com.mistral.hilo.game;

import com.mistral.hilo.player.Player;
import com.mistral.hilo.ui.GameInterface;

public class Manche {
    private Player player1;
    private Player player2;
    private GameInterface gameInterface;
    private int currentRound;

    public Manche(Player player1, Player player2, GameInterface gameInterface) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameInterface = gameInterface;
        this.currentRound = 0;
    }

    public void playManche(int cardsPerRound) {
        currentRound++;
        System.out.println("\n" + "=".repeat(30));
        System.out.println("MANCHE " + currentRound);
        System.out.println("=".repeat(30));

        // Initialiser les decks et distribuer les cartes
        player1.initializeForNewRound(cardsPerRound);
        player2.initializeForNewRound(cardsPerRound);

        // Jouer tous les tours de la manche
        while (player1.hasNextCard() && player2.hasNextCard()) {
            playOneTurn();
        }

        System.out.println("Fin de manche - " + player1 + " | " + player2);
    }

    private void playOneTurn() {
        // Tour du Player1
        String guess1 = gameInterface.getPlayerGuess(
                player1.getName(),
                player1.getCurrentCard().toString(),
                player2.getCurrentCard().toString());

        // Tour du Player2
        String guess2 = gameInterface.getPlayerGuess(
                player2.getName(),
                player2.getCurrentCard().toString(),
                player1.getCurrentCard().toString());

        // Revelation et evaluation
        evaluateRound(guess1, guess2);
    }

    private void evaluateRound(String guess1, String guess2) {
        boolean player1Correct = evaluateGuess(player1, guess1);
        boolean player2Correct = evaluateGuess(player2, guess2);

        // Logique de points : seulement si UN SEUL a raison
        if (player1Correct && !player2Correct) {
            player1.addPoint();
            System.out.println(player1.getName() + " gagne 1 point !");
        } else if (!player1Correct && player2Correct) {
            player2.addPoint();
            System.out.println(player2.getName() + " gagne 1 point !");
        } else if (player1Correct && player2Correct) {
            System.out.println("Les deux ont raison - Aucun point attribué");
        } else {
            System.out.println("Les deux se sont trompés - Aucun point attribué");
        }

        System.out.println("Scores actuels : " + player1 + " | " + player2);
    }

    private boolean evaluateGuess(Player player, String guess) {
        var currentCard = player.getCurrentCard();
        var nextCard = player.getNextCard(); 

        System.out.println(player.getName() + " : " +
                currentCard + " -> " + nextCard);

        boolean isHigher = nextCard.getValue() >= currentCard.getValue();
        boolean guessedHigher = guess.equals("h");

        boolean correct = (isHigher && guessedHigher) || (!isHigher && !guessedHigher);

        System.out.println(player.getName() + " " +
                (correct ? "CORRECT" : "INCORRECT"));

        return correct;
    }
}