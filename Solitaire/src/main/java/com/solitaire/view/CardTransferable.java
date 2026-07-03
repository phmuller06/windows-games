package com.solitaire.view;

import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Wraps a CardView so it can be transported via the Swing Drag and Drop system.
 */
public class CardTransferable implements Transferable {
    public static final DataFlavor FLAVOR = new DataFlavor(CardTransferable.class, "CardView");
    private final CardView cardView;

    public CardTransferable(CardView cardView) {
        this.cardView = cardView;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!FLAVOR.equals(flavor)) throw new UnsupportedFlavorException(flavor);
        return cardView;
    }
}