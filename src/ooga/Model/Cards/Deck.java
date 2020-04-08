package ooga.Model.Cards;

public interface Deck {

    void addCard(AbstractCard card);
    void makeCard(String cardType);
    void shuffleDeck();

}
