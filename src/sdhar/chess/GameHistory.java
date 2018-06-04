package sdhar.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class GameHistory {

    private final GameNode root;
    private GameNode head;

    public GameHistory(final Board rootBoard) {
        this.root = new GameNode(rootBoard);
        head = this.root;
    }

    public void addNext(final Board board) {
        final GameNode newNode = new GameNode(board);
        head = head.addNext(newNode);
    }

    public Optional<Board> getCurrentBoard() {
        if (head.hasBoard()) return Optional.of(head.getBoard());
        return Optional.empty();
    }

    public Optional<Board> getPreviousBoard() {
        if (head.hasParentNode()) {
            GameNode node = head.getParentNode();
            if (node.hasBoard()){
                return Optional.of(node.getBoard());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Board> getNextLines() {
        if (head.hasNextMainLine()) {
            final List<Board> boards = new ArrayList<>();
            if (head.getNextMainLine().hasBoard()) {
                boards.add(head.getNextMainLine().getBoard());
            }
            if (head.hasNextVariations()) {
                for (final GameNode node: head.getNextVariations()) {
                    if (node.hasBoard()) {
                        boards.add(node.getBoard());
                    }
                }
            }
            return Collections.unmodifiableList(boards);
        }
        return new ArrayList<>();
    }

    public boolean goBack() {
        if (head.hasParentNode()) {
            head = head.getParentNode();
            return true;
        }
        return false;
    }

    public boolean goForward(final Function<List<GameNode>, GameNode> func) {
        if (head.hasNextVariations()) {
            List<GameNode> nodes = new ArrayList<>(head.getNextVariations());
            nodes.add(head.getNextMainLine());
            GameNode nodeToMoveTo = func.apply(nodes);
            if (nodeToMoveTo != null) {
                head = nodeToMoveTo;
                return true;
            }
            return false;
        }
        if (head.hasNextMainLine()) {
            head = head.getNextMainLine();
            return true;
        }
        return false;
    }

    public List<Board> getMainLine() {
        final List<Board> result = new ArrayList<>();

        GameNode current = this.root;

        while (current.hasNextMainLine()) {
            result.add(current.getBoard());
            current = current.getNextMainLine();
        }

        return Collections.unmodifiableList(result);
    }

}
