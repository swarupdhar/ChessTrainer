package sdhar.gui;

import sdhar.chess.BoardBuilder;
import sdhar.chess.Square;
import sdhar.engine.Engine;
import sdhar.io.FENString;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayComputerPane extends BasePane {

    private Engine engine = new Engine("/Library/Application Support/Adobe/Adobe PCD/cache/Stash/stockfish-9-mac/Mac/stockfish-9-64");

    public PlayComputerPane(
            final double width,
            final double height,
            final BiConsumer<UserEvent, BasePane> handler
    ) {
        super(width, height, none -> BoardBuilder.buildStandard());

        menu.addItem("Analysis Mode", event -> handler.accept(UserEvent.START_ANALYSIS_MODE_EVENT, this));
        menu.addItem("Training", event -> handler.accept(UserEvent.START_TRAINING_EVENT, this));
        menu.addItem("Settings", event -> handler.accept(UserEvent.SHOW_SETTINGS_EVENT, this));
        menu.addItem("About", event -> handler.accept(UserEvent.SHOW_ABOUT_EVENT, this));

        try {
            engine.openProgram();
            engine.sendUciNewGameCommand();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        boardRenderer.setOnPieceMoved((squareFrom, squareTo) -> {
            if (currentBoard.getPiece(squareFrom).getSide() == currentBoard.getPiece(squareTo).getSide())
                return;

            currentBoard.makeMove(squareFrom, squareTo).ifPresent(newBoard -> {
                try {
                    engine.sendPositionCommand(FENString.createFromBoard(newBoard));
                    engine.sendGoCommand(20, evaluationOutput -> {
                        String bestMove = evaluationOutput.getBestMove();
                        if (!bestMove.isEmpty()) {
                            Square from = Square.valueOf(bestMove.substring(0, 2).toUpperCase());
                            Square to = Square.valueOf(bestMove.substring(2, 4).toUpperCase());

                            currentBoard = newBoard.makeMove(from, to).orElse(newBoard);
                            history.addNext(currentBoard);
                            boardRenderer.setSquares(currentBoard.getSquares());
                            boardRenderer.renderBoard();
                            System.out.println("Your move...");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Engine is thinking...");
                history.addNext(newBoard);
                currentBoard = newBoard;
                boardRenderer.setSquares(currentBoard.getSquares());
            });
        });
    }
}
