package sdhar.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import res.Res;
import sdhar.chess.Board;
import sdhar.chess.Piece;
import sdhar.chess.Square;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BoardRenderer extends Canvas {

    interface PieceMovedHandler {
        void handle(final Square from, final Square to);
    }

    private static final Color DARK_SQUARE_COL = Color.GREEN.darker();
    private static final Color LIGHT_SQUARE_COL = Color.BEIGE;

    private final GraphicsContext g;
    private final boolean[] pieceVisibility;
    private Piece selected;
    private int selectedPieceIndex;
    private boolean dragged;
    private double mouseX, mouseY;
    private boolean boardFlipped;

    private BiConsumer<Square, Square> pieceMovedHandler;

    private List<Piece> squares;

    BoardRenderer(final int size, final List<Piece> initialSquares) {
        g = getGraphicsContext2D();
        pieceVisibility = new boolean[64];
        selected = Piece.NONE;

        setWidth(size);
        setHeight(size);
        setMouseHandlers();

        render(initialSquares);

        widthProperty().addListener(evt -> renderBoard());
        heightProperty().addListener(evt -> renderBoard());
    }

    void setOnPieceMoved(final BiConsumer<Square, Square> handler) {
        pieceMovedHandler = handler;
    }

    void render(final List<Piece> squares) {
        this.squares = squares;
        Arrays.fill(pieceVisibility, true);
    }

    void flipBoard() {
        boardFlipped = true;
        renderBoard();
    }

    private void renderBoard() {
        g.clearRect(0, 0, getWidth(), getHeight());
        int colorCounter = 0;
        int delta = boardFlipped? -1: 1;
        int index = boardFlipped? 63: 0;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                colorCounter ^= 1;
                final double squareSize = getHeight() / 8;
                final Color squareCol = (colorCounter == 1) ? LIGHT_SQUARE_COL : DARK_SQUARE_COL;

                g.setFill(squareCol);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                final int finalX = x;
                final int finalY = y;
//                final int index = x + y * 8;

                if (pieceVisibility[index]) {
                    Res.getImageForPiece(squares.get(index)).ifPresent(image -> {
                        final double imageX = finalX * squareSize + (squareSize / 2 - image.getWidth() / 2);
                        final double imageY = finalY * squareSize + (squareSize / 2 - image.getHeight() / 2);

                        g.drawImage(image, imageX, imageY);
                    });
                }

                Res.getImageForPiece(selected).ifPresent(image -> {
                    final double imageX = mouseX - image.getWidth() / 2;
                    final double imageY = mouseY - image.getHeight() / 2;

                    g.drawImage(image, imageX, imageY);
                });

                index += delta;
            }
            colorCounter ^= 1;
        }

    }

    private void setMouseHandlers() {
        setOnMousePressed(event -> {
            final int currentIndex = mouseToBoardIndex(event.getX(), event.getY(), getWidth(), getHeight(), boardFlipped);
            if (selected != Piece.NONE) {
                pieceVisibility[currentIndex] = true;
                pieceVisibility[selectedPieceIndex] = true;
                if (pieceMovedHandler != null) {
                    pieceMovedHandler.accept(indexToSquare(selectedPieceIndex), indexToSquare(currentIndex));
                }
                selected = Piece.NONE;
            } else {
                selected = squares.get(currentIndex);
                selectedPieceIndex = currentIndex;
            }
        });
        setOnMouseDragged(e -> {
            if (selected != Piece.NONE) {
                pieceVisibility[selectedPieceIndex] = false;
                mouseX = e.getX();
                mouseY = e.getY();
                dragged = true;
                renderBoard();
            }
        });
        setOnMouseReleased(e -> {
            final int currentIndex = mouseToBoardIndex(e.getX(), e.getY(), getWidth(), getHeight(), boardFlipped);
            if (dragged) {
                if (selected != Piece.NONE) {
                    pieceVisibility[selectedPieceIndex] = true;
                    pieceVisibility[currentIndex] = true;
                    if (pieceMovedHandler != null) {
                        pieceMovedHandler.accept(indexToSquare(selectedPieceIndex), indexToSquare(currentIndex));
                    }
                    selected = Piece.NONE;
                    renderBoard();
                }
                dragged = false;
            }
        });
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

    private static int mouseToBoardIndex (
            final double mouseX,
            final double mouseY,
            final double boardWidth,
            final double boardHeight,
            final boolean flipped
    ) {
        int x = (int) Math.floor(mouseX / (boardWidth / 8));
        int y = (int) Math.floor(mouseY / (boardHeight / 8));
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 7) x = 7;
        if (y > 7) y = 7;
        if (flipped) {
            x = 7 - x;
            y = 7 - y;
        }
        return x + y * 8;
    }

    private static Square indexToSquare(final int index) {
        if (index < 0 || index >= 64) return null;
        switch (index) {
            case 0:
                return Square.A8;
            case 1:
                return Square.B8;
            case 2:
                return Square.C8;
            case 3:
                return Square.D8;
            case 4:
                return Square.E8;
            case 5:
                return Square.F8;
            case 6:
                return Square.G8;
            case 7:
                return Square.H8;

            case 8:
                return Square.A7;
            case 9:
                return Square.B7;
            case 10:
                return Square.C7;
            case 11:
                return Square.D7;
            case 12:
                return Square.E7;
            case 13:
                return Square.F7;
            case 14:
                return Square.G7;
            case 15:
                return Square.H7;

            case 16:
                return Square.A6;
            case 17:
                return Square.B6;
            case 18:
                return Square.C6;
            case 19:
                return Square.D6;
            case 20:
                return Square.E6;
            case 21:
                return Square.F6;
            case 22:
                return Square.G6;
            case 23:
                return Square.H6;

            case 24:
                return Square.A5;
            case 25:
                return Square.B5;
            case 26:
                return Square.C5;
            case 27:
                return Square.D5;
            case 28:
                return Square.E5;
            case 29:
                return Square.F5;
            case 30:
                return Square.G5;
            case 31:
                return Square.H5;

            case 32:
                return Square.A4;
            case 33:
                return Square.B4;
            case 34:
                return Square.C4;
            case 35:
                return Square.D4;
            case 36:
                return Square.E4;
            case 37:
                return Square.F4;
            case 38:
                return Square.G4;
            case 39:
                return Square.H4;

            case 40:
                return Square.A3;
            case 41:
                return Square.B3;
            case 42:
                return Square.C3;
            case 43:
                return Square.D3;
            case 44:
                return Square.E3;
            case 45:
                return Square.F3;
            case 46:
                return Square.G3;
            case 47:
                return Square.H3;

            case 48:
                return Square.A3;
            case 49:
                return Square.B2;
            case 50:
                return Square.C2;
            case 51:
                return Square.D2;
            case 52:
                return Square.E2;
            case 53:
                return Square.F2;
            case 54:
                return Square.G2;
            case 55:
                return Square.H2;

            case 56:
                return Square.A1;
            case 57:
                return Square.B1;
            case 58:
                return Square.C1;
            case 59:
                return Square.D1;
            case 60:
                return Square.E1;
            case 61:
                return Square.F1;
            case 62:
                return Square.G1;
            default:
                return Square.H1;
        }
    }

}
