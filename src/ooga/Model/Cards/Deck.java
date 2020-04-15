package ooga.Model.Cards;

import java.util.List;

public interface Deck {

    void addCard(Playable card);
    void shuffleDeck();
    int getDeckSize();
    List<Playable> getCards();
    List<Integer> getID();

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
