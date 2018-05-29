package sdhar.io;

import sdhar.chess.*;

import java.util.List;
import java.util.Optional;

public class FENString {

    public static String createFromBoard(final Board board) {
        final StringBuilder fenBuilder = new StringBuilder();
        final StringBuilder rowBuilder = new StringBuilder();
        final List<Piece> squares = board.getSquares();

        for (int row = 0; row < 8; row++) {
            int numEmptySquares = 0;
            rowBuilder.delete(0, rowBuilder.length());

            for (int col = 0; col < 8; col++) {
                final Piece currentPiece = squares.get(col + row * 8);
                if (currentPiece == Piece.NONE) {
                    numEmptySquares += 1;
                } else {
                    if (numEmptySquares > 0) {
                        rowBuilder.append(numEmptySquares);
                        numEmptySquares = 0;
                    }
                    rowBuilder.append(currentPiece.getLabel());
                }
            }

            if (numEmptySquares > 0) {
                rowBuilder.append(numEmptySquares);
            }
            if (row != 7) {
                rowBuilder.append("/");
            }
            fenBuilder.append(rowBuilder);
        }

        fenBuilder.append(String.format(" %s ", board.getTurnToMove()== Side.WHITE? "w":"b"));

        final CastleRights wCastleRights = board.getCastleRight(Side.WHITE);
        final CastleRights bCastleRights = board.getCastleRight(Side.BLACK);

        if (wCastleRights == CastleRights.NONE && bCastleRights == CastleRights.NONE) {
            fenBuilder.append(" - ");
        } else {
            switch (wCastleRights) {
                case KING_SIDE:
                    fenBuilder.append("K");
                    break;
                case QUEEN_SIDE:
                    fenBuilder.append("Q");
                    break;
                case BOTH:
                    fenBuilder.append("KQ");
                    break;
            }
            switch (bCastleRights) {
                case KING_SIDE:
                    fenBuilder.append("k");
                    break;
                case QUEEN_SIDE:
                    fenBuilder.append("q");
                    break;
                case BOTH:
                    fenBuilder.append("kq");
                    break;
            }
        }

        Square enpassSquare = board.getEnpassantSquare();
        if (enpassSquare == Square.NONE) {
            fenBuilder.append(" - ");
        } else {
            fenBuilder.append(String.format(" %s ", enpassSquare.toString().toLowerCase()));
        }

        fenBuilder.append("0 1"); //TODO: make this more accurate

        return fenBuilder.toString();
    }

    public static Optional<Board> generateBoardFrom(final String fen) {
        return Optional.empty();
    }

}
