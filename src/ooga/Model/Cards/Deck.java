package ooga.Model.Cards;

import java.util.List;

public interface Deck {

    void addCard(Playable card);
    void makeCard(String cardType);
    void shuffleDeck();
    int getDeckSize();
    List<Playable> getCards();

}