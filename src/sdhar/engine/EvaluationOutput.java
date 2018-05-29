package sdhar.engine;

public class EvaluationOutput {

    private final int depth;
    private final int score;
    private String bestMove;
    private String currMove;
    private String pv;

    EvaluationOutput(final int depth, final int score) {
        this.depth = depth;
        this.score = score;
    }

    void setBestMove(final String move) {
        if (bestMove != null) return;
        bestMove = move;
    }

    void setCurrMove(final String move) {
        if (currMove != null) return;
        currMove = move;
    }

    String getCurrMove() {
        if (currMove == null) return "";
        return currMove;
    }

    void setPv(final String line) {
        if (pv != null) return;
        pv = line;
    }

    public String getPv() {
        if (pv == null) return "";
        return pv;
    }

    public String getBestMove() {
        if (bestMove == null) return "";
        return bestMove;
    }

    public int getDepth() { return depth; }

    public int getScore() { return score; }

}
