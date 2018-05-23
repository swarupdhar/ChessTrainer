package sdhar.engine;

import java.util.Optional;

public class EvaluationOutput {

    private final int depth;
    private final int score;
    private String bestMove;

    EvaluationOutput(final int depth, final int score) {
        this.depth = depth;
        this.score = score;
    }

    void setBestMove(final String move) {
        if (bestMove != null) return;
        bestMove = move;
    }

    public Optional<String> getBestMove() {
        if (bestMove == null) return Optional.empty();
        return Optional.of(bestMove);
    }

    public int getDepth() { return depth; }

    public int getScore() { return score; }

}
