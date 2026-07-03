package com.solitaire.model;

import java.util.Stack;

/**
 * A Pile is a generic container for cards.
 * Solitaire consists of various piles: the Stock, the Waste,
 * the Foundations, and the Tableau columns.
 */
public class Pile extends Stack<Card> {
    private final String name;

    public Pile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    // We extend Stack because Solitaire is fundamentally
    // a LIFO (Last-In, First-Out) operation for most piles.
}