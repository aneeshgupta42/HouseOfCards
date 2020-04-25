package ooga.Model.Cards;

import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;
import ooga.View.utils.GameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck implements Deck {
    DeckType deckType;
    List<Playable> gameCards;


    public CardDeck (DeckType deckType){
        this.deckType = deckType;
        this.gameCards = new ArrayList<>();
        CardFactory.initializeDeck(this, deckType);
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
    public void addCard(List<Playable> cards){gameCards.addAll(cards);}

    @Override
    public List<Playable> getCards(){
        return gameCards;
    }

    @Override
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

    @Override
    public DeckType getDeckType(){
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
    public void copyCardToNewDeck(int cardID, CardDeck dest){
        try{
            Playable card = getCardWithID(cardID);
            dest.addCard(card);
        }
        catch (Exception e){
            new GameException(e.getMessage());
        }
    }

    @Override
    public void removeCard(int cardID){
        if(isCardPresent(cardID)){
            gameCards.remove(getCardWithID(cardID));
        }
        else{
            return;
        }
    }

    @Override
    public CardDeck returnADeepCopy() {
        CardDeck ret = new CardDeck(this.deckType, new ArrayList<>());
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
            ret += "ID: " + card.getID() + "    Num: " + card.getNumber() + "   Value: " + card.getValue() + "\n";
        }
        return ret;
    }

    @Override
    public boolean isCardPresent(int cardID) {
        for (Playable card : gameCards){
            if (card.getID() == cardID){
                return true;
            }
        }
        return false;
    }
}
