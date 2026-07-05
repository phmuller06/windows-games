package com.solitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * manages the collection of 52 cards
 * handles initialization, shuffling, and drawing logic
 */
public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * populates the deck with one of each rank for every suit
     */
    private void initializeDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * randomizes the order of cards in the deck
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * removes and returns the top card from the deck
     * @return the card object or null if the deck is empty
     */
    public Card draw() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}