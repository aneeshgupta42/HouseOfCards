package ooga.Model.Games;

import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.List;

//TODO: pull all constant attributes and non-API methods to this class

/**
 * Game Interface contains all the External APIs that the front-end calls.
 * This class implements all the Internal APIs to be used within the model;
 */
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
    //TODO: Implement this here instead
    protected abstract Playable getCard(int cardID);

}
