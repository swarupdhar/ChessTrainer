package sdhar.gui;

import sdhar.chess.BoardBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AnalysisPane extends BasePane {

    public AnalysisPane(final double width, final double height, final BiConsumer<UserEvent, BasePane> handler) {
        super(width, height, none -> BoardBuilder.buildStandard());

        menu.addItem("Training", event -> handler.accept(UserEvent.START_TRAINING_EVENT, this));
        menu.addItem("Play Computer", event -> handler.accept(UserEvent.PLAY_COMPUTER_EVENT, this));
        menu.addItem("Reports/Stats", event -> handler.accept(UserEvent.SHOW_REPORTS_EVENT, this));
        menu.addItem("Settings", event -> handler.accept(UserEvent.SHOW_SETTINGS_EVENT, this));
        menu.addItem("About", event -> handler.accept(UserEvent.SHOW_ABOUT_EVENT, this));

        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            if (currentBoard.getPiece(squareFrom).getSide() == currentBoard.getPiece(squareTo).getSide())
                return;
            currentBoard = currentBoard.makeMove(squareFrom, squareTo).orElse(currentBoard);
            boardRenderer.setSquares(currentBoard.getSquares());
        });
    }
}
