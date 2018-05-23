package sdhar.gui;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import sdhar.chess.Board;
import sdhar.chess.BoardBuilder;
import sdhar.engine.Engine;

public class AnalysisPane extends Pane {

    private BoardRenderer boardRenderer;
    private Board board = BoardBuilder.buildStandard();

    public AnalysisPane(
            final double width,
            final double height,
            final GlobalEventHandler handler
    ) {
        final Menu menu = new Menu();
        menu.addItem("Training", event ->
            handler.handleEvent(GlobalEventHandler.START_TRAINING_EVENT)
        );
        menu.addItem("Play Computer", event -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Let's play!");
            a.show();
        });
        menu.addItem("Reports/Stats", event ->
                handler.handleEvent(GlobalEventHandler.SHOW_REPORTS_EVENT)
        );
        menu.addItem("Settings", event ->
                handler.handleEvent(GlobalEventHandler.SHOW_SETTINGS_EVENT)
        );
        menu.addItem("About", event ->
                handler.handleEvent(GlobalEventHandler.SHOW_ABOUT_EVENT)
        );

        boardRenderer = new BoardRenderer(400, board.getSquares());

        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            if (board.getPiece(squareFrom).getSide() == board.getPiece(squareTo).getSide())
                return;
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
