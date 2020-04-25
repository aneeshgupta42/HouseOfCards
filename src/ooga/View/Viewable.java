package ooga.View;

import ooga.Controller.GameController;

import java.util.List;

public interface Viewable {
    void userScreen(String gameName, boolean darkMode);

    void initializeGame(String gameName, List<String> playerNames);

    String getTitle();

    GameController getController();

    void setWinScreen(String gameName, String playerName, int playerScore);

    double getWidth();

    double getHeight();

    void setSplash();
}
