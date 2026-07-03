package com.solitaire.view;

import com.solitaire.model.Card;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.image.BufferedImage;

/**
 * A visual representation of a Card model.
 * Maps the Card's rank and suit to images in /assets/images/cards/.
 */
public class CardView extends JLabel {
    private final Card card;
    // Constant for the base path to make it easy to change if you restructure
    private static final String CARD_PATH = "/assets/images/cards/";
    private static final String BACK_PATH = "/assets/images/cards/card_back.png";

    public CardView(Card card) {
        this.card = card;
        updateAppearance();
    }

    /**
     * Updates the image based on the card's current face-up state.
     */
    public void updateAppearance() {
        if (!card.isFaceUp()) {
            setImage(loadResource(BACK_PATH));
        } else {
            // This converts "HEARTS" and "ACE" to "hearts_ace.png"
            String filename = String.format("%s_%s.png",
                    card.getSuit().name().toLowerCase(),
                    card.getRank().name().toLowerCase());

            setImage(loadResource(CARD_PATH + filename));
        }
    }

    private Image loadResource(String path) {
        // getClass().getResource() looks into the classpath/resources folder
        URL imgURL = getClass().getResource(path);

        if (imgURL == null) {
            // Debugging log so you know exactly which file is missing
            System.err.println("Resource not found: " + path);
            return createFallbackImage();
        }

        return new ImageIcon(imgURL).getImage();
    }

    private void setImage(Image img) {
        this.setIcon(new ImageIcon(img));
    }

    /**
     * Generates a blank image if the resource is missing.
     * Prevents the GUI from crashing and alerts the developer via the console.
     */
    private Image createFallbackImage() {
        BufferedImage fallback = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = fallback.createGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.WHITE);
        g.drawString("MISSING", 10, 32);
        g.dispose();
        return fallback;
    }
}