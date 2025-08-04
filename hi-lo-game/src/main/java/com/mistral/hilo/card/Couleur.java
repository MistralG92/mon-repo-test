package com.mistral.hilo.card;

public enum Couleur {
    HEARTS(" Coeur "),
    DIAMONDS(" Carreau "),
    CLUBS(" Trèfle "),
    SPADES(" Pique ");

    private final String symbol;

    Couleur(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}