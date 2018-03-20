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

    private PieceMovedHandler pieceMovedHandler;

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

    void setOnPieceMoved(final PieceMovedHandler handler) {
        pieceMovedHandler = handler;
    }

    void render(final List<Piece> squares) {
        this.squares = squares;
        Arrays.fill(pieceVisibility, true);
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

            }
            colorCounter ^= 1;
        }
    }

    private void setMouseHandlers() {
        setOnMousePressed(event -> {
            final int currentIndex = mouseToBoardIndex(event.getX(), event.getY(), getWidth(), getHeight());
            if (selected != Piece.NONE) {
                pieceVisibility[currentIndex] = true;
                pieceVisibility[selectedPieceIndex] = true;
                if (pieceMovedHandler != null) {
                    pieceMovedHandler.handle(Square.fromIndex(selectedPieceIndex), Square.fromIndex(currentIndex));
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
            final int currentIndex = mouseToBoardIndex(e.getX(), e.getY(), getWidth(), getHeight());
            if (dragged) {
                if (selected != Piece.NONE) {
                    pieceVisibility[selectedPieceIndex] = true;
                    pieceVisibility[currentIndex] = true;
                    if (pieceMovedHandler != null) {
                        pieceMovedHandler.handle(Square.fromIndex(selectedPieceIndex), Square.fromIndex(currentIndex));
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

    private static int mouseToBoardIndex(final double mouseX, final double mouseY, final double boardWidth, final double boardHeight) {
        int x = (int) Math.floor(mouseX / (boardWidth / 8));
        int y = (int) Math.floor(mouseY / (boardHeight / 8));
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 7) x = 7;
        if (y > 7) y = 7;
        return x + y * 8;
    }

}
