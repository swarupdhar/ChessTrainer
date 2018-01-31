package sdhar.gui;

import javafx.scene.layout.Pane;

public class AnalysisPane extends Pane {

    private final AnalysisBoard analysisBoard;
    private final AnalysisMenu menu;

    public AnalysisPane(final double width, final double height) {
        analysisBoard = new AnalysisBoard(400);
        menu = new AnalysisMenu();

        setWidth(width);
        setHeight(height);

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            setWidth(newValue.doubleValue());
            centerAnalysisBoard();
        });

        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            setHeight(newValue.doubleValue());
            resizeAnalysisBoard();
            centerAnalysisBoard();
        });

        menu.prefHeightProperty().bind(heightProperty());
        menu.setTranslateX(0);
        getChildren().addAll(analysisBoard, menu);
    }

    private void centerAnalysisBoard() {
        analysisBoard.setTranslateX(getWidth()/2 - analysisBoard.getWidth()/2);
        analysisBoard.setTranslateY(getHeight()/2 - analysisBoard.getHeight()/2 - 20);
    }

    private void resizeAnalysisBoard() {
        if (getHeight() * 0.8 < getWidth() * 0.65) {
            analysisBoard.setHeight(getHeight() * 0.8);
            analysisBoard.setWidth(getHeight() * 0.8);
        }
    }

}
