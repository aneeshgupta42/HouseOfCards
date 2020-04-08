package ooga.Model;

import ooga.Model.Players.User;

public interface Game {
    void makeUser();

    User getUser();

    boolean checkWin();

    /**
     * logic to update a game based on the current state when this method is invoked
     */
    void updateProtocol();

    void updateScore(int score);

    void changeLevel(int level);

    void makePlayer(User user);

    void pauseGame();

    void resumeGame();

    void startGame();
//    This is where the animation would begin

}
