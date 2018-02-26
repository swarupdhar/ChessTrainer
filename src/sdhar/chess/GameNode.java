package sdhar.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class GameNode {

    private final Board board;
    private float moveNumber;

    private GameNode parentNode;
    private GameNode nextMainLine;
    private List<GameNode> nextVariations = new ArrayList<>();

    GameNode(final Board board) {
        this.board = board;
        moveNumber = 0;
        parentNode = null;
        nextMainLine = null;
    }

    Board getBoard() { return board; }
    boolean hasBoard() { return board != null; }

    float getMoveNumber() { return moveNumber; }

    GameNode getParentNode() { return parentNode; }
    boolean hasParentNode() { return parentNode != null; }

    GameNode getNextMainLine() { return nextMainLine; }
    boolean hasNextMainLine() { return nextMainLine != null; }

    boolean hasNextVariations() { return nextVariations.size() > 0; }
    List<GameNode> getNextVariations() { return Collections.unmodifiableList(nextVariations); }

    GameNode addNext(final GameNode node) {
        if (hasNextMainLine()) {
            for (final GameNode n: nextVariations) {
                if (n.board.equals(node.board)) {
                    return n;
                }
            }
            node.parentNode = this;
            node.moveNumber = moveNumber + 0.5f;
            nextVariations.add(node);
            return node;
        }
        node.parentNode = this;
        node.moveNumber = moveNumber + 0.5f;
        nextMainLine = node;
        return node;
    }

}
