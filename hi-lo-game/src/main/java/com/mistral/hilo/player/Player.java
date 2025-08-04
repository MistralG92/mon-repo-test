package com.mistral.hilo.player;

import com.mistral.hilo.card.Card;
import com.mistral.hilo.card.Deck;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int score;
    private Deck personalDeck;
    private List<Card> hand;
    private int currentCardIndex;
    private Boolean lastGuess = null; // null = pas de guess, true = plus grand, false = plus petit

    public Player(String name) {
        this.name = name.trim().isEmpty() ? "Joueur Anonyme" : name.trim();
        this.score = 0;
        this.hand = new ArrayList<>();
        this.currentCardIndex = 0;
    }

    public void initializeForNewRound(int cardsCount) {
        personalDeck = new Deck(); // Nouveau deck melange
        hand.clear();
        currentCardIndex = 0;

        // Distribuer les cartes
        for (int i = 0; i < cardsCount; i++) {
            hand.add(personalDeck.drawCard());
        }

        System.out.println(name + " recoit " + cardsCount + " cartes");
    }

    public Card getCurrentCard() {
        if (currentCardIndex >= hand.size()) {
            throw new IllegalStateException("Plus de cartes disponibles pour " + name);
        }
        return hand.get(currentCardIndex);
    }

    public Card getNextCard() {
        currentCardIndex++;
        if (currentCardIndex >= hand.size()) {
            throw new IllegalStateException("Plus de cartes suivantes pour " + name);
        }
        return hand.get(currentCardIndex);
    }

    public boolean hasNextCard() {
        return currentCardIndex + 1 < hand.size();
    }

    public void addPoint() {
        score++;
    }

    public boolean hasWon(int targetScore) {
        return score >= targetScore;
    }

    public void resetScore() {
        score = 0;
    }

    public void setLastGuess(Boolean guess) {
        this.lastGuess = guess;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLastGuess() {
        return lastGuess.toString();
    }

    public int getScore() {
        return score;
    }

    public int getRemainingCards() {
        return hand.size() - currentCardIndex - 1;
    }

    @Override
    public String toString() {
        return String.format("%s (Score: %d)", name, score);
    }
}