package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryDriver extends GameDriver {
    public MemoryDriver(GameController controller, List<String>playerNames) {
        super(controller, playerNames);
    }

    @Override
    protected void makePiles() {
        CardDeck completeSet = new CardDeck(DeckType.POKER);
        CardDeck deckWithSpecifiedSuits = new CardDeck(DeckType.POKER, new ArrayList<>());
        int numOfColumns = (playerList.size() > 1)? 4 : 2;
        for (Playable card : completeSet.getCards()){
            if (playerList.size() > 1){
                if (card.getValue().equals("S") || card.getValue().equals("D")){
                    deckWithSpecifiedSuits.addCard(card);
                }
            }else{
                if (card.getValue().equals("S")){
                    deckWithSpecifiedSuits.addCard(card);
                }
            }

        }
        deckWithSpecifiedSuits.addCard(deckWithSpecifiedSuits.returnADeepCopy().getCards());
        deckWithSpecifiedSuits.shuffleDeck();
        for(int i = 0; i < numOfColumns; i++){
            piles.put(i, new CardDeck(DeckType.POKER, deckWithSpecifiedSuits.popCards(13)));
        }
    }

    public Map<Integer, CardDeck> getPiles() {
        return piles;
    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    public List<Object> updateProtocol(List<Object> args) {
        List<Object> ret = new ArrayList<>();
        Playable card1 = getCard((Integer) args.get(0));
        Playable card2 = getCard((Integer) args.get(1));
        String player = (String) args.get(2);
        if(card1.getNumber() == card2.getNumber() && card1.getValue().equals(card2.getValue())){
            updateScore(1, player);
            ret.add(1);
            removeCardFromPiles((Integer) args.get(0)); removeCardFromPiles((Integer) args.get(1));
        }else{
            ret.add(0);
        }
        if(testForGameExit()){
            ret.add(1);
        }
        return ret;
    }

    private boolean testForGameExit() {
        for (Integer i : piles.keySet()){
            if(piles.get(i).getDeckSize() > 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateScore(int score, String playerName) {
        if (playerName.equals("")){
            playerList.get(0).addToScore(score);
            return;
        }
        for (Player p : playerList){
            if(playerName.equals(p.getName())){
                p.addToScore(score);
            }
        }
    }

    public static void main(String[] args) {
        List<Object> update = new ArrayList<>();
        update.add(1); update.add(20);
        MemoryDriver test = new MemoryDriver(new GameController(), new ArrayList<>());
        test.updateProtocol(update);
        for (Integer i : test.piles.keySet()){
            System.out.println("Pile:"+ i);
            System.out.println(test.piles.get(i).getDeckSize());
        }
    }
}
