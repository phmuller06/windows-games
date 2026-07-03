package com.solitaire.view;

import com.solitaire.model.GameBoard;
import com.solitaire.model.Pile;
import com.solitaire.controller.GameEngine;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.solitaire.util.GameConstants;

public class GameWindow extends JFrame {
    private final GameBoard board;
    private final GameEngine engine;
    private final List<PileView> allPileViews = new ArrayList<>();

    public GameWindow(GameBoard board, GameEngine engine) {
        this.board = board;
        this.engine = engine;

        setTitle("Java Solitaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        setResizable(false);

        // Set layout to null for total control
        setLayout(null);
        getContentPane().setBackground(new Color(34, 139, 34));

        setupLayout();
        renderBoard();

        setLocationRelativeTo(null);
    }


    // --- Inside GameWindow.java ---


    private void setupLayout() {
        // 1. STOCK AND WASTE (Top Left)
        PileView stockView = createPileView(board.getStock(), false);
        PileView wasteView = createPileView(board.getWaste(), false);
        allPileViews.add(stockView);
        allPileViews.add(wasteView);

        // Position: X=20, Y=20. Width=64, Height=96 (standard card height)
        stockView.setBounds(20, 20, GameConstants.CARD_WIDTH, GameConstants.CARD_HEIGHT);
        wasteView.setBounds(20 + GameConstants.CARD_WIDTH + 15, 20, GameConstants.CARD_WIDTH, GameConstants.CARD_HEIGHT);

        add(stockView);
        add(wasteView);

        // 2. FOUNDATIONS (Top Right)
        // We calculate the starting X so they align with the right side of the tableau
        int foundationStartX = (GameConstants.WINDOW_WIDTH - (4 * (GameConstants.CARD_WIDTH + 15))) / 2 + (3 * (GameConstants.CARD_WIDTH + 15));
        // Simplified: Let's just align them to the right of the screen with a margin
        int fX = GameConstants.WINDOW_WIDTH - (4 * (GameConstants.CARD_WIDTH + 15)) - 20;
        int fY = 20;

        for (int i = 0; i < 4; i++) {
            Pile f = board.getFoundations()[i];
            PileView pv = createPileView(f, false);
            allPileViews.add(pv);

            // Position each foundation pile explicitly
            pv.setBounds(fX + (i * (GameConstants.CARD_WIDTH + 15)), fY, GameConstants.CARD_WIDTH, GameConstants.CARD_HEIGHT);
            add(pv);
        }

        // 3. TABLEAU (Center/Bottom)
        int startX = (GameConstants.WINDOW_WIDTH - (7 * (GameConstants.CARD_WIDTH + 15))) / 2;
        int startY = 150;

        for (int i = 0; i < 7; i++) {
            Pile p = board.getTableau().get(i);
            PileView pv = createPileView(p, true);
            allPileViews.add(pv);

            // Position: we give them a large height (400) so cards can stack downwards
            pv.setBounds(startX + (i * (GameConstants.CARD_WIDTH + 15)), startY, GameConstants.CARD_WIDTH, 400);
            add(pv);
        }
    }

    private PileView createPileView(Pile pile, boolean isTableau) {
        return new PileView(pile, isTableau);
    }

    public void renderBoard() {
        for (PileView pv : allPileViews) {
            pv.refresh();
        }
    }
}