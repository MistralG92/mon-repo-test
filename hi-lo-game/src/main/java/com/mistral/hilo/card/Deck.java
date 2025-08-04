package com.mistral.hilo.card;

import java.util.*;

public class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        this.cards = new ArrayList<>();
        this.random = new Random();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        for (Hauteur hauteur : Hauteur.values()) {
            for (Couleur couleur : Couleur.values()) {
                cards.add(new Card(hauteur, couleur));
            }
        }

        System.err.println("Deck initialisé avec " + cards.size() + " cartes.");
    }

    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Le deck est vide !");
        }
        return cards.remove(cards.size() - 1);
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    public int remainingCards() {
        return cards.size();
    }
}