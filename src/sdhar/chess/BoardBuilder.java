package sdhar.chess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardBuilder {
    private static final Map<Integer, Piece> STARTING_BOARD_SQUARES = new HashMap<>();
    static {
        STARTING_BOARD_SQUARES.put(0, Piece.BLACK_ROOK);
        STARTING_BOARD_SQUARES.put(1, Piece.BLACK_KNIGHT);
        STARTING_BOARD_SQUARES.put(2, Piece.BLACK_BISHOP);
        STARTING_BOARD_SQUARES.put(3, Piece.BLACK_QUEEN);
        STARTING_BOARD_SQUARES.put(4, Piece.BLACK_KING);
        STARTING_BOARD_SQUARES.put(5, Piece.BLACK_BISHOP);
        STARTING_BOARD_SQUARES.put(6, Piece.BLACK_KNIGHT);
        STARTING_BOARD_SQUARES.put(7, Piece.BLACK_ROOK);

        for (int i = 8; i < 16; i++) {
            STARTING_BOARD_SQUARES.put(i, Piece.BLACK_PAWN);
        }

        for (int i = 48; i < 56; i++) {
            STARTING_BOARD_SQUARES.put(i, Piece.WHITE_PAWN);
        }

        STARTING_BOARD_SQUARES.put(56, Piece.WHITE_ROOK);
        STARTING_BOARD_SQUARES.put(57, Piece.WHITE_KNIGHT);
        STARTING_BOARD_SQUARES.put(58, Piece.WHITE_BISHOP);
        STARTING_BOARD_SQUARES.put(59, Piece.WHITE_QUEEN);
        STARTING_BOARD_SQUARES.put(60, Piece.WHITE_KING);
        STARTING_BOARD_SQUARES.put(61, Piece.WHITE_BISHOP);
        STARTING_BOARD_SQUARES.put(62, Piece.WHITE_KNIGHT);
        STARTING_BOARD_SQUARES.put(63, Piece.WHITE_ROOK);
    }

    public static BoardBuilder fromPieceMap(final Map<Integer, Piece> squares) {
        return new BoardBuilder(squares);
    }

    public static Board buildStandard() {
        return fromPieceMap(STARTING_BOARD_SQUARES).build();
    }

    private Map<Integer, Piece> squares;
    private Side turnToMove;
    private CastleRights whiteCastleRight;
    private CastleRights blackCastleRight;
    private int enpassantIndex;
    private int promotionSquareIndex;

    private BoardBuilder(final Map<Integer, Piece> squares) {
        this.squares = squares;
        turnToMove = Side.WHITE;
        whiteCastleRight = CastleRights.BOTH;
        blackCastleRight = CastleRights.BOTH;
        enpassantIndex = -1;
        promotionSquareIndex = -1;
    }

    public BoardBuilder turnToMove(final Side side) {
        this.turnToMove = side;
        return this;
    }

    public BoardBuilder whiteCastleRight(final CastleRights whiteCastleRight) {
        this.whiteCastleRight = whiteCastleRight;
        return this;
    }

    public BoardBuilder blackCastleRight(final CastleRights blackCastleRight) {
        this.blackCastleRight = blackCastleRight;
        return this;
    }

    public BoardBuilder enpassantIndex(final int enpassantIndex) {
        this.enpassantIndex = enpassantIndex;
        return this;
    }

    public BoardBuilder promotionSquareIndex(final int promotionSquareIndex) {
        this.promotionSquareIndex = promotionSquareIndex;
        return this;
    }

    public Board build() {
        return new Board(squares, turnToMove, whiteCastleRight, blackCastleRight, enpassantIndex, promotionSquareIndex);
    }

}
