
public interface Viewable {
    void moveCard (int id, int x, int y);

    void updateScore(int score);

    void displayCard (CardView cardView);

    void makeDeck (Deck deck);

    void gameMessage (String message);

    void getNewCard (Deck deck);

    void shuffleDeck (int deckId);

    void generateGame (int gameChoice);
}
