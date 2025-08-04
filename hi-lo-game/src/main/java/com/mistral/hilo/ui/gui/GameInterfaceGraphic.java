// ui/gui/GameInterfaceGraphic.java
package com.mistral.hilo.ui.gui;

import com.mistral.hilo.ui.GameInterface;
import com.mistral.hilo.game.GameConfig;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterfaceGraphic extends GameInterface {
    private JFrame mainFrame;
    private JPanel currentPanel;

    // Variables pour stocker les réponses
    private volatile int menuChoice = -1;
    private volatile GameConfig gameConfig = null;
    private volatile String playerName = "";
    private volatile String playerGuess = "";
    private volatile boolean newRoundAnswer = false;
    private volatile boolean waitingForInput = false;

    public GameInterfaceGraphic() {
        initializeFrame();
    }

    private void initializeFrame() {
        mainFrame = new JFrame("🎮 Hi-Lo Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
    }

    @Override
    public void displayWelcome() {
        SwingUtilities.invokeLater(() -> {
            JPanel welcomePanel = createWelcomePanel();
            showPanel(welcomePanel);
            mainFrame.setVisible(true);
        });
    }

    @Override
    public int getMainMenuChoice() {
        SwingUtilities.invokeLater(() -> {
            JPanel menuPanel = createMenuPanel();
            showPanel(menuPanel);
        });

        return waitForMenuChoice();
    }

    @Override
    public GameConfig setupGameConfig() {
        SwingUtilities.invokeLater(() -> {
            JPanel configPanel = createConfigPanel();
            showPanel(configPanel);
        });

        return waitForGameConfig();
    }

    @Override
    public String getPlayerName(int playerNumber) {
        SwingUtilities.invokeLater(() -> {
            JPanel namePanel = createNamePanel(playerNumber);
            showPanel(namePanel);
        });

        return waitForPlayerName();
    }

    @Override
    public String getPlayerGuess(String playerName, String currentCard, String opponentCard) {
        SwingUtilities.invokeLater(() -> {
            JPanel guessPanel = createGuessPanel(playerName, currentCard, opponentCard);
            showPanel(guessPanel);
        });

        return waitForPlayerGuess();
    }

    @Override
    public boolean askNewRound() {
        SwingUtilities.invokeLater(() -> {
            JPanel newRoundPanel = createNewRoundPanel();
            showPanel(newRoundPanel);
        });

        return waitForNewRoundAnswer();
    }

    @Override
    public void cleanup() {
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.dispose();
            }
        });
    }

    // Méthodes de création des panneaux
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(34, 139, 34));

        JLabel title = new JLabel("🎮 HI-LO GAME 🎮", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JTextArea rules = new JTextArea();
        rules.setText("📋 RÈGLES :\n\n" +
                "• 2 joueurs s'affrontent avec leurs propres decks\n" +
                "• Devinez si votre prochaine carte sera HAUTE ou BASSE\n" +
                "• En cas d'égalité → 'HAUTE' est considéré correct\n" +
                "• Points SEULEMENT si UN SEUL joueur a raison\n" +
                "• Premier à atteindre le score cible = 🏆 GAGNANT");
        rules.setFont(new Font("Arial", Font.PLAIN, 16));
        rules.setBackground(new Color(240, 248, 255));
        rules.setEditable(false);
        rules.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton continueBtn = new JButton("Continuer");
        continueBtn.setFont(new Font("Arial", Font.BOLD, 18));
        continueBtn.addActionListener(e -> menuChoice = 1);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(rules), BorderLayout.CENTER);
        panel.add(continueBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(70, 130, 180));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("MENU PRINCIPAL");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 40, 0);
        panel.add(title, gbc);

        JButton newGameBtn = new JButton("🎯 Nouvelle partie");
        newGameBtn.setFont(new Font("Arial", Font.BOLD, 18));
        newGameBtn.setPreferredSize(new Dimension(250, 50));
        newGameBtn.addActionListener(e -> setMenuChoice(1));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(newGameBtn, gbc);

        JButton quitBtn = new JButton("🚪 Quitter");
        quitBtn.setFont(new Font("Arial", Font.BOLD, 18));
        quitBtn.setPreferredSize(new Dimension(250, 50));
        quitBtn.addActionListener(e -> setMenuChoice(2));
        gbc.gridy = 2;
        panel.add(quitBtn, gbc);

        return panel;
    }

    private JPanel createConfigPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(135, 206, 235));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("⚙️ CONFIGURATION");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(title, gbc);

        // Cartes par manche
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("🃏 Cartes par manche:"), gbc);

        JSpinner cardsSpinner = new JSpinner(new SpinnerNumberModel(5, 2, 26, 1));
        cardsSpinner.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panel.add(cardsSpinner, gbc);

        // Score cible
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("🎯 Score à atteindre:"), gbc);

        JSpinner scoreSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        scoreSpinner.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panel.add(scoreSpinner, gbc);

        // Bouton validation
        JButton validateBtn = new JButton("✅ Valider");
        validateBtn.setFont(new Font("Arial", Font.BOLD, 16));
        validateBtn.addActionListener(e -> {
            int cards = (Integer) cardsSpinner.getValue();
            int score = (Integer) scoreSpinner.getValue();
            setGameConfig(new GameConfig(cards, score));
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(validateBtn, gbc);

        return panel;
    }

    private JPanel createNamePanel(int playerNumber) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 182, 193));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("👤 JOUEUR " + playerNumber);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(title, gbc);

        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        JButton validateBtn = new JButton("✅ OK");
        validateBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            setPlayerName(name.isEmpty() ? ("Joueur " + playerNumber) : name);
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(validateBtn, gbc);

        return panel;
    }

    private JPanel createGuessPanel(String playerName, String currentCard, String opponentCard) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(147, 112, 219));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("🎲 Tour de " + playerName);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("🎴 Votre carte: " + currentCard), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("👁️ Carte adverse: " + opponentCard), gbc);

        JLabel question = new JLabel("📈 Votre prochaine carte sera-t-elle ?");
        question.setFont(new Font("Arial", Font.BOLD, 16));
        question.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 0, 20, 0);
        panel.add(question, gbc);

        JButton highBtn = new JButton("🔺 HAUTE");
        highBtn.setFont(new Font("Arial", Font.BOLD, 16));
        highBtn.setPreferredSize(new Dimension(120, 40));
        highBtn.addActionListener(e -> setPlayerGuess("h"));

        JButton lowBtn = new JButton("🔻 BASSE");
        lowBtn.setFont(new Font("Arial", Font.BOLD, 16));
        lowBtn.setPreferredSize(new Dimension(120, 40));
        lowBtn.addActionListener(e -> setPlayerGuess("b"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(highBtn);
        buttonPanel.add(lowBtn);
        buttonPanel.setOpaque(false);

        gbc.gridy = 3;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createNewRoundPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(60, 179, 113));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel question = new JLabel("🔄 Nouvelle manche ?");
        question.setFont(new Font("Arial", Font.BOLD, 24));
        question.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(question, gbc);

        JButton yesBtn = new JButton("✅ OUI");
        yesBtn.setFont(new Font("Arial", Font.BOLD, 16));
        yesBtn.setPreferredSize(new Dimension(100, 40));
        yesBtn.addActionListener(e -> setNewRoundAnswer(true));

        JButton noBtn = new JButton("❌ NON");
        noBtn.setFont(new Font("Arial", Font.BOLD, 16));
        noBtn.setPreferredSize(new Dimension(100, 40));
        noBtn.addActionListener(e -> setNewRoundAnswer(false));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(yesBtn);
        buttonPanel.add(noBtn);
        buttonPanel.setOpaque(false);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    @Override
    public void displayRoundResult(String player1, String player2,
            String card1, String card2,
            boolean player1Correct, boolean player2Correct,
            int score1, int score2) {
        System.out.println("GUI Result: " + player1 + " vs " + player2);
    }

    // Méthodes de définition/attente
    private void setGameConfig(GameConfig config) {
        this.gameConfig = config;
    }

    private GameConfig waitForGameConfig() {
        gameConfig = null;
        while (gameConfig == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return gameConfig;
    }

    private void setPlayerName(String name) {
        this.playerName = name;
    }

    private String waitForPlayerName() {
        playerName = "";
        while (playerName.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return playerName;
    }

    private void setPlayerGuess(String guess) {
        this.playerGuess = guess;
    }

    private String waitForPlayerGuess() {
        playerGuess = "";
        while (playerGuess.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return playerGuess;
    }

    private void setNewRoundAnswer(boolean answer) {
        this.newRoundAnswer = answer;
        this.waitingForInput = false;
    }

    private boolean waitForNewRoundAnswer() {
        waitingForInput = true;
        while (waitingForInput) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return newRoundAnswer;
    }

    // Méthodes d'attente synchrone
    private int waitForMenuChoice() {
        menuChoice = -1;
        while (menuChoice == -1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return menuChoice;
    }

    private void setMenuChoice(int choice) {
        this.menuChoice = choice;
    }

    // ... Autres méthodes d'attente

    private void showPanel(JPanel panel) {
        if (currentPanel != null) {
            mainFrame.remove(currentPanel);
        }
        currentPanel = panel;
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
