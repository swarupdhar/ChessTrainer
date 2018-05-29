package sdhar.gui;

import javafx.scene.layout.Pane;
import sdhar.chess.Board;
import sdhar.chess.GameHistory;

import java.util.function.Function;

public abstract class BasePane extends Pane {

    protected Board currentBoard;
    protected final GameHistory history;
    protected final BoardRenderer boardRenderer;
    protected final Menu menu;

    BasePane(final double width, final double height, final Function<Void, Board> initFunc) {
        currentBoard = initFunc.apply(null);
        if (currentBoard == null)
            throw new AssertionError(
                    "Can't have an uninitialized board! Initialize the board in the `initBoard` method."
            );

        history = new GameHistory(currentBoard);
        boardRenderer = new BoardRenderer(400, currentBoard.getSquares());
        menu = new Menu();

        setWidth(width);
        setHeight(height);

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            setWidth(value);
            centerBoard();
        });

        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            setHeight(value);
            resizeBoard();
            centerBoard();
        });

        menu.prefHeightProperty().bind(heightProperty());
        boardRenderer.renderBoard();

        getChildren().addAll(boardRenderer, menu);
    }

    protected void centerBoard() {
        boardRenderer.setTranslateX(getWidth()/2 - boardRenderer.getWidth()/2);
        boardRenderer.setTranslateY(getHeight()/2 - boardRenderer.getHeight()/2 - 20);
    }

    protected void resizeBoard() {
        if (getHeight() * 0.8 < getWidth() * 0.65) {
            boardRenderer.setHeight(getHeight() * 0.8);
            boardRenderer.setWidth(getHeight() * 0.8);
        }
    }
}
