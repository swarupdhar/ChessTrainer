package sdhar.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Piece {
    NONE(PieceType.NONE, ' ', Side.NONE, new ArrayList<>()),

    WHITE_KING(PieceType.KING, 'K', Side.WHITE, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9)),
    WHITE_QUEEN(PieceType.QUEEN, 'Q', Side.WHITE, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9)),
    WHITE_ROOK(PieceType.ROOK, 'R', Side.WHITE, Arrays.asList(-8, -1, 1, 8)),
    WHITE_KNIGHT(PieceType.KNIGHT, 'N', Side.WHITE, Arrays.asList(-17, -15, -10, -6, 6, 10, 15, -17)),
    WHITE_BISHOP(PieceType.BISHOP, 'B', Side.WHITE, Arrays.asList(-7, -9, 7, 9)),
    WHITE_PAWN(PieceType.PAWN, 'P', Side.WHITE, Arrays.asList(-9, -8, -7, -16)),

    BLACK_KING(PieceType.KING, 'K', Side.BLACK, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9)),
    BLACK_QUEEN(PieceType.QUEEN, 'q', Side.BLACK, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9)),
    BLACK_ROOK(PieceType.ROOK, 'r', Side.BLACK, Arrays.asList(-8, -1, 1, 8)),
    BLACK_KNIGHT(PieceType.KNIGHT, 'n', Side.BLACK, Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17)),
    BLACK_BISHOP(PieceType.BISHOP, 'b', Side.BLACK, Arrays.asList(-7, -9, 7, 9)),
    BLACK_PAWN(PieceType.PAWN, 'p', Side.BLACK, Arrays.asList(9, 8, 7, 16));

    private final PieceType type;
    private final char label;
    private final Side side;
    private final List<Integer> moveDirections;

    Piece(final PieceType type, final char label, final Side side, final List<Integer> moveDirs) {
        this.type = type;
        this.label = label;
        this.side = side;
        moveDirections = Collections.unmodifiableList(moveDirs);
    }

    public PieceType getType() { return type; }

    public char getLabel() { return label; }

    public Side getSide() { return side; }

    public List<Integer> getMoveDirections() { return moveDirections; }

}
