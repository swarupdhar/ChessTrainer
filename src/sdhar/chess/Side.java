package sdhar.chess;

public enum Side {
    WHITE,
    BLACK,
    NONE;

    Side flip() {
        switch (this) {
            case WHITE: return BLACK;
            case BLACK: return WHITE;
        }
        return NONE;
    }

}
