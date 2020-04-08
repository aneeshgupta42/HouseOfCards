package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;

import java.util.Collections;
import java.util.List;

public class CardDeck implements Deck {
    DeckType deckType;
    List<AbstractCard> gameCards;

    public CardDeck (GameTypes type, CardColors color){
        switch (type){
            // need to split this to 2 different decks for the questions and answers
            case HUMANITY:
                deckType = DeckType.HUMANITY;
                break;
            case UNO:
                deckType = DeckType.UNO;
                break;
            default:
                deckType = DeckType.POKER;
        }
        CardFactory.initializeDeck(this, deckType, color);

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
