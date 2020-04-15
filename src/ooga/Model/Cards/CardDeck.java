package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck implements Deck {
    DeckType deckType;
    List<Playable> gameCards;

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
        gameCards = new ArrayList<>();
        CardFactory.initializeDeck(this, deckType, color);

    }

    public CardDeck (DeckType deckType){
        this.deckType = deckType;
        this.gameCards = new ArrayList<>();
    }

    public CardDeck(DeckType deckType, List<Playable>gameCards){
        this.deckType = deckType;
        this.gameCards = gameCards;
    }

    @Override
    public void addCard(Playable card) {
        gameCards.add(card);
    }

    @Override
    public List<Playable> getCards(){
        return gameCards;
    }

    public Playable getCardWithID(int cardID){
        for (Playable card : gameCards){
            if (card.getID() == cardID){
                return card;
            }
        }
        return null;
    }
    @Override
    public List<Integer> getIDList() {
        List<Integer>ret = new ArrayList<>();
        for (Playable card : gameCards){
            ret.add(card.getID());
        }
        return ret;
    }

    public DeckType getDeckType (){
        return deckType;
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(gameCards);
    }

    @Override
    public int getDeckSize() {
        return gameCards.size();
    }

    @Override
    public List<Playable> popCards(int numOfCards){
        List <Playable> ret = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++){
            ret.add(gameCards.remove(0));
        }
        return ret;
    }

    @Override
    public CardDeck returnADeepCopy() {
        CardDeck ret = new CardDeck(this.deckType);
        for (Playable card : gameCards){
            Playable temp = CardFactory.makeACopy(card, this.deckType);
            ret.addCard(temp);
        }
        return ret;
    }

    @Override
    public String toString(){
        String ret = "";
        for (Playable card : gameCards){
            ret = ret + card.getNumber() + card.getValue() + "\n";
        }
        return ret;
    }

    public boolean isCardPresent(int cardID) {
        for (Playable card : gameCards){
            if (card.getID() == cardID){
                return true;
            }
        }
        return false;
    }
}
