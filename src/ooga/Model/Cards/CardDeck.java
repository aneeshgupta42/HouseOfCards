package ooga.Model.Cards;

import ooga.Controller.GameTypes;

import java.util.Collections;
import java.util.List;

public class CardDeck implements Deck {
    List<AbstractCard> gameCards;

    public CardDeck (GameTypes type){

        switch (type){

        }
        CardFactory.initializeDeck(this, type);
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
