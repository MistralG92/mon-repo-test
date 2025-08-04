package com.mistral.hilo.card;

import java.util.Objects;

public class Card {
    private final Hauteur hauteur;
    private final Couleur couleur;

    public Card(Hauteur hauteur, Couleur couleur) {
        this.hauteur = hauteur;
        this.couleur = couleur;
    }

    public Hauteur getHauteur() { return hauteur; }
    public Couleur getCouleur() { return couleur; }
    public int getValue() { return hauteur.getValue(); }

    @Override
    public String toString() {
        return hauteur.getDisplayName() + " " + couleur.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return hauteur == card.hauteur && couleur == card.couleur;
    }

        @Override
    public int hashCode() {
        return Objects.hash(hauteur, couleur);
    }
}
