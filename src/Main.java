import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdhar.gui.AnalysisPane;
import sdhar.gui.PlayComputerPane;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final AnalysisPane analysisPane = new AnalysisPane(800, 600, event -> {
            switch (event) {
                case START_TRAINING_EVENT:
                    // TODO: do training stuff
                    break;
                case PLAY_COMPUTER_EVENT:
                    // TODO: get the user to pick strength and color/random
                    PlayComputerPane playComputerPane = new PlayComputerPane(800, 600, null);
                    playComputerPane.prefWidthProperty().bind(primaryStage.widthProperty());
                    playComputerPane.prefHeightProperty().bind(primaryStage.heightProperty());
                    Scene scene = new Scene(playComputerPane);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    break;
                case SHOW_REPORTS_EVENT:
                    break;
                case SHOW_SETTINGS_EVENT:
                    break;
                case SHOW_ABOUT_EVENT:
                    break;
            }
        });
        Scene scene = new Scene(analysisPane);

        primaryStage.setTitle("Trainer (Alpha)");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);

        analysisPane.prefWidthProperty().bind(primaryStage.widthProperty());
        analysisPane.prefHeightProperty().bind(primaryStage.heightProperty());

        primaryStage.show();
    }
}
