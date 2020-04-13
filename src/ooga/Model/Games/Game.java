package ooga.Model.Games;

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


    void pauseGame();

    void resumeGame();

    void startGame();
//    This is where the animation would begin

}
