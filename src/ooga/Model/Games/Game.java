package ooga.Model.Games;

import java.util.List;

public interface Game {
    void makePlayers(List<String> userName);

    List<String> getPlayerNames();

    boolean checkWin();

    /**
     * logic to update a game based on the current state when this method is invoked.
     * Main game logic resides here
     */
    List<Object> updateProtocol(List<Object> args);

    void updateScore(int score, String playerName);

    Object sendCards();

    String getCardImagePath(int ID);

    int getPlayerScore(String playerName);

    boolean IsCardFaceUp(int cardID);

    void setFaceUp(int cardID);
}
