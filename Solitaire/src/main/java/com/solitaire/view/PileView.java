package com.solitaire.view;

import com.solitaire.model.Card;
import com.solitaire.model.Pile;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.solitaire.util.GameConstants;

/**
 * A specialized container that renders a Pile of cards.
 * Handles the vertical offset for the tableau's cascading effect.
 */
public class PileView extends JPanel {
    private final Pile modelPile;
    private final List<CardView> cardViews = new ArrayList<>();
    private final boolean isTableau;


    public PileView(Pile modelPile, boolean isTableau) {
        this.modelPile = modelPile;
        this.isTableau = isTableau;

        this.setLayout(null);
        this.setPreferredSize(new Dimension(64, 500));
        this.setOpaque(false);
    }

    /**
     * Syncs the visual components with the actual Card models in the Pile.
     */
    public void refresh() {
        this.removeAll();
        cardViews.clear();

        int index = 0;
        for (Card card : modelPile) {
            CardView cv = new CardView(card);

            int yOffset = isTableau ? index * GameConstants.TABLEAU_OFFSET : 0;

            // Use constants instead of hardcoded numbers
            cv.setBounds(0, yOffset, GameConstants.CARD_WIDTH, GameConstants.CARD_HEIGHT);

            this.add(cv);
            cardViews.add(cv);
            index++;
        }
        revalidate();
        repaint();
    }
}