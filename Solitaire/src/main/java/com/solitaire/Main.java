package com.solitaire;

import com.solitaire.model.GameBoard;
import com.solitaire.controller.GameEngine;
import com.solitaire.view.GameWindow;

import javax.swing.SwingUtilities;

/**
 * Application entry point.
 * Initializes the game state and launches the GUI on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        // Using SwingUtilities.invokeLater is critical.
        // Swing is not thread-safe; all GUI updates must happen
        // on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            // 1. Initialize Model
            GameBoard board = new GameBoard();
            // 2. Initialize Controller
            GameEngine engine = new GameEngine(board);
            // 3. Initialize the game state
            engine.startGame();
            // 4. Initialize View
            GameWindow window = new GameWindow(board, engine);
            window.setVisible(true);
        });
    }
}