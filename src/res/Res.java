package res;

import javafx.scene.image.Image;
import sdhar.chess.Piece;

import java.util.Optional;

public class Res {
    private Res(){}

    public static class PieceImages {
        static final Image BLACK_BISHOP = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_bishop.png"));
        static final Image WHITE_BISHOP = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_bishop.png"));

        static final Image BLACK_KNIGHT = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_knight.png"));
        static final Image WHITE_KNIGHT = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_knight.png"));

        static final Image BLACK_ROOK = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_rook.png"));
        static final Image WHITE_ROOK = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_rook.png"));

        static final Image BLACK_KING = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_king.png"));
        static final Image WHITE_KING = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_king.png"));

        static final Image BLACK_QUEEN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_queen.png"));
        static final Image WHITE_QUEEN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_queen.png"));

        static final Image BLACK_PAWN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_pawn.png"));
        static final Image WHITE_PAWN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_pawn.png"));
    }

    public static Optional<Image> getImageForPiece(final Piece piece) {
        switch (piece) {
            case WHITE_ROOK: return Optional.of(PieceImages.WHITE_ROOK);
            case WHITE_BISHOP: return Optional.of(PieceImages.WHITE_BISHOP);
            case WHITE_KNIGHT: return Optional.of(PieceImages.WHITE_KNIGHT);
            case WHITE_QUEEN: return Optional.of(PieceImages.WHITE_QUEEN);
            case WHITE_KING: return Optional.of(PieceImages.WHITE_KING);
            case WHITE_PAWN: return Optional.of(PieceImages.WHITE_PAWN);

            case BLACK_ROOK: return Optional.of(PieceImages.BLACK_ROOK);
            case BLACK_BISHOP: return Optional.of(PieceImages.BLACK_BISHOP);
            case BLACK_KNIGHT: return Optional.of(PieceImages.BLACK_KNIGHT);
            case BLACK_QUEEN: return Optional.of(PieceImages.BLACK_QUEEN);
            case BLACK_KING: return Optional.of(PieceImages.BLACK_KING);
            case BLACK_PAWN: return Optional.of(PieceImages.BLACK_PAWN);

            case NONE:
            default: return Optional.empty();
        }
    }

}
