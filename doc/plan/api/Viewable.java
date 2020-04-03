package ooga.api;

import javafx.scene.image.Image;

public interface Viewable {

    void moveCard (int id, int x, int y);

    void updateScore(int score);

    void displayCard (Image cardView);

    void makeDeck (Deck deck);

    void downloadGame(Game game);

    void gameMessage (String message);

    void getNewCard (Deck deck);

    void shuffleDeck (int deckId);

    void generateGame (int gameChoice);

    void endGame();

    Deck getDeck(int x, int y);
}
