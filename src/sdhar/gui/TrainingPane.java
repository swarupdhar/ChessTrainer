package sdhar.gui;

import sdhar.chess.BoardBuilder;

import java.util.function.Consumer;

public class TrainingPane extends BasePane {

    public TrainingPane(double width, double height, Consumer<UserEvent> handler) {
        super(width, height, (none) -> BoardBuilder.buildStandard());

        menu.addItem("Analysis Mode", event -> handler.accept(UserEvent.START_ANALYSIS_MODE_EVENT));
        menu.addItem("Play Computer", event -> handler.accept(UserEvent.START_TRAINING_EVENT));
        menu.addItem("Settings", event -> handler.accept(UserEvent.SHOW_SETTINGS_EVENT));
        menu.addItem("About", event -> handler.accept(UserEvent.SHOW_ABOUT_EVENT));
    }
}
