package ooga.Model.Games;

import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.List;

public interface Game {
    void makePlayer(String userName);

    Player getPlayer();

    boolean checkWin();

    /**
     * logic to update a game based on the current state when this method is invoked.
     * Main game logic resides here
     */
    List<Object> updateProtocol(List<Object> args);

    void updateScore(int score);

    void changeLevel(int level);

    Object sendCards();

    String getCardImagePath(int ID);

    void pauseGame();

    void resumeGame();

    void startGame();

    boolean IsCardFaceUp(int cardID);

    void setFaceUp(int cardID);
}
