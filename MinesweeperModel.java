//MinesweeperModel handles the game logic and state

package main.java;

import java.util.Random;

public class MinesweeperModel { //implements the board generation, mine placement, and state tracking
    private static final int MINE = -1;
    private static final int EMPTY = 0;

    private int[][] mineField;
    private boolean[][] revealed;
    private boolean[][] flagged;

    private int rows;
    private int cols;
    private int totalMines;
    private int revealedCount = 0;

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setDifficulty(String difficulty) { //configures game dimensions and mine count; initializes the internal state arrays based on the chosen difficulty
        switch (difficulty.toLowerCase()) {
            case "beginner":
                this.rows = 9;
                this.cols = 9;
                this.totalMines = 10;
                break;
            case "intermediate":
                this.rows = 16;
                this.cols = 16;
                this.totalMines = 40;
                break;
            case "expert":
                this.rows = 30;
                this.cols = 16;
                this.totalMines = 99;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level provided.");
        }

        this.mineField = new int[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.flagged = new boolean[rows][cols];
        this.revealedCount = 0;
    }

    public void initializeField() { //populates the field with mines and calculates the neighbor counts
        if (mineField == null) {
            throw new IllegalStateException("Difficulty must be set before initializing field.");
        }

        int minesPlaced = 0;
        Random rand = new Random();

        while (minesPlaced < totalMines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            if (mineField[r][c] == EMPTY) {
                mineField[r][c] = MINE;
                minesPlaced++;
            }
        }
        calculateNeighbors();
    }

    private void calculateNeighbors() { //iterates through the field to populate cell values with the number of adjacent mines
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (mineField[r][c] == MINE) {
                    continue;
                }
                mineField[r][c] = countAdjacentMines(r, c);
            }
        }
    }

    private int countAdjacentMines(int r, int c) { //checks the 3x3 grid surrounding a cell to count mines
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nr = r + i;
                int nc = c + j;

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    if (mineField[nr][nc] == MINE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean isRevealed(int r, int c) {
        return revealed[r][c];
    }

    public void setRevealed(int r, int c, boolean state) {
        if (!revealed[r][c] && state) {
            revealedCount++;
        }
        revealed[r][c] = state;
    }

    public boolean isFlagged(int r, int c) {
        return flagged[r][c];
    }

    public void setFlagged(int r, int c, boolean state) {
        flagged[r][c] = state;
    }

    public int getCellValue(int r, int c) {
        return mineField[r][c];
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public int getTotalSafeCells() {
        return (rows * cols) - totalMines;
    }
}