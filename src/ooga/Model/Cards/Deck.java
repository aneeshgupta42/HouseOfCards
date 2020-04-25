package ooga.Model.Cards;

import ooga.Controller.DeckType;

import java.util.List;

public interface Deck {

    void addCard(Playable card);

    Playable getCardWithID(int cardID);

    /**
     * returns a list of the card IDs in a deck
     * @return
     */
    List<Integer> getIDList();

    DeckType getDeckType();

    void shuffleDeck();
    int getDeckSize();

    void addCard(List<Playable> cards);

    List<Playable> getCards();

    /**
     * Removes numOfCards Playable cards from the deck and returns it in a list.
     * @param numOfCards
     * @return
     */
    List<Playable> popCards(int numOfCards);

    void copyCardToNewDeck(int cardID, CardDeck dest);

    void removeCard(int cardID);

    /**
     * return a deep copy of a cardDeck object. All Playable objects in the deck will be copied as well.
     * @return
     */
    CardDeck returnADeepCopy();

    boolean isCardPresent(int cardID);
}
