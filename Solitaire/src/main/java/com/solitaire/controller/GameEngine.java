package com.solitaire.controller;

import com.solitaire.model.*;
import com.solitaire.model.Card.Rank;
import com.solitaire.model.Card.Suit;
import com.solitaire.model.GameBoard;
import com.solitaire.model.Pile;
import java.util.List;

import java.util.Optional;

/**
 * The brain of the game. Validates moves and manages game state transitions.
 */
public class GameEngine {
    private final GameBoard board;

    public GameEngine(GameBoard board) {
        this.board = board;
    }

    /**
     * Initializes a new game session.
     * Shuffles the deck and distributes cards to the tableau.
     */
    public void startGame() {
        Deck deck = new Deck();
        deck.shuffle();

        List<Pile> tableau = board.getTableau();

        // Standard Solitaire Deal:
        // Column 1 gets 1 card, Col 2 gets 2, ..., Col 7 gets 7.
        for (int i = 0; i < 7; i++) {
            Pile currentColumn = tableau.get(i);

            // Deal the number of cards corresponding to the column index + 1
            for (int j = 0; j <= i; j++) {
                Card card = deck.draw();
                if (card != null) {
                    // Only the top card of each tableau column is face up
                    if (j == i) {
                        card.setFaceUp(true);
                    }
                    currentColumn.push(card);
                }
            }
        }

        // The remaining cards in the deck go into the Stock pile
        Pile stock = board.getStock();
        while (!deck.isEmpty()) {
            stock.push(deck.draw());
        }
    }

    /**
     * Validates if a card can be moved onto another card in the Tableau.
     * Rule: Must be opposite color and exactly one rank lower.
     */
    public boolean canMoveToTableau(Card movingCard, Card targetCard) {
        if (movingCard == null || targetCard == null) return false;

        boolean oppositeColor = isOppositeColor(movingCard, targetCard);
        boolean descendingRank = movingCard.getRank().getValue() == targetCard.getRank().getValue() - 1;

        return oppositeColor && descendingRank;
    }

    /**
     * Validates if a card can be moved to a Foundation pile.
     * Rule: Must be same suit and exactly one rank higher (Ace is the base).
     */
    public boolean canMoveToFoundation(Card movingCard, Pile foundation) {
        if (movingCard == null) return false;

        if (foundation.isEmpty()) {
            // Only an Ace can start a foundation
            return movingCard.getRank() == Rank.ACE;
        }

        Card topCard = foundation.peek();
        boolean sameSuit = movingCard.getSuit() == topCard.getSuit();
        boolean ascendingRank = movingCard.getRank().getValue() == topCard.getRank().getValue() + 1;

        return sameSuit && ascendingRank;
    }

    /**
     * Utility to determine if two cards have opposite colors.
     * Hearts/Diamonds are Red, Spades/Clubs are Black.
     */
    private boolean isOppositeColor(Card c1, Card c2) {
        boolean c1IsRed = (c1.getSuit() == Suit.HEARTS || c1.getSuit() == Suit.DIAMONDS);
        boolean c2IsRed = (c2.getSuit() == Suit.HEARTS || c2.getSuit() == Suit.DIAMONDS);
        return c1IsRed != c2IsRed;
    }

    /**
     * Logic for drawing from the stock to the waste.
     */
    public void drawCard() {
        Pile stock = board.getStock();
        Pile waste = board.getWaste();

        if (!stock.isEmpty()) {
            Card card = stock.pop();
            card.setFaceUp(true);
            waste.push(card);
        } else {
            // Reset waste back to stock if stock is empty
            while (!waste.isEmpty()) {
                Card card = waste.pop();
                card.setFaceUp(false);
                stock.push(card);
            }
        }
    }

    /**
     * Checks if all foundation piles are full.
     * @return true if the player has won the game.
     */
    public boolean checkWinCondition() {
        Pile[] foundations = board.getFoundations();

        for (Pile pile : foundations) {
            // A standard deck has 13 ranks per suit.
            // If any foundation has fewer than 13 cards, the game isn't over.
            if (pile.size() < 13) {
                return false;
            }
        }

        // If the loop completes, all 4 piles have 13 cards.
        return true;
    }

    /**
     * Wraps the movement to foundation with a win check.
     */
    public boolean moveCardToFoundation(Card card, Pile foundation) {
        if (canMoveToFoundation(card, foundation)) {
            foundation.push(card);

            if (checkWinCondition()) {
                // Here you would trigger the win event in the GUI
                System.out.println("Congratulations! You won!");
                return true;
            }
            return true;
        }
        return false;
    }
}