package sdhar.gui;

public interface GlobalEventHandler {
    int START_TRAINING_EVENT = 0;
    int SHOW_REPORTS_EVENT = 1;
    int SHOW_ABOUT_EVENT = 2;
    int SHOW_SETTINGS_EVENT = 3;
    int PLAY_COMPUTER_EVENT = 4;
    int START_ANALYSIS_MODE_EVENT = 5;

    void handleEvent(final int event);
}
