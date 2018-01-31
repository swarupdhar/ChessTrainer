package sdhar.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import res.Res;
import sdhar.chess.Board;
import sdhar.chess.BoardBuilder;
import sdhar.chess.GameHistory;
import sdhar.chess.Piece;

public class AnalysisBoard extends Canvas {

    private static final Color DARK_SQUARE_COL = Color.GREEN.darker();
    private static final Color LIGHT_SQUARE_COL = Color.BEIGE;

    private static int mouseToBoardIndex(final double mouseX, final double mouseY, final double boardWidth, final double boardHeight) {
        int x = (int) Math.floor(mouseX / (boardWidth / 8));
        int y = (int) Math.floor(mouseY / (boardHeight / 8));
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 7) x = 7;
        if (y > 7) y = 7;
        return x + y * 8;
    }

    private final GraphicsContext g;
    private final boolean[] pieceVisible;
    private Board chessBoard;
    private GameHistory history;
    private Piece selectedPiece;
    private int selectedPieceIndex;
    private double mouseX, mouseY;
    private boolean dragged;

    AnalysisBoard(final double size) {
        g = getGraphicsContext2D();
        pieceVisible = new boolean[64];
        chessBoard = BoardBuilder.buildStandard();

        history = new GameHistory(chessBoard);

        selectedPiece = Piece.NONE;
        selectedPieceIndex = -1;

        for (int i = 0; i < 64; i++) pieceVisible[i] = true;

        setWidth(size);
        setHeight(size);
        widthProperty().addListener(evt -> renderBoard());
        heightProperty().addListener(evt -> renderBoard());

        setMouseHandlers();
    }

    private void setMouseHandlers() {
        setOnMousePressed(event -> {
            final int currentIndex = mouseToBoardIndex(event.getX(), event.getY(), getWidth(), getHeight());
            if (selectedPiece != Piece.NONE) {
                pieceVisible[currentIndex] = true;
                pieceVisible[selectedPieceIndex] = true;
                chessBoard.makeMove(selectedPieceIndex, currentIndex).ifPresent(board -> {
                    history.addNext(board);
                    chessBoard = board;
                    renderBoard();
                });
                selectedPiece = Piece.NONE;
            } else {
                selectedPiece = chessBoard.getPiece(currentIndex);
                selectedPieceIndex = currentIndex;
            }
        });
        setOnMouseDragged(e -> {
            if (selectedPiece != Piece.NONE) {
                pieceVisible[selectedPieceIndex] = false;
                mouseX = e.getX();
                mouseY = e.getY();
                dragged = true;
                renderBoard();
            }
        });
        setOnMouseReleased(e -> {
            final int index = mouseToBoardIndex(e.getX(), e.getY(), getWidth(), getHeight());
            if (dragged) {
                if (selectedPiece != Piece.NONE) {
                    pieceVisible[selectedPieceIndex] = true;
                    pieceVisible[index] = true;
                    chessBoard.makeMove(selectedPieceIndex, index).ifPresent(board -> {
                        history.addNext(board);
                        chessBoard = board;
                    });
                    selectedPiece = Piece.NONE;
                    renderBoard();
                }
                dragged = false;
            }
        });
    }

    private void renderBoard() {
        g.clearRect(0, 0, getWidth(), getHeight());
        int colorCounter = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                colorCounter ^= 1;
                final double squareSize = getHeight() / 8;
                final Color squareCol = (colorCounter == 1) ? LIGHT_SQUARE_COL : DARK_SQUARE_COL;

                g.setFill(squareCol);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                final int finalX = x;
                final int finalY = y;
                final int index = x + y * 8;

                if (pieceVisible[index]) {
                    Res.getImageForPiece(chessBoard.getPiece(index)).ifPresent(image -> {
                        final double imageX = finalX * squareSize + (squareSize / 2 - image.getWidth() / 2);
                        final double imageY = finalY * squareSize + (squareSize / 2 - image.getHeight() / 2);

                        g.drawImage(image, imageX, imageY);
                    });
                }

                Res.getImageForPiece(selectedPiece).ifPresent(image -> {
                    final double imageX = mouseX - image.getWidth() / 2;
                    final double imageY = mouseY - image.getHeight() / 2;

                    g.drawImage(image, imageX, imageY);
                });


            }
            colorCounter ^= 1;
        }
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
