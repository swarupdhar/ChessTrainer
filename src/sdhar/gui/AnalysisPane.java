package sdhar.gui;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import sdhar.chess.Board;
import sdhar.chess.BoardBuilder;
import sdhar.chess.Square;
import sdhar.engine.Engine;
import sdhar.io.FENString;

import java.io.IOException;
import java.util.function.Consumer;

public class AnalysisPane extends BasePane {

    public AnalysisPane(final double width, final double height, final Consumer<UserEvent> handler) {
        super(width, height, none -> BoardBuilder.buildStandard());

        menu.addItem("Training", event -> handler.accept(UserEvent.START_TRAINING_EVENT));
        menu.addItem("Play Computer", event -> handler.accept(UserEvent.PLAY_COMPUTER_EVENT));
        menu.addItem("Reports/Stats", event -> handler.accept(UserEvent.SHOW_REPORTS_EVENT));
        menu.addItem("Settings", event -> handler.accept(UserEvent.SHOW_SETTINGS_EVENT));
        menu.addItem("About", event -> handler.accept(UserEvent.SHOW_ABOUT_EVENT));

        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            if (currentBoard.getPiece(squareFrom).getSide() == currentBoard.getPiece(squareTo).getSide())
                return;
            currentBoard = currentBoard.makeMove(squareFrom, squareTo).orElse(currentBoard);
            boardRenderer.setSquares(currentBoard.getSquares());
        });
    }
}
