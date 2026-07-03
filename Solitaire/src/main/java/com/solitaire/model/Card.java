package com.solitaire.model;

import java.util.Objects;

/**
 * Represents a single playing card.
 * Using an enum for Suit and Rank ensures type safety and prevents
 * invalid card states.
 */
public class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

        private final int value;
        Rank(int value) { this.value = value; }
        public int getValue() { return value; }
    }

    private final Suit suit;
    private final Rank rank;
    private boolean isFaceUp;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false; // Cards start face down by default
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public boolean isFaceUp() { return isFaceUp; }

    public void setFaceUp(boolean faceUp) {
        this.isFaceUp = faceUp;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", rank, suit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}