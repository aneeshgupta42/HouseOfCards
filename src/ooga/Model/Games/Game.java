package ooga.Model.Games;

import java.util.List;

public interface Game {
    void makePlayers(List<String> userName);

    List<String> getPlayerNames();

    List<Integer> getPlayerScores();

    boolean checkWin();

    /**
     * logic to update a game based on the current state when this method is invoked.
     * Main game logic resides here
     */
    List<Object> updateProtocol(List<Object> args);

    void updateScore(int score, String playerName);

    void changeLevel(int level);

    Object sendCards();

    String getCardImagePath(int ID);

    void pauseGame();

    void resumeGame();

    void startGame();

    boolean IsCardFaceUp(int cardID);

    void setFaceUp(int cardID);
}
