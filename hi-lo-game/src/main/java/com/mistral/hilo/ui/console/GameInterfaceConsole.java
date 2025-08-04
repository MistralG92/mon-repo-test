package com.mistral.hilo.ui.console;

import com.mistral.hilo.ui.GameInterface;
import com.mistral.hilo.game.GameConfig;
import java.util.Scanner;

public class GameInterfaceConsole extends GameInterface {
    private final Scanner scanner;

    public GameInterfaceConsole() {
        this.scanner = new Scanner(System.in);
    }

    public GameInterfaceConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void displayWelcome() {
        clearScreen();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    🎮 HI-LO GAME 🎮                    ");
        System.out.println("=".repeat(60));
        System.out.println(" RÈGLES :");
        System.out.println("  • 2 joueurs s'affrontent avec leurs propres decks");
        System.out.println("  • Devinez si votre prochaine carte sera HAUTE ou BASSE");
        System.out.println("  • En cas d'égalité → 'HAUTE' est considéré correct");
        System.out.println("  • Points SEULEMENT si UN SEUL joueur a raison");
        System.out.println("  • Premier à atteindre le score cible = 🏆 GAGNANT");
        System.out.println("=".repeat(60));
        waitForEnter();
    }

    @Override
    public int getMainMenuChoice() {
        clearScreen();
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║          MENU PRINCIPAL          ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1.    Nouvelle partie           ║");
        System.out.println("║  2.    Quitter                   ║");
        System.out.println("╚══════════════════════════════════╝");

        return getIntInput("🎮 Votre choix : ", 1, 2);
    }

    @Override
    public GameConfig setupGameConfig() {
        clearScreen();
        System.out.println("⚙️ CONFIGURATION DE PARTIE :");
        System.out.println("-".repeat(30));

        int cardsPerRound = getIntInput(
                " Nombre de cartes par manche (2-26) ? : ", 2, 26);

        int targetScore = getIntInput(
                " Score à atteindre pour gagner (1-20) ? : ", 1, 20);

        GameConfig config = new GameConfig(cardsPerRound, targetScore);
        System.out.println("\n " + config.toString());
        return config;
    }

    @Override
    public String getPlayerName(int playerNumber) {
        System.out.print("👤 Nom du Joueur " + playerNumber + " : ");
        String name = scanner.nextLine().trim();
        return name.isEmpty() ? ("Joueur " + playerNumber) : name;
    }

    @Override
    public String getPlayerGuess(String playerName, String currentCard, String opponentCard) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println(" Tour de " + playerName);
        System.out.println("═".repeat(50));
        System.out.println(" Votre carte actuelle    : " + currentCard);
        System.out.println(" Carte visible adverse  : " + opponentCard);
        System.out.println("-".repeat(50));

        String guess;
        do {
            System.out.print(" Votre prochaine carte sera-t-elle HAUTE (h) ou BASSE (b) ? ");
            guess = scanner.nextLine().toLowerCase().trim();

            if (isValidGuess(guess)) {
                break;
            }
            System.out.println(" Entrée invalide ! Utilisez 'h' ou 'b'");
        } while (true);

        return guess.startsWith("h") ? "h" : "b";
    }

    @Override
    public boolean askNewRound() {
        String response;
        do {
            System.out.print(" Nouvelle manche ? (o/n) : ");
            response = scanner.nextLine().toLowerCase().trim();

            if (isValidYesNo(response)) {
                break;
            }
            System.out.println(" Répondez par 'o' (oui) ou 'n' (non)");
        } while (true);

        return response.startsWith("o");
    }

    @Override
    public void displayRoundResult(String player1, String player2,
            String card1, String card2,
            boolean player1Correct, boolean player2Correct,
            int score1, int score2) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("                  RÉSULTATS DE LA MANCHE");
        System.out.println("═".repeat(60));
        System.out.printf(" %-15s : %-10s → %s%n",
                player1, card1, player1Correct ? " CORRECT" : " INCORRECT");
        System.out.printf(" %-15s : %-10s → %s%n",
                player2, card2, player2Correct ? " CORRECT" : " INCORRECT");
        System.out.println("-".repeat(60));
        System.out.printf(" SCORES : %s(%d) - %s(%d)%n",
                player1, score1, player2, score2);
        System.out.println("═".repeat(60));
        waitForEnter();
    }

    @Override
    public void displayGameWinner(String winner, int finalScore) {
        clearScreen();
        System.out.println("🎊".repeat(20));
        System.out.println("             🏆 VICTOIRE ! 🏆");
        System.out.println("🎊".repeat(20));
        System.out.println("        🎉 " + winner.toUpperCase() + " GAGNE ! 🎉");
        System.out.println("         Score final : " + finalScore + " points");
        System.out.println("🎊".repeat(20));
    }

    @Override
    public void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
    }

    // Méthodes utilitaires privées
    private int getIntInput(String prompt, int min, int max) {
        int value;
        do {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    break;
                }
                System.out.printf(" Valeur doit être entre %d et %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println(" Veuillez entrer un nombre valide");
            }
        } while (true);

        return value;
    }

    private boolean isValidGuess(String guess) {
        return guess.equals("h") || guess.equals("b") ||
                guess.equals("haute") || guess.equals("basse");
    }

    private boolean isValidYesNo(String response) {
        return response.equals("o") || response.equals("oui") ||
                response.equals("n") || response.equals("non");
    }

    private void clearScreen() {
        // Pour Windows/Unix
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // Fallback - lignes vides
            System.out.println("\n".repeat(50));
        }
    }

    private void waitForEnter() {
        System.out.print("\n⏎ Appuyez sur ENTRÉE pour continuer...");
        scanner.nextLine();
    }
}