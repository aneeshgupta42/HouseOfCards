package ooga.Model.Games;

import ooga.Model.Players.Player;

public interface Game {
    void makePlayer(String userName);

    Player getPlayer();

    boolean checkWin();

    /**
     * logic to update a game based on the current state when this method is invoked
     */
    void updateProtocol();

    void updateScore(int score);

    void changeLevel(int level);

    Object sendCards();


    void pauseGame();

    void resumeGame();

    void startGame();
//    This is where the animation would begin

}
