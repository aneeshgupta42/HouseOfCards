package ooga.api;

public interface Game {
    void makeUser();

    User getUser();

    boolean checkWin();

    void updateScore(int score);

    void changeLevel(int level);

    void makePlayer(User user);

    void pauseGame();

    void resumeGame();

    void startGame();
//    This is where the animation would begin
}
