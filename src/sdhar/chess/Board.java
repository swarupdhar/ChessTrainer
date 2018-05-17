package sdhar.chess;

import java.util.*;

public class Board {
    private static final boolean[] A_FILE = setFile(0);
    private static final boolean[] B_FILE = setFile(1);
    private static final boolean[] C_FILE = setFile(2);
    private static final boolean[] D_FILE = setFile(3);
    private static final boolean[] E_FILE = setFile(4);
    private static final boolean[] F_FILE = setFile(5);
    private static final boolean[] G_FILE = setFile(6);
    private static final boolean[] H_FILE = setFile(7);
    private static final boolean[] FIRST_RANK = setRank(0); // A8 - H8
    private static final boolean[] SECOND_RANK = setRank(8); // A7 - H7
    private static final boolean[] THIRD_RANK = setRank(16);
    private static final boolean[] FOURTH_RANK = setRank(24);
    private static final boolean[] FIFTH_RANK = setRank(32);
    private static final boolean[] SIXTH_RANK = setRank(40);
    private static final boolean[] SEVENTH_RANK = setRank(48); // A2 - H2
    private static final boolean[] EIGHTH_RANK = setRank(56); //A1 - H1

    /**
     * Outputs an array representing that file
     *
     * @param fileIndex index the file starts on
     * @return boolean array representing the file
     */
    private static boolean[] setFile(final int fileIndex) {
        final boolean[] result = new boolean[64];
        for (int i = fileIndex; i < 64; i += 8) {
            result[i] = true;
        }
        return result;
    }

    /**
     * Outputs an array representing the rank starting with the specified index number
     *
     * @param rankIndex Index of the rank starting at 0
     * @return boolean array representing the rank
     */
    private static boolean[] setRank(final int rankIndex) {
        final boolean[] result = new boolean[64];
        for (int i = 0; i < 8; i++) {
            result[rankIndex + i] = true;
        }
        return result;
    }

    static boolean isValidIndex(final int index) { return index >= 0 && index < 64; }

    static boolean isAFile(final int index) { return isValidIndex(index) && A_FILE[index]; }
    static boolean isBFile(final int index) { return isValidIndex(index) && B_FILE[index]; }
    static boolean isGFile(final int index) { return isValidIndex(index) && G_FILE[index]; }
    static boolean isHFile(final int index) { return isValidIndex(index) && H_FILE[index]; }

    static boolean isFirstRank(final int index) { return isValidIndex(index) && FIRST_RANK[index]; }
    static boolean isSecondRank(final int index) { return isValidIndex(index) && SECOND_RANK[index]; }
    static boolean isSeventhRank(final int index) { return isValidIndex(index) && SEVENTH_RANK[index]; }
    static boolean isEighthRank(final int index) { return isValidIndex(index) && EIGHTH_RANK[index]; }

    private final CastleRights whiteCastleRight;
    private final CastleRights blackCastleRight;
    private final int enpassantIndex;
    private final Side turnToMove;
    private final Piece[] squares;
    private final int whiteKingIndex;
    private final int blackKingIndex;
    private final int promotionSquareIndex;

    Board(
            final Map<Integer, Piece> pieceMap,
            final Side turnToMove,
            final CastleRights whiteCastleRight,
            final CastleRights blackCastleRight,
            final int enpassantIndex,
            final int promotionSquareIndex
    ) {
        this.turnToMove = turnToMove;
        this.whiteCastleRight = whiteCastleRight;
        this.blackCastleRight = blackCastleRight;
        this.enpassantIndex = enpassantIndex;
        this.squares = new Piece[64];

        int wKIndex = -1;
        int bKIndex = -1;

        for (int i = 0; i < 64; i++) {
            final Piece piece = pieceMap.getOrDefault(i, Piece.NONE);
            squares[i] = piece;
            switch (piece) {
                case BLACK_KING:
                    bKIndex = i;
                    break;
                case WHITE_KING:
                    wKIndex = i;
                    break;
            }
        }

        if (wKIndex == -1 || bKIndex == -1)
            throw new IllegalArgumentException("The board must contain both kings!");

        whiteKingIndex = wKIndex;
        blackKingIndex = bKIndex;
        this.promotionSquareIndex = promotionSquareIndex;
    }

    private Board(
            final Piece[] pieces,
            final Side turnToMove,
            final CastleRights whiteCastleRight,
            final CastleRights blackCastleRight,
            final int enpassantIndex,
            final int promotionSquareIndex
    ) {
        this.turnToMove = turnToMove;
        this.whiteCastleRight = whiteCastleRight;
        this.blackCastleRight = blackCastleRight;
        this.enpassantIndex = enpassantIndex;
        squares = new Piece[64];

        int wKIndex = -1;
        int bKIndex = -1;

        for (int i = 0; i < 64; i++) {
            final Piece piece = pieces[i];
            squares[i] = piece;
            switch (piece) {
                case BLACK_KING:
                    bKIndex = i;
                    break;
                case WHITE_KING:
                    wKIndex = i;
                    break;
            }
        }

        if (wKIndex == -1 || bKIndex == -1) {
            throw new IllegalArgumentException("The board must contain both kings!");
        }

        whiteKingIndex = wKIndex;
        blackKingIndex = bKIndex;

        this.promotionSquareIndex = promotionSquareIndex;
    }

    public Side getTurnToMove() { return turnToMove; }

    Optional<Integer> getEnpassantIndex() {
        if (enpassantIndex == -1) return Optional.empty();
        return Optional.of(enpassantIndex);
    }

    CastleRights getCastleRight(final Side side) {
        if (side == Side.WHITE) return whiteCastleRight;
        return blackCastleRight;
    }

    int getKingIndex(final Side side) {
        if (side == Side.WHITE) return whiteKingIndex;
        return blackKingIndex;
    }

    Piece getPiece(final int index) {
        if (index < 0 || index > 63) return Piece.NONE;
        return squares[index];
    }

    public Piece getPiece(final Square square) {
        return squares[square.index];
    }

    public Optional<Integer> getPromotionSquare() {
        if (promotionSquareIndex == -1) return Optional.empty();
        return Optional.of(promotionSquareIndex);
    }

    public List<Piece> getCopyOfSquares() {
        Piece[] copy = new Piece[64];
        System.arraycopy(squares, 0, copy, 0, squares.length);
        return Arrays.asList(copy);
    }

    public List<Piece> getSquares() { return Collections.unmodifiableList(Arrays.asList(squares)); }

    public Optional<Board> makeMove(final Square startingSquare, final Square endingSquare) {
        final int startingIndex = startingSquare.index;
        final int endingIndex = endingSquare.index;

        final Piece pieceMoving = getPiece(startingIndex);

        if (pieceMoving == Piece.NONE) return Optional.empty();
        if (pieceMoving.getSide() != turnToMove) return Optional.empty();

        final List<Integer> legalMoves = MoveGenerator.legalMoves(this, startingIndex);
        if (!legalMoves.contains(endingIndex)) return Optional.empty();

        final Piece[] newSquares = Arrays.copyOf(squares, 64);

        newSquares[endingIndex] = pieceMoving;
        newSquares[startingIndex] = Piece.NONE;

        int newEnpassantIndex = -1;
        int newPromotionSquareIndex = -1;
        CastleRights newCastleRights = CastleRights.BOTH;

        switch (pieceMoving) {
            case WHITE_PAWN:
                // check enpassant
                if (endingIndex == enpassantIndex) {
                    newSquares[endingIndex + 8] = Piece.NONE;
                }
                // set enpassant
                else if (endingIndex == startingIndex - 16) {
                    newEnpassantIndex = startingIndex - 8;
                }
                // check for promotion
                else if (isFirstRank(endingIndex)) {
                    newPromotionSquareIndex = endingIndex;
                }
                break;
            case BLACK_PAWN:
                // check enpassant
                if (endingIndex == enpassantIndex) {
                    newSquares[endingIndex - 8] = Piece.NONE;
                }
                // set enpassant
                else if (endingIndex == startingIndex + 16) {
                    newEnpassantIndex = startingIndex + 8;
                }
                // check for promotion
                else if (isEighthRank(endingIndex)) {
                    newPromotionSquareIndex = endingIndex;
                }
                break;
            case WHITE_KING:
            case BLACK_KING:
                // check for castling
                if (endingIndex == startingIndex + 2) {
                    newSquares[endingIndex - 1] = newSquares[endingIndex + 1];
                    newSquares[endingIndex + 1] = Piece.NONE;
                } else if (endingIndex == startingIndex - 2) {
                    newSquares[endingIndex + 1] = newSquares[endingIndex - 2];
                    newSquares[endingIndex - 2] = Piece.NONE;
                }
                newCastleRights = CastleRights.NONE;
                break;
            case WHITE_ROOK:
            case BLACK_ROOK:
                // set appropriate castle rights
                newCastleRights = computeCastleRightsFromRookMovement(
                        getCastleRight(pieceMoving.getSide()),
                        startingIndex
                );
                break;
        }

        return Optional.of(
                new Board(
                        newSquares,
                        turnToMove.flip(),
                        turnToMove == Side.WHITE? newCastleRights: whiteCastleRight,
                        turnToMove == Side.BLACK? newCastleRights: blackCastleRight,
                        newEnpassantIndex,
                        newPromotionSquareIndex
                )
        );
    }

    boolean equals(final Board other) {
        for (int i = 0; i < 64; i++) {
            if (squares[i] != other.squares[i]) {
                return false;
            }
        }

        return whiteCastleRight == other.whiteCastleRight &&
                blackCastleRight == other.blackCastleRight &&
                turnToMove == other.turnToMove &&
                enpassantIndex == other.enpassantIndex;
    }

    private static CastleRights computeCastleRightsFromRookMovement(
            final CastleRights currentRight,
            final int startingIndex
    ) {
        if (isAFile(startingIndex)) {
            switch (currentRight) {
                case BOTH:
                case KING_SIDE:
                    return CastleRights.KING_SIDE;
                case QUEEN_SIDE:
                    return CastleRights.NONE;
                default:
                    return CastleRights.NONE;
            }
        } else if (isHFile(startingIndex)) {
            switch (currentRight) {
                case BOTH:
                case QUEEN_SIDE:
                    return CastleRights.QUEEN_SIDE;
                case KING_SIDE:
                    return CastleRights.NONE;
                default:
                    return CastleRights.NONE;
            }
        }

        return currentRight;
    }

}
