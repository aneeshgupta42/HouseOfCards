package ooga.Model.Cards;

public interface Deck {

    void addCard(Playable card);
    void makeCard(String cardType);
    void shuffleDeck();

}
