import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdhar.gui.AnalysisPane;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AnalysisPane pane = new AnalysisPane(800, 600);
        final Scene scene = new Scene(pane);

        primaryStage.setTitle("Trainer (Alpha)");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);

        pane.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.prefHeightProperty().bind(primaryStage.heightProperty());

        primaryStage.show();
    }
}
