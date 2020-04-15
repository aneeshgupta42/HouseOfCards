package ooga.Model.Cards;

import java.util.List;

public interface Deck {

    void addCard(Playable card);

    /**
     * returns a list of the card IDs in a deck
     * @return
     */
    List<Integer> getIDList();

    void shuffleDeck();
    int getDeckSize();
    List<Playable> getCards();

    /**
     * Removes numOfCards Playable cards from the deck and returns it in a list.
     * @param numOfCards
     * @return
     */
    List<Playable> popCards(int numOfCards);

    /**
     * return a deep copy of a cardDeck object. All Playable objects in the deck will be copied as well.
     * @return
     */
    CardDeck returnADeepCopy();
}
