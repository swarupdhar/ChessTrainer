package sdhar.gui;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Menu extends Pane {
    private static final int WIDTH = 200;
    private static final int TIME = 300;

    private boolean isOpen;
    private VBox itemsBox;
    private TranslateTransition menuTransition;
    private Button openButton;

    Menu() {
        isOpen = false;
        itemsBox = new VBox();
        menuTransition = new TranslateTransition();
        openButton = new Button("Open");

        setStyle("-fx-background-color: cornflowerblue");
        setPrefWidth(WIDTH);
        setVisible(true);
        setTranslateX(-WIDTH);
        setTranslateY(0);
        setTranslateZ(-1);

        menuTransition.setNode(this);
        menuTransition.setFromX(-WIDTH);
        menuTransition.setToX(0);
        menuTransition.setDuration(Duration.millis(TIME));

        itemsBox.setPrefWidth(WIDTH);
        itemsBox.prefHeightProperty().bind(heightProperty());

        openButton.setTranslateX(WIDTH + 35);
        openButton.setTranslateY(35);
        openButton.setTranslateZ(1);
        openButton.setOnAction(event -> {
            open();
        });

        getChildren().addAll(itemsBox, openButton);
    }

    void addItem(final String itemName, final EventHandler<ActionEvent> handler) {
        Button menuItemButton = new Button(itemName);
        menuItemButton.setOnAction(handler);
        itemsBox.getChildren().add(menuItemButton);
    }

    void close() {
        isOpen = false;
        openButton.setVisible(true);
        menuTransition.setRate(-1);
        menuTransition.play();
    }

    void open() {
        isOpen = true;
        menuTransition.setRate(1);
        menuTransition.play();
        openButton.setVisible(false);
    }

}
