// hilo/Main.java
package com.mistral.hilo;

import com.mistral.hilo.game.HiloGame;
import com.mistral.hilo.ui.GameInterface;
import com.mistral.hilo.ui.console.GameInterfaceConsole;
import com.mistral.hilo.ui.gui.GameInterfaceGraphic;

public class Main {

    private static final String VERSION = "2.0";
    private static final String AUTHOR = "Mistral";

    public static void main(String[] args) {
        displayBanner();

        try {
            // Détermine l'interface à utiliser
            GameInterface gameInterface = selectInterface(args);

            // Lance le jeu
            HiloGame game = new HiloGame(gameInterface);
            game.start();

        } catch (Exception e) {
            System.err.println("❌ ERREUR CRITIQUE : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("\n👋 Merci d'avoir joué ! À bientôt !");
    }

    /**
     * Sélectionne l'interface utilisateur selon les arguments
     * 
     * @param args Arguments de ligne de commande
     * @return Interface utilisateur choisie
     */
    private static GameInterface selectInterface(String[] args) {
        // Analyse des arguments
        String interfaceType = parseInterfaceArgument(args);

        switch (interfaceType.toLowerCase()) {
            case "gui":
            case "graphic":
            case "swing":
                System.out.println("🖼️ Lancement de l'interface graphique...");
                return new GameInterfaceGraphic();

            case "console":
            case "terminal":
            case "cli":
            default:
                System.out.println("🖥️ Lancement de l'interface console...");
                return new GameInterfaceConsole();
        }
    }

    /**
     * Parse les arguments pour déterminer le type d'interface
     * 
     * @param args Arguments de ligne de commande
     * @return Type d'interface demandé
     */
    private static String parseInterfaceArgument(String[] args) {
        // Recherche --interface=TYPE ou -i TYPE
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toLowerCase();

            if (arg.startsWith("--interface=")) {
                return arg.substring(12);
            }

            if ((arg.equals("-i") || arg.equals("--interface")) && i + 1 < args.length) {
                return args[i + 1];
            }

            if (arg.equals("--gui") || arg.equals("-g")) {
                return "gui";
            }

            if (arg.equals("--console") || arg.equals("-c")) {
                return "console";
            }
        }

        // Interface par défaut si pas d'argument
        return "console";
    }

    /**
     * Affiche la bannière de démarrage
     */
    private static void displayBanner() {
        System.out.println("🎮".repeat(25));
        System.out.println("           HI-LO GAME v" + VERSION);
        System.out.println("              par " + AUTHOR);
        System.out.println("🎮".repeat(25));
        System.out.println();

        // Affiche l'aide si demandée
        if (shouldDisplayHelp()) {
            displayHelp();
        }
    }

    /**
     * Vérifie si l'aide doit être affichée
     */
    private static boolean shouldDisplayHelp() {
        String[] args = System.getProperty("sun.java.command", "").split(" ");
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Affiche l'aide d'utilisation
     */
    private static void displayHelp() {
        System.out.println("📋 UTILISATION :");
        System.out.println("  java -jar hilo.jar [OPTIONS]");
        System.out.println();
        System.out.println("🔧 OPTIONS :");
        System.out.println("  -i, --interface TYPE    Interface utilisateur (console|gui)");
        System.out.println("  -c, --console          Force l'interface console");
        System.out.println("  -g, --gui              Force l'interface graphique");
        System.out.println("  -h, --help             Affiche cette aide");
        System.out.println();
        System.out.println("📖 EXEMPLES :");
        System.out.println("  java -jar hilo.jar                    # Interface console");
        System.out.println("  java -jar hilo.jar --gui              # Interface graphique");
        System.out.println("  java -jar hilo.jar -i console         # Interface console");
        System.out.println("  java -jar hilo.jar --interface=gui    # Interface graphique");
        System.out.println();
        System.out.println("-".repeat(50));
    }

    /**
     * Gestionnaire d'arrêt propre
     */
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Arrêt du jeu...");
            // Ici tu peux ajouter du nettoyage si nécessaire
        }));
    }
}
