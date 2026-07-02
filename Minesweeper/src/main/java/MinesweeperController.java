//MinesweeperController manages the interaction between the Model and the View

package main.java;

public class MinesweeperController {
    private final MinesweeperModel model;
    private final MinesweeperView view;

    public MinesweeperController(MinesweeperModel model, MinesweeperView view) {
        this.model = model;
        this.view = view;
    }

    public void handleCellClick(int r, int c) {
        if (model.isRevealed(r, c) || model.isFlagged(r, c)) return;

        int value = model.getCellValue(r, c);

        if (value == -1) {
            view.updateCellText(r, c, "X");
            // Game over logic could be expanded here to reveal all mines
            return;
        }

        revealCell(r, c);
    }

    public void handleRightClick(int r, int c) {
        if (model.isRevealed(r, c)) return;

        boolean currentState = model.isFlagged(r, c);
        model.setFlagged(r, c, !currentState);

        if (model.isFlagged(r, c)) {
            view.updateCellText(r, c, "🚩");
        } else {
            view.updateCellText(r, c, "");
        }
    }

    private void revealCell(int r, int c) {
        if (r < 0 || r >= model.getRows() || c < 0 || c >= model.getCols() || model.isRevealed(r, c)) {
            return;
        }

        model.setRevealed(r, c, true);
        int value = model.getCellValue(r, c);

        if (value > 0) {
            view.updateCellText(r, c, String.valueOf(value));
            view.setCellDisabled(r, c, true);
        } else {
            view.updateCellText(r, c, "");
            view.setCellDisabled(r, c, true);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        revealCell(r + i, c + j);
                    }
                }
            }
        }

        if (model.getRevealedCount() == model.getTotalSafeCells()) {
            view.showWinMessage();
        }
    }
}