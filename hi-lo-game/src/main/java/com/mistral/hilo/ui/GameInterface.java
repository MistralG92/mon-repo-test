package com.mistral.hilo.ui;

import com.mistral.hilo.game.GameConfig;

public abstract class GameInterface {

    public abstract void displayWelcome();

    public abstract int getMainMenuChoice();

    public abstract GameConfig setupGameConfig();

    public abstract String getPlayerName(int playerNumber);

    public abstract String getPlayerGuess(String playerName, String currentCard, String opponentCard);

    public abstract boolean askNewRound();

    public abstract void cleanup();

    // Méthodes communes pour affichage
    public abstract void displayRoundResult(String player1, String player2,
            String card1, String card2,
            boolean player1Correct, boolean player2Correct,
            int score1, int score2);

    public void displayGameWinner(String winner, int finalScore) {
        System.out.println("\n🏆 VICTOIRE DE " + winner.toUpperCase() + " ! 🏆");
        System.out.println("Score final : " + finalScore + " points");
    }
}
