package com.solitaire.view;

import com.solitaire.controller.GameEngine;
import com.solitaire.model.Card;
import com.solitaire.model.Pile;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.*;

/**
 * Handles the logic of dragging a CardView from one Pile to another.
 */
public class CardTransferHandler extends TransferHandler {
    private final GameEngine engine;

    public CardTransferHandler(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public int getSourceActions(JComponent c) {
        // We only allow moving the card, not copying it.
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        // We wrap the Card model inside a Transferable object.
        // This allows the system to "carry" the card data while dragging.
        return new CardTransferable((CardView) c);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // This is where we ask the GameEngine if the move is legal.
        if (!support.isDataFlavorSupported(CardTransferable.FLAVOR)) {
            return false;
        }

        // In a real scenario, we'd get the card being dragged and the target pile
        // and call engine.canMoveToTableau() or engine.canMoveToFoundation().
        return true;
    }

    public boolean importImmediate(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            // Extract the card from the transferable object
            CardView cardView = (CardView) support.getTransferable()
                    .getTransferData(CardTransferable.FLAVOR);

            // Execute the move logic in the engine here
            // engine.moveCard(cardView.getCard(), targetPile);

            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}