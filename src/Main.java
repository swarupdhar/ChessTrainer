import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdhar.gui.AnalysisPane;
import sdhar.gui.PlayComputerPane;
import sdhar.gui.TrainingPane;
import sdhar.io.NativeUtils;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
//        try {
//            NativeUtils.loadLibraryFromJar("/jni/libtensorflow_framework.so");
//            NativeUtils.loadLibraryFromJar("/jni/libtensorflow_jni.dylib");
            System.load("/Users/student/Projects/ChessTrainer/out/production/ChessTrainer/libs/jni/libtensorflow_framework.so");
            System.load("/Users/student/Projects/ChessTrainer/out/production/ChessTrainer/libs/jni/libtensorflow_jni.dylib");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        final AnalysisPane analysisPane = new AnalysisPane(800, 600, (event, caller) -> {
            switch (event) {
                case START_TRAINING_EVENT:
                    TrainingPane trainingPane = new TrainingPane(800, 600, (evt, c) -> {

                    });
                    trainingPane.prefWidthProperty().bind(primaryStage.widthProperty());
                    trainingPane.prefHeightProperty().bind(primaryStage.heightProperty());
                    Scene tScene = new Scene(trainingPane);
                    primaryStage.setScene(tScene);
                    break;
                case PLAY_COMPUTER_EVENT:
                    // TODO: get the user to pick strength and color/random
                    PlayComputerPane playComputerPane = new PlayComputerPane(800, 600, null);
                    playComputerPane.prefWidthProperty().bind(primaryStage.widthProperty());
                    playComputerPane.prefHeightProperty().bind(primaryStage.heightProperty());
                    Scene scene = new Scene(playComputerPane);
                    primaryStage.setScene(scene);
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
