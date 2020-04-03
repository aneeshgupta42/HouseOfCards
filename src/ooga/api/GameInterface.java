package ooga.api;

public interface GameInterface {
    void makeUser();

    User getUser();

    boolean checkWin();

    void updateScore(int score);

    void changeLevel(int level);

    void makePlayer(User user);
}
