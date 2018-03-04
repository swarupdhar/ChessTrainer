package res;

import javafx.scene.image.Image;
import sdhar.chess.Piece;

import java.util.Optional;

public class Res {
    private Res(){}

    private static final Image BLACK_BISHOP = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_bishop.png"));
    private static final Image WHITE_BISHOP = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_bishop.png"));

    private static final Image BLACK_KNIGHT = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_knight.png"));
    private static final Image WHITE_KNIGHT = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_knight.png"));

    private static final Image BLACK_ROOK = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_rook.png"));
    private static final Image WHITE_ROOK = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_rook.png"));

    private static final Image BLACK_KING = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_king.png"));
    private static final Image WHITE_KING = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_king.png"));

    private static final Image BLACK_QUEEN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_queen.png"));
    private static final Image WHITE_QUEEN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_queen.png"));

    private static final Image BLACK_PAWN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/black_pawn.png"));
    private static final Image WHITE_PAWN = new Image(Res.class.getClassLoader().getResourceAsStream("res/wikiPieces/white_pawn.png"));

    public static final Image ICON_HAMBURG = new Image(Res.class.getClassLoader().getResourceAsStream("res/iconImages/icon-hamburger.png"));
    public static final Image ICON_X = new Image(Res.class.getClassLoader().getResourceAsStream("res/iconImages/icon-X.png"));

    public static Optional<Image> getImageForPiece(final Piece piece) {
        switch (piece) {
            case WHITE_ROOK: return Optional.of(WHITE_ROOK);
            case WHITE_BISHOP: return Optional.of(WHITE_BISHOP);
            case WHITE_KNIGHT: return Optional.of(WHITE_KNIGHT);
            case WHITE_QUEEN: return Optional.of(WHITE_QUEEN);
            case WHITE_KING: return Optional.of(WHITE_KING);
            case WHITE_PAWN: return Optional.of(WHITE_PAWN);

            case BLACK_ROOK: return Optional.of(BLACK_ROOK);
            case BLACK_BISHOP: return Optional.of(BLACK_BISHOP);
            case BLACK_KNIGHT: return Optional.of(BLACK_KNIGHT);
            case BLACK_QUEEN: return Optional.of(BLACK_QUEEN);
            case BLACK_KING: return Optional.of(BLACK_KING);
            case BLACK_PAWN: return Optional.of(BLACK_PAWN);

            case NONE:
            default: return Optional.empty();
        }
    }

}
