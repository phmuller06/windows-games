//MinesweeperView handles the visual representation of the game; it serves as the entry point for the JavaFX application

package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MinesweeperView extends Application {
    private MinesweeperController controller;
    private Button[][] buttons;

    @Override
    public void start(Stage primaryStage) {
        // Initialize Model
        MinesweeperModel model = new MinesweeperModel();
        model.setDifficulty("beginner");
        model.initializeField();

        // Initialize Controller
        controller = new MinesweeperController(model, this);

        // UI Setup
        GridPane grid = new GridPane();
        int rows = model.getRows();
        int cols = model.getCols();
        buttons = new Button[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Button cellButton = new Button();
                cellButton.setPrefSize(32, 32);

                final int row = r;
                final int col = c;

                // Left Click: Reveal Logic
                cellButton.setOnAction(_ -> controller.handleCellClick(row, col));

                // Right Click: Flagging Logic
                cellButton.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
                    if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                        controller.handleRightClick(row, col);
                    }
                });

                buttons[r][c] = cellButton;
                grid.add(cellButton, c, r);
            }
        }

        Scene scene = new Scene(grid);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateCellText(int r, int c, String text) {
        buttons[r][c].setText(text);
    }

    public void setCellDisabled(int r, int c, boolean disabled) {
        buttons[r][c].setDisable(disabled);
    }

    public void showWinMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victory!");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You cleared the minefield!");
        alert.showAndWait();
    }
}