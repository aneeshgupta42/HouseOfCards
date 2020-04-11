package ooga.Model.Games;

import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Deck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolitaireDriver extends GameDriver{
    private CardColors DEFAULT_COLOR = CardColors.BLUE;
    private Player player;
    private CardDeck allGameCards;
    private CardDeck [] piles;
    private GameController controller;
    int score;
    //many decks since solitaire contains decks everywhere
    // TODO: add a method to get the number of suits.
    public SolitaireDriver (){
        makeDecks();
    }

    private void makeDecks(){
       CardDeck [] gameDecks = new CardDeck[8];
       allGameCards = new CardDeck(DeckType.POKER);
       for (int i = 0; i < gameDecks.length; i++){
           gameDecks[i] = new CardDeck(GameTypes.SOLITAIRE, DEFAULT_COLOR);
           for (Playable card : gameDecks[i].getCards()){
               if (card.getValue().equals("S")){
                    allGameCards.addCard(card);
               }
           }
       }
    }

    @Override
    public Object sendCards (){
        return allGameCards;
        //changed it from getCards to just this^
    }

    @Override
    public void makePlayer(String userName) {

    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    /**
     * Function to check the winning condition
     */
    public boolean checkWin() {
        return false;
    }

    @Override
    public void updateProtocol() {

    }

    @Override
    public void updateScore(int score) {

    }

    @Override
    public void changeLevel(int level) {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    @Override
    public void startGame() {

    }

    public static void main (String[]args){
        SolitaireDriver test = new SolitaireDriver();
        System.out.println(test.allGameCards);

    }
}
