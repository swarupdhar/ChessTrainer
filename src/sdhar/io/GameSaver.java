package sdhar.io;

import sdhar.chess.Board;
import sdhar.chess.GameHistory;
import sdhar.chess.Piece;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSaver {

    public static void save(final GameHistory history, final String filename) throws IOException {
        final Path path = Paths.get(System.getProperty("user.home"), "chess_trainer", filename);
        final File file = new File(path.toUri());

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        try (final FileWriter fileWriter = new FileWriter(file, true);
             final PrintWriter writer = new PrintWriter(new BufferedWriter(fileWriter));
        ) {
            final List<Board> positions = history.getMainLine();
            for (final Board board: positions) {
                final List<Integer> encoding = encode(board);
                for (int i = 0; i < encoding.size(); i++) {
                    if (i == encoding.size() - 1) {
                        writer.print(encoding.get(i));
                        writer.print("\n");
                    } else {
                        writer.print(encoding.get(i));
                        writer.print(",");
                    }
                }
            }
        }
    }

    public static List<Integer> encode(final Board board) {
        final List<Integer> result = new ArrayList<>();
        final List<Piece> squares = board.getSquares();

        for (final Piece piece: squares) {
            switch (piece) {
                case BLACK_KING:
                    result.add(0b01100111);
                    break;
                case BLACK_QUEEN:
                    result.add(0b01011111);
                    break;
                case BLACK_ROOK:
                    result.add(0b01000011);
                    break;
                case BLACK_BISHOP:
                    result.add(0b01001111);
                    break;
                case BLACK_KNIGHT:
                    result.add(0b01000111);
                    break;
                case BLACK_PAWN:
                    result.add(0b01000001);
                    break;
                case WHITE_KING:
                    result.add(0b10100000);
                    break;
                case WHITE_QUEEN:
                    result.add(0b10010000);
                    break;
                case WHITE_ROOK:
                    result.add(0b10000010);
                    break;
                case WHITE_BISHOP:
                    result.add(0b10001000);
                    break;
                case WHITE_KNIGHT:
                    result.add(0b10000100);
                    break;
                case WHITE_PAWN:
                    result.add(0b10000001);
                    break;

                case NONE:
                    result.add(0);
                    break;
            }
        }

        return Collections.unmodifiableList(result);
    }

}
