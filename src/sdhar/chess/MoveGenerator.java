package sdhar.chess;

import java.util.*;

class MoveGenerator {

    private static List<Integer> allMoves(final Board board, final int index) {
        final List<Integer> moves = new ArrayList<>();

        final Piece piece = board.getPiece(index);

        switch (piece) {
            case WHITE_KING:
            case BLACK_KING:
                moves.addAll(generateKingMoves(board, piece, index));
                break;
            case WHITE_QUEEN:
            case BLACK_QUEEN:
                moves.addAll(generateQueenMoves(board, piece, index));
                break;
            case WHITE_ROOK:
            case BLACK_ROOK:
                moves.addAll(generateRookMoves(board, piece, index));
                break;
            case WHITE_BISHOP:
            case BLACK_BISHOP:
                moves.addAll(generateBishopMoves(board, piece, index));
                break;
            case WHITE_KNIGHT:
            case BLACK_KNIGHT:
                moves.addAll(generateKnightMoves(board, piece, index));
                break;
            case WHITE_PAWN:
                moves.addAll(generateWhitePawnMoves(board, piece, index));
                break;
            case BLACK_PAWN:
                moves.addAll(generateBlackPawnMoves(board, piece, index));
                break;
            default:
                break;
        }

        return Collections.unmodifiableList(moves);
    }

    static List<Integer> legalMoves(final Board board, final int index) {
        final List<Integer> moves = new ArrayList<>();
        final List<Integer> result = new ArrayList<>();
        moves.addAll(allMoves(board, index));

        final List<Piece> squares = board.getSquares();
        final Piece piece = squares.get(index);

        for (final int move: moves) {
            final Piece temp = squares.get(move);
            squares.set(move, piece);
            squares.set(index, Piece.NONE);

            final Piece[] enpassedPawn = {Piece.NONE};
            final int[] enIndex = {-1};
            int castlingRookIndex = -1;
            int rookCastleToIndex = -1;

            switch (piece) {
                case WHITE_PAWN:
                case BLACK_PAWN:
                    board.getEnpassantIndex().ifPresent(enpassIndex -> {
                        if (enpassIndex == move) {
                            enpassedPawn[0] = squares.get(enpassIndex);
                            enIndex[0] = enpassIndex;
                        }
                    });
                    break;
                case WHITE_KING:
                case BLACK_KING:
                    if (move == index + 2) {
                        castlingRookIndex = move + 1;
                        rookCastleToIndex = move - 1;

                        final Piece rook = squares.get(castlingRookIndex);
                        squares.set(castlingRookIndex, Piece.NONE);
                        squares.set(rookCastleToIndex, rook);
                    } else if (move == index - 2) {
                        castlingRookIndex = move - 2;
                        rookCastleToIndex = move + 1;

                        final Piece rook = squares.get(castlingRookIndex);
                        squares.set(castlingRookIndex, Piece.NONE);
                        squares.set(rookCastleToIndex, rook);
                    }
                    break;
            }

            if (!isEmptyPiece(enpassedPawn[0])) {
                squares.set(enIndex[0], Piece.NONE);
            }

            if (castlingRookIndex != -1) {
                squares.set(castlingRookIndex, squares.get(rookCastleToIndex));
                squares.set(rookCastleToIndex, Piece.NONE);
            }

            final int kingIndex = piece.getType() == PieceType.KING?
                    move : board.getKingIndex(piece.getSide());

            if (!isKingInCheck(squares, kingIndex)) {
                result.add(move);
            }

            squares.set(index, piece);
            squares.set(move, temp);
            if (!isEmptyPiece(enpassedPawn[0])) {
                squares.set(enIndex[0], enpassedPawn[0]);
            }
        }

        return Collections.unmodifiableList(result);
    }

    private static boolean isKingInCheck(final List<Piece> squares, final int kingIndex) {
        final Piece king = squares.get(kingIndex);

        for (final int dir : king.getMoveDirections()) {
            if (kingEdgeCase(kingIndex, dir)) {
                continue;
            }

            final int candidateIndex = kingIndex + dir;
            if (Board.isValidIndex(candidateIndex)) {
                final Piece candidatePiece = squares.get(candidateIndex);
                if (!isEmptyPiece(candidatePiece) && areOpponents(king, candidatePiece)) {
                    switch (candidatePiece.getType()) {
                        case QUEEN:
                        case KING:
                            return true;
                        case BISHOP:
                            if (dir == -9 || dir == -7 || dir == 9 || dir == 7) return true;
                            break;
                        case ROOK:
                            if (dir == -8 || dir == -1 || dir == 1 || dir == 8) return true;
                            break;
                        case PAWN:
                            if (king.getSide() == Side.WHITE) {
                                if (dir == -9 || dir == -7) return true;
                            } else {
                                if (dir == 9 || dir == 7) return true;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        for (final int dir : Piece.WHITE_BISHOP.getMoveDirections()) {
            int candidateIndex = kingIndex;
            while (Board.isValidIndex(candidateIndex)) {
                if (bishopEdgeCase(candidateIndex, dir)) {
                    break;
                }

                candidateIndex += dir;
                if (Board.isValidIndex(candidateIndex)) {
                    final Piece candidatePiece = squares.get(candidateIndex);

                    if (!isEmptyPiece(candidatePiece)) {
                        if (areOpponents(king, candidatePiece)) {
                            switch (candidatePiece.getType()) {
                                case BISHOP:
                                case QUEEN:
                                    return true;
                                default:
                                    break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        for (final int dir : Piece.WHITE_ROOK.getMoveDirections()) {
            int candidateIndex = kingIndex;
            while (Board.isValidIndex(candidateIndex)) {
                if (rookEdgeCase(candidateIndex, dir)) {
                    break;
                }
                candidateIndex += dir;
                if (Board.isValidIndex(candidateIndex)) {
                    final Piece candidatePiece = squares.get(candidateIndex);

                    if (!isEmptyPiece(candidatePiece)) {
                        if (areOpponents(king, candidatePiece)) {
                            switch (candidatePiece.getType()) {
                                case ROOK:
                                case QUEEN:
                                    return true;
                                default:
                                    break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        for (final int dir : Piece.WHITE_KNIGHT.getMoveDirections()) {
            if (knightEdgeCase(kingIndex, dir)) {
                continue;
            }

            final int candidateIndex = kingIndex + dir;

            if (Board.isValidIndex(candidateIndex)) {
                final Piece candidatePiece = squares.get(candidateIndex);
                if (!isEmptyPiece(candidatePiece) && areOpponents(king, candidatePiece)) {
                    if (candidatePiece.getType() == PieceType.KNIGHT) return true;
                }
            }
        }

        return false;
    }

    private static List<Integer> generateBishopMoves (
            final Board board,
            final Piece bishop,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        int candidateIndex;
        for (final int dir: bishop.getMoveDirections()) {
            candidateIndex = index;
            while (Board.isValidIndex(candidateIndex)) {
                if (bishopEdgeCase(index, dir)) {
                    break;
                }

                candidateIndex += dir;
                if (Board.isValidIndex(candidateIndex)) {
                    final Piece candidatePiece = board.getPiece(candidateIndex);
                    if (isEmptyPiece(candidatePiece)) {
                        moves.add(candidateIndex);
                    } else {
                        if (areOpponents(bishop, candidatePiece)) moves.add(candidateIndex);
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    private static List<Integer> generateRookMoves (
            final Board board,
            final Piece rook,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        int candidateIndex;
        for (final int dir: rook.getMoveDirections()) {
            if (rookEdgeCase(index, dir)) {
                continue;
            }

            candidateIndex = index;
            while (Board.isValidIndex(candidateIndex)) {
                candidateIndex += dir;
                if (Board.isValidIndex(candidateIndex)) {
                    final Piece candidatePiece = board.getPiece(candidateIndex);
                    if (isEmptyPiece(candidatePiece)) {
                        moves.add(candidateIndex);
                    } else {
                        if (areOpponents(rook, candidatePiece)) moves.add(candidateIndex);
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    private static List<Integer> generateQueenMoves (
            final Board board,
            final Piece queen,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        int candidateIndex;
        for (final int dir: queen.getMoveDirections()) {
            candidateIndex = index;
            while (Board.isValidIndex(candidateIndex)) {
                if (queenEdgeCase(index, dir)) {
                    break;
                }

                candidateIndex += dir;
                if (Board.isValidIndex(candidateIndex)) {
                    final Piece candidatePiece = board.getPiece(candidateIndex);
                    if (isEmptyPiece(candidatePiece)) {
                        moves.add(candidateIndex);
                    } else {
                        if (areOpponents(queen, candidatePiece)) moves.add(candidateIndex);
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    private static List<Integer> generateKnightMoves(
            final Board board,
            final Piece knight,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        for (final int dir: knight.getMoveDirections()) {
            if (knightEdgeCase(index, dir)) {
                continue;
            }

            final int candidateIndex = index + dir;
            if (Board.isValidIndex(candidateIndex)) {
                final Piece candidatePiece = board.getPiece(candidateIndex);
                if (isEmptyPiece(candidatePiece)) {
                    moves.add(candidateIndex);
                } else {
                    if (areOpponents(knight, candidatePiece)) {
                        moves.add(candidateIndex);
                    }
                }
            }
        }

        return moves;
    }

    private static List<Integer> generateKingMoves (
            final Board board,
            final Piece king,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        for (final int dir: king.getMoveDirections()) {
            if (kingEdgeCase(index, dir)){
                continue;
            }

            final int candidateIndex = index + dir;
            if (Board.isValidIndex(candidateIndex)) {
                final Piece candidatePiece = board.getPiece(candidateIndex);
                if (isEmptyPiece(candidatePiece)) {
                    moves.add(candidateIndex);
                } else {
                    if (areOpponents(king, candidatePiece)) {
                        moves.add(candidateIndex);
                    }
                }
            }
        }

        switch (king.getSide()) {
            case WHITE:
                if (whiteKingSideCastlePossible(board, king, index)) {
                    moves.add(62);
                }
                if (whiteQueenSideCastlePossible(board, king, index)) {
                    moves.add(58);
                }
                break;
            case BLACK:
                if (blackKingSideCastlePossible(board, king, index)) {
                    moves.add(6);
                }
                if (blackQueenSideCastlePossible(board, king, index)) {
                    moves.add(2);
                }
                break;
        }

        return moves;
    }

    private static boolean whiteKingSideCastlePossible(
            final Board board,
            final Piece king,
            final int index
    ) {
        final CastleRights rights = board.getCastleRight(king.getSide());
        if (rights == CastleRights.NONE) return false;

        if (index == 60) {
            if (rights == CastleRights.BOTH || rights == CastleRights.KING_SIDE) {
                final Piece cornerPiece = board.getPiece(63);
                if (cornerPiece == Piece.WHITE_ROOK &&
                        isEmptyPiece(board.getPiece(61)) &&
                        isEmptyPiece(board.getPiece(62))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean blackKingSideCastlePossible(
            final Board board,
            final Piece king,
            final int index
    ) {
        final CastleRights rights = board.getCastleRight(king.getSide());
        if (rights == CastleRights.NONE) return false;

        if (index == 4 && king == Piece.BLACK_KING) {
            if (rights == CastleRights.BOTH || rights == CastleRights.KING_SIDE) {
                final Piece cornerPiece = board.getPiece(7);
                if (cornerPiece == Piece.BLACK_ROOK &&
                        isEmptyPiece(board.getPiece(5)) &&
                        isEmptyPiece(board.getPiece(6))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean whiteQueenSideCastlePossible(
            final Board board,
            final Piece king,
            final int index
    ) {
        final CastleRights rights = board.getCastleRight(king.getSide());
        if (rights == CastleRights.NONE) return false;

        if (index == 60) {
            if (rights == CastleRights.BOTH || rights == CastleRights.QUEEN_SIDE) {
                final Piece cornerPiece = board.getPiece(56);
                if (cornerPiece == Piece.WHITE_ROOK &&
                        isEmptyPiece(board.getPiece(59)) &&
                        isEmptyPiece(board.getPiece(58)) &&
                        isEmptyPiece(board.getPiece(57))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean blackQueenSideCastlePossible(
            final Board board,
            final Piece king,
            final int index
    ) {
        final CastleRights rights = board.getCastleRight(king.getSide());
        if (rights == CastleRights.NONE) return false;

        if (index == 4 && king == Piece.BLACK_KING) {
            if (rights == CastleRights.BOTH || rights == CastleRights.QUEEN_SIDE) {
                final Piece cornerPiece = board.getPiece(0);
                if (cornerPiece == Piece.BLACK_ROOK &&
                        isEmptyPiece(board.getPiece(1)) &&
                        isEmptyPiece(board.getPiece(2)) &&
                        isEmptyPiece(board.getPiece(3))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static List<Integer> generateWhitePawnMoves(
            final Board board,
            final Piece pawn,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        for (final int dir: pawn.getMoveDirections()) {
            if (whitePawnEdgeCase(index, dir)) {
                continue;
            }

            final int candidateIndex = index + dir;
            final Piece candidatePiece = board.getPiece(candidateIndex);
            switch (dir) {
                case -16:
                    if (isEmptyPiece(candidatePiece) && isEmptyPiece(board.getPiece(index-8))) {
                        moves.add(candidateIndex);
                    }
                    break;
                case -9:
                case -7:
                    if (!isEmptyPiece(candidatePiece) && areOpponents(pawn, candidatePiece)) {
                        moves.add(candidateIndex);
                    }
                    break;
                default:
                    if (isEmptyPiece(candidatePiece)) {
                        moves.add(candidateIndex);
                    }
            }
        }

        board.getEnpassantIndex().ifPresent(enpassantIndex -> {
            if (index - 7 == enpassantIndex || index - 9 == enpassantIndex) {
                moves.add(enpassantIndex);
            }
        });

        return moves;
    }

    private static List<Integer> generateBlackPawnMoves(
            final Board board,
            final Piece pawn,
            final int index
    ) {
        final List<Integer> moves = new ArrayList<>();

        for (final int dir: pawn.getMoveDirections()) {
            if (blackPawnEdgeCase(index, dir)) {
                continue;
            }

            final int candidateIndex = index + dir;
            final Piece candidatePiece = board.getPiece(candidateIndex);
            switch (dir) {
                case 16:
                    if (isEmptyPiece(candidatePiece) && isEmptyPiece(board.getPiece(index+8))) {
                        moves.add(candidateIndex);
                    }
                    break;
                case 9:
                case 7:
                    if (!isEmptyPiece(candidatePiece) && areOpponents(pawn, candidatePiece)) {
                        moves.add(candidateIndex);
                    }
                    break;
                default:
                    if (isEmptyPiece(candidatePiece)) {
                        moves.add(candidateIndex);
                    }
            }
        }

        board.getEnpassantIndex().ifPresent(enpassantIndex -> {
            if (index + 7 == enpassantIndex || index + 9 == enpassantIndex) {
                moves.add(enpassantIndex);
            }
        });

        return moves;
    }

    private static boolean isEmptyPiece(final Piece candidate) {
        return candidate == Piece.NONE;
    }

    private static boolean areOpponents(final Piece piece, final Piece candidate) {
        return piece.getSide() != candidate.getSide();
    }

    private static boolean bishopEdgeCase(final int index, final int dir) {
        if (Board.isAFile(index)) {
            if (dir == -9 || dir == 7) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == 9 || dir == -7) return true;
        }
        return false;
    }

    private static boolean rookEdgeCase(final int index, final int dir) {
        if (Board.isAFile(index)) {
            if (dir == -1) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == 1) return true;
        }
        return false;
    }

    private static boolean queenEdgeCase(final int index, final int dir) {
        return bishopEdgeCase(index, dir) || rookEdgeCase(index, dir);
    }

    private static boolean knightEdgeCase(final int index, final int dir) {
        if (Board.isAFile(index)) {
            if (dir == -17 || dir == -10 || dir == 6 || dir == 15) return true;
        }
        if (Board.isBFile(index)) {
            if (dir == -10 || dir == 6) return true;
        }
        if (Board.isGFile(index)) {
            if (dir == -6 || dir == 10) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == 17 || dir == 10 || dir == -6 || dir == -15) return true;
        }
        return false;
    }

    private static boolean kingEdgeCase(final int index, final int dir) {
        if (Board.isAFile(index)) {
            if (dir == -9 || dir == -1 || dir == 7) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == 9 || dir == 1 || dir == -7) return true;
        }
        return false;
    }

    private static boolean whitePawnEdgeCase(final int index, final int dir) {
        if (!Board.isSeventhRank(index)) {
            if (dir == -16) return true;
        }
        if (Board.isAFile(index)) {
            if (dir == -9) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == -7) return true;
        }
        return false;
    }

    private static boolean blackPawnEdgeCase(final int index, final int dir) {
        if (!Board.isSecondRank(index)) {
            if (dir == 16) return true;
        }
        if (Board.isHFile(index)) {
            if (dir == 9) return true;
        }
        if (Board.isAFile(index)) {
            if (dir == 7) return true;
        }
        return false;
    }

}
