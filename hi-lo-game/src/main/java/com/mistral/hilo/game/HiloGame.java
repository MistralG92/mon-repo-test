package com.mistral.hilo.game;

import com.mistral.hilo.player.Player;
import com.mistral.hilo.game.Manche;
import com.mistral.hilo.ui.GameInterface;

public class HiloGame {
    private final GameInterface gameInterface;
    private GameConfig config;
    private Player player1;
    private Player player2;
    private boolean gameRunning = true;

    public HiloGame(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public HiloGame() {
        this(new com.mistral.hilo.ui.console.GameInterfaceConsole());
    }

    public void start() {
        gameInterface.displayWelcome();

        while (gameRunning) {
            int choice = gameInterface.getMainMenuChoice();

            switch (choice) {
                case 1 -> startNewGame();
                case 2 -> quit();
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void startNewGame() {
        // Configuration de la partie
        config = gameInterface.setupGameConfig();
        if (config == null)
            return; // Retour au menu si annule

        // Initialisation des joueurs
        setupPlayers();

        // Demarrage des manches
        playGame();
    }

    private void setupPlayers() {
        String name1 = gameInterface.getPlayerName(1);
        String name2 = gameInterface.getPlayerName(2);

        player1 = new Player(name1);
        player2 = new Player(name2);

        System.out.println("\n✅ Joueurs inscrits :");
        System.out.println("- " + player1.getName());
        System.out.println("- " + player2.getName());
    }

    private void playGame() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEBUT DE LA PARTIE");
        System.out.println("Premier a " + config.getTargetScore() + " points gagne !");
        System.out.println("=".repeat(50));

        int mancheNumber = 1;

        while (!isGameFinished()) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MANCHE " + mancheNumber);
            System.out.println("-".repeat(30));

            // Preparation des decks pour cette manche
            player1.initializeForNewRound(config.getCardsPerRound());
            player2.initializeForNewRound(config.getCardsPerRound());

            // Jouer la manche
            Manche currentManche = new Manche(player1, player2, gameInterface);
            currentManche.playManche(config.getCardsPerRound());

            // Afficher les scores actuels
            showCurrentScores();

            mancheNumber++;
        }

        // Fin de partie
        showGameResults();
    }

    private boolean isGameFinished() {
        return player1.getScore() >= config.getTargetScore() ||
                player2.getScore() >= config.getTargetScore();
    }

    private void showCurrentScores() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("SCORES ACTUELS");
        System.out.println("=".repeat(30));
        gameInterface.displayRoundResult(
                player1.getName(), player2.getName(),
                player1.getCurrentCard().toString(), player2.getCurrentCard().toString(),
                true, false, player1.getScore(), player2.getScore());

        int remaining1 = config.getTargetScore() - player1.getScore();
        int remaining2 = config.getTargetScore() - player2.getScore();

        if (remaining1 > 0) {
            System.out.println(player1.getName() + " - Reste " + remaining1 + " points");
        }
        if (remaining2 > 0) {
            System.out.println(player2.getName() + " - Reste " + remaining2 + " points");
        }
    }

    private void showGameResults() {
        Player winner = determineWinner();

        if (winner != null) {
            gameInterface.displayGameWinner(winner.getName(), winner.getScore());
        } else {
            System.out.println("🤝 ÉGALITÉ PARFAITE !");
        }
    }

    private Player determineWinner() {
        if (player1.getScore() >= config.getTargetScore() &&
                player2.getScore() >= config.getTargetScore()) {
            return player1.getScore() > player2.getScore() ? player1
                    : player2.getScore() > player1.getScore() ? player2 : null;
        }
        return player1.getScore() >= config.getTargetScore() ? player1 : player2;
    }

    private void quit() {
        System.out.println("\n👋 Merci d'avoir joué !");
        gameRunning = false;
        gameInterface.cleanup(); // ← Nettoyage interface
    }
}
