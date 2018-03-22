package sdhar.gui;

import javafx.scene.layout.Pane;
import sdhar.chess.Board;
import sdhar.chess.BoardBuilder;

public class AnalysisPane extends Pane {

    private BoardRenderer boardRenderer;
    private Board board = BoardBuilder.buildStandard();

    public AnalysisPane(final double width, final double height) {
        final Menu menu = new Menu();

        boardRenderer = new BoardRenderer(400, board.getSquares());
        boardRenderer.flipBoard();
        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            board = board.makeMove(squareFrom, squareTo).orElse(board);
            boardRenderer.render(board.getSquares());
        });

        setWidth(width);
        setHeight(height);

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            setWidth(newValue.doubleValue());
            centerAnalysisBoard();
        });

        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            setHeight(newValue.doubleValue());
            resizeAnalysisBoard();
            centerAnalysisBoard();
        });

        menu.prefHeightProperty().bind(heightProperty());
        getChildren().addAll(boardRenderer, menu);
    }

    private void centerAnalysisBoard() {
        boardRenderer.setTranslateX(getWidth()/2 - boardRenderer.getWidth()/2);
        boardRenderer.setTranslateY(getHeight()/2 - boardRenderer.getHeight()/2 - 20);
    }

    private void resizeAnalysisBoard() {
        if (getHeight() * 0.8 < getWidth() * 0.65) {
            boardRenderer.setHeight(getHeight() * 0.8);
            boardRenderer.setWidth(getHeight() * 0.8);
        }
    }

}
