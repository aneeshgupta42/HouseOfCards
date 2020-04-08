package ooga.Model.Cards;

import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;

import java.util.Collections;
import java.util.List;

public class CardDeck implements Deck {
    DeckType deckType;
    List<AbstractCard> gameCards;

    public CardDeck (GameTypes type){
        switch (type){
            case HUMANITY:
                deckType = DeckType.HUMANITY;
                break;
            case UNO:
                deckType = DeckType.UNO;
                break;
            default:
                deckType = DeckType.POKER;
        }
        CardFactory.initializeDeck(this, deckType);
    }

    @Override
    public void addCard(AbstractCard card) {
        gameCards.add(card);
    }

    @Override
    public void makeCard(String cardType) {

    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(gameCards);
    }
}
