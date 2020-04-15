package ooga.Model.Games;

import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.List;

//TODO: pull all constant attributes and non-API methods to this class
public abstract class GameDriver implements Game {
    private CardDeck[] decks;
    private Player [] playerList;

    /**
     * A class specific helper utility to traverse through a data structure to return a card given an ID
     * @param cardID
     * @return
     */
    public GameDriver(){
    }
    protected abstract Playable getCard(int cardID);
}
