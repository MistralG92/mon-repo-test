package com.mistral.hilo.card;

public enum Hauteur {
    ACE(1, "As"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "Valet"),
    QUEEN(12, "Dame"),
    KING(13, "Roi");

    private final int value;
    private final String displayName;

    Hauteur(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
    public int getValue() { return value; }
    public String getDisplayName() { return displayName; }
}
