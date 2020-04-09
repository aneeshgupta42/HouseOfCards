package ooga.Model.Games;

import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Deck;
import ooga.Model.Players.Player;

public class SolitaireDriver extends GameDriver{
    Player player;
    CardDeck [] piles;
    int score;
    //many decks since solitaire contains decks everywhere
    public SolitaireDriver (){
        makeDecks();
    }

    private void makeDecks(){
        //make multiple decks for gamePlay
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
}
