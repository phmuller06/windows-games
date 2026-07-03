package com.solitaire.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the layout of the Solitaire board.
 * Holds the Stock, Waste, Foundations, and Tableau.
 */
public class GameBoard {
    private final Pile stock;
    private final Pile waste;
    private final Pile[] foundations;
    private final List<Pile> tableau;

    public GameBoard() {
        this.stock = new Pile("Stock");
        this.waste = new Pile("Waste");
        this.foundations = new Pile[4];
        this.tableau = new ArrayList<>(7);

        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize Foundation piles
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Pile("Foundation " + (i + 1));
        }

        // Initialize Tableau columns
        for (int i = 0; i < 7; i++) {
            tableau.add(new Pile("Tableau " + (i + 1)));
        }
    }

    public Pile getStock() { return stock; }
    public Pile getWaste() { return waste; }
    public Pile[] getFoundations() { return foundations; }
    public List<Pile> getTableau() { return tableau; }

    /**
     * Helper method to get a specific tableau column by index.
     * @param index The column index (0-6)
     * @return The Pile associated with that column.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Pile getTableauColumn(int index) {
        if (index < 0 || index >= 7) {
            throw new IndexOutOfBoundsException("Tableau index must be between 0 and 6");
        }
        return tableau.get(index);
    }
}