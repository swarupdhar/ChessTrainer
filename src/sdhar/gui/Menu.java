package sdhar.gui;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import res.Res;

public class Menu extends Pane {
    private static final int WIDTH = 280;
    private static final int TIME = 300;

    private final VBox itemsBox;
    private final TranslateTransition menuTransition;
    private final Button openButton;
    private final Button closeButton;

    Menu() {
        itemsBox = new VBox();
        menuTransition = new TranslateTransition();
        openButton = new Button();
        closeButton = new Button();

        setStyle("-fx-background-color: cornflowerblue");
        setPrefWidth(WIDTH);
        setVisible(true);
        setTranslateX(-WIDTH);
        setTranslateY(0);
        setTranslateZ(-1);

        itemsBox.setPrefWidth(WIDTH);
        itemsBox.prefHeightProperty().bind(heightProperty());

        menuTransition.setNode(this);
        menuTransition.setFromX(-WIDTH);
        menuTransition.setToX(0);
        menuTransition.setDuration(Duration.millis(TIME));
        menuTransition.setOnFinished(event -> {
            if (openButton.isVisible()) {
                openButton.setVisible(false);
            } else {
                openButton.setVisible(true);
            }
        });

        final ImageView openButtonImageView = new ImageView(Res.ICON_HAMBURG);
        openButtonImageView.setFitHeight(45);
        openButtonImageView.setFitWidth(45);
        openButton.setGraphic(openButtonImageView);
        openButton.setStyle(Styles.BORDERLESS_BUTTON_STYLE);
        openButton.setTranslateX(WIDTH + 35);
        openButton.setTranslateY(35);
        openButton.setTranslateZ(1);
        openButton.setOnAction(event -> {
            playTransition(menuTransition, 1);
        });

        itemsBox.getChildren().add(closeButton);
        final ImageView closeButtonImageView = new ImageView(Res.ICON_X);
        closeButtonImageView.setFitWidth(45);
        closeButtonImageView.setFitHeight(45);
        closeButton.setGraphic(closeButtonImageView);
        closeButton.setStyle(Styles.BORDERLESS_BUTTON_STYLE);
        closeButton.setTranslateX(WIDTH - 75);
        closeButton.setTranslateY(35);
        closeButton.setOnAction(event -> {
            playTransition(menuTransition, -1);
        });

        getChildren().addAll(itemsBox, openButton);
    }

    void addItem(final String itemName, final EventHandler<ActionEvent> handler) {
        final Button menuItemButton = new Button(itemName);
        menuItemButton.setFont(Styles.MENU_FONT);
        menuItemButton.setStyle(Styles.MENU_ITEM_STYLE);
        menuItemButton.setOnAction(handler);

        final int numItems = itemsBox.getChildren().size();
        if (numItems == 0) {
            menuItemButton.setTranslateX(25);
            menuItemButton.setTranslateY(230);
        } else {
            final Button lastButton = (Button) itemsBox.getChildren().get(numItems - 1);
            menuItemButton.setTranslateX(25);
            menuItemButton.setTranslateY(lastButton.getTranslateY() + 22);
        }
        itemsBox.getChildren().add(menuItemButton);
    }

    private static void playTransition(final TranslateTransition transition, final int rate) {
        transition.setRate(rate);
        transition.play();
    }

}
