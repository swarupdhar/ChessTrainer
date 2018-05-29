package sdhar.training;

import org.tensorflow.*;
import sdhar.chess.Board;
import sdhar.chess.GameHistory;
import sdhar.chess.Piece;
import sdhar.engine.Engine;

import java.util.ArrayList;
import java.util.List;

public class Trainer {

    private GameHistory history;
    private List<Board> savedPositions;
    private Engine engine;

    public Trainer(final GameHistory history) {
        engine = new Engine("/Library/Application Support/Adobe/Adobe PCD/cache/Stash/stockfish-9-mac/Mac/stockfish-9-64");
        savedPositions = new ArrayList<>();
        this.history = history;

        try (SavedModelBundle b =
                     SavedModelBundle.load("/Users/student/Desktop/TensorChessEvaluator/results/auto_model64_v1", "serve")
        ) {
            Session s = b.session();
            float[][] matrix=new float[1][64];

            history.goForward(gameNodes -> gameNodes.get(0));
            history.goForward(gameNodes -> gameNodes.get(0));

            Tensor input = Tensor.create(matrix);
            Tensor result = s.runner().feed("input", input).fetch("output").run().get(0);
            float[][] res=new float[1][1];
            res[0]=new float[1];
            result.copyTo(res);
            System.out.println("results: "+res[0][0]);
        }
    }

}
