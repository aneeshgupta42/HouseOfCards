package ooga.Model.Games;

import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: pull all constant attributes and non-API methods to this class

/**
 * Game Interface contains all the External APIs that the front-end calls.
 * This class implements all the Internal APIs to be used within the model;
 */
public abstract class GameDriver implements Game {
    protected CardDeck allGameCards;
    protected Map<Integer, CardDeck> piles;
    protected GameController controller;
    protected List<Player> playerList;

    public GameDriver(GameController controller, List<String>playerNames){
        this.controller = controller;
        playerList = new ArrayList<>();
        piles = new HashMap<>();
        makePlayers(playerNames);
        makePiles();
    }

    public String getCardValue(int cardID){
        return getCard(cardID).getValue();
    }


    @Override
    public Map<Integer, List<Integer>> sendCards() {
        Map<Integer, List<Integer>> ret = new HashMap<>();
        for (Integer index : piles.keySet()){
            ret.put(index, piles.get(index).getIDList());
        }
        return ret;
    }

    @Override
    public String getCardImagePath(int cardID) {
        Playable card = getCard(cardID);
        return card.getCardImagePath();
    }

    @Override
    public void makePlayers(List<String>names) {
        for (String name : names){
            playerList.add(new Player(name));
        }
    }

    @Override
    public List<String> getPlayerNames(){
        List<String>ret = new ArrayList<>();
        for (Player p : playerList){
            ret.add(p.getName());
        }
        return ret;
    }

    @Override
    public int getPlayerScore(String playerName) {
        for (Player p : playerList){
            if (p.getName().equals(playerName)){
                return p.getScore();
            }
        }
        return 0;
    }

    @Override
    public boolean IsCardFaceUp(int cardID) {
        Playable card = getCard(cardID);
        return card.isFaceUp();
    }

    protected Playable getCard(int cardID) {
        for (Integer i : piles.keySet()){
            if (piles.get(i).isCardPresent(cardID)){
                return piles.get(i).getCardWithID(cardID);
            }
        }
        return null;
    }

    protected void removeCardFromPiles(int cardID){
        for (Integer i : piles.keySet()){
            if (piles.get(i).isCardPresent(cardID)){
                piles.get(i).removeCard(cardID);
                return;
            }
        }
    }

    protected abstract void makePiles();

    @Override
    public void setFaceUp(int cardID) {
        getCard(cardID).setFaceUp(true);
    }
}
