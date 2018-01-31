package sdhar.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class AnalysisMenu extends Pane {

    public AnalysisMenu() {
        setStyle("-fx-background-color: cornflowerblue");
        setPrefWidth(200);
        setVisible(true);

        final Button closeButton = new Button("X");
        closeButton.setTranslateX(10);
        closeButton.setTranslateY(10);
        getChildren().addAll(closeButton);
    }

}
