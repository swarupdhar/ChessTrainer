package sdhar.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameHistory {

    private GameNode head;

    public GameHistory(final Board root) {
        head = new GameNode(root);
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

    public Optional<List<Board>> getNextVariations() {
        if (head.hasNextMainLine()) {
            final List<Board> boards = new ArrayList<>();
            if (head.getNextMainLine().hasBoard()) {
                boards.add(head.getNextMainLine().getBoard());
            }
            if (head.hasNextVariations()) {
                for (GameNode node: head.getNextVariations()) {
                    if (node.hasBoard()) {
                        boards.add(node.getBoard());
                    }
                }
            }
            return Optional.of(Collections.unmodifiableList(boards));
        }
        return Optional.empty();
    }

    public boolean goBack() {
        if (head.hasParentNode()) {
            head = head.getParentNode();
            return true;
        }
        return false;
    }

    public boolean goForward(final Selector<GameNode> s) {
        if (head.hasNextVariations()) {
            List<GameNode> nodes = new ArrayList<>(head.getNextVariations());
            nodes.add(head.getNextMainLine());
            GameNode nodeToMoveTo = s.select(nodes);
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

}
