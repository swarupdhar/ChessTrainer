package sdhar.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;
import sdhar.chess.Board;
import sdhar.chess.BoardBuilder;
import sdhar.io.GameSaver;
import sdhar.training.Trainer;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

public class TrainingPane extends BasePane {

    public TrainingPane(double width, double height, BiConsumer<UserEvent, BasePane> handler) {
        super(width, height, (none) -> BoardBuilder.buildStandard());

        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            if (currentBoard.getPiece(squareFrom).getSide() == currentBoard.getPiece(squareTo).getSide())
                return;

            currentBoard.makeMove(squareFrom, squareTo).ifPresent(newBoard -> {
                history.addNext(newBoard);
                currentBoard = newBoard;
                boardRenderer.setSquares(currentBoard.getSquares());
            });
        });

        menu.addItem("Analysis Mode", event -> handler.accept(UserEvent.START_ANALYSIS_MODE_EVENT, this));
        menu.addItem("Play Computer", event -> handler.accept(UserEvent.START_TRAINING_EVENT, this));
        menu.addItem("Settings", event -> handler.accept(UserEvent.SHOW_SETTINGS_EVENT, this));
        menu.addItem("About", event -> handler.accept(UserEvent.SHOW_ABOUT_EVENT, this));

        final VBox trainingMacroControlBox = new VBox(20);

        final Button addGameBtn = new Button("Add Game");
        addGameBtn.setOnAction(event -> {
            final List<Board> mainLine = history.getMainLine();
            try {
                GameSaver.save(history, "games.dat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        final Button startSessionBtn = new Button("Start Session");
        startSessionBtn.setOnAction(event -> {
            final List<Integer> encoding = GameSaver.encode(currentBoard);
            int[][] data = new int[1][encoding.size()];

            for (int i = 0; i < data.length; i++) {
                data[0][i] = encoding.get(i);
            }
        });


        trainingMacroControlBox.getChildren().addAll(addGameBtn, startSessionBtn);
        trainingMacroControlBox.setTranslateX(getWidth() - 150);
        trainingMacroControlBox.setTranslateY(20);

        getChildren().addAll(trainingMacroControlBox);
    }
}
