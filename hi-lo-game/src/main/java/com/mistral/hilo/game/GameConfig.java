package com.mistral.hilo.game;

public class GameConfig {
    private final int cardsPerRound;
    private final int targetScore;

    public GameConfig(int cardsPerRound, int targetScore) {
        validateInput(cardsPerRound, targetScore);
        this.cardsPerRound = cardsPerRound;
        this.targetScore = targetScore;
    }

    private void validateInput(int cards, int target) {
        if (cards < 2 || cards > 26) {
            throw new IllegalArgumentException("Cartes par manche : entre 2 et 26");
        }
        if (target < 1 || target > 20) {
            throw new IllegalArgumentException("Score cible : entre 1 et 20");
        }
    }

    public int getCardsPerRound() {
        return cardsPerRound;
    }

    public int getTargetScore() {
        return targetScore;
    }

    @Override
    public String toString() {
        return String.format("Config[%d cartes/manche, objectif: %d pts]",
                cardsPerRound, targetScore);
    }
}