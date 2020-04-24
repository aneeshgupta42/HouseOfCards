package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.ArrayList;
import java.util.List;

public class MemoryDriver extends GameDriver {
    public MemoryDriver(GameController controller, List<String>playerNames) {
        super(controller);
        makePlayers(playerNames);
        makePiles();
    }

    @Override
    protected void makePiles() {
        CardDeck completeSet = new CardDeck(DeckType.POKER);
        CardDeck deckWithSpecifiedSuits = new CardDeck(DeckType.POKER, new ArrayList<>());
        for (Playable card : completeSet.getCards()){
            if (card.getValue().equals("S") || card.getValue().equals("D")){
                deckWithSpecifiedSuits.addCard(card);
            }
        }
        deckWithSpecifiedSuits.addCard(deckWithSpecifiedSuits.returnADeepCopy().getCards());
        deckWithSpecifiedSuits.shuffleDeck();
        //System.out.println(deckWithSpecifiedSuits);
        for(int i = 0; i < 4; i++){
            piles.put(i, new CardDeck(DeckType.POKER, deckWithSpecifiedSuits.popCards(13)));
        }
        //System.out.println(deckWithSpecifiedSuits.getDeckSize());

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
        if(card1.getNumber() == card2.getNumber() && card1.getValue().equals(card2.getValue())){
            ret.add(1);
            removeCardFromPiles((Integer) args.get(0)); removeCardFromPiles((Integer) args.get(1));
            updateScore(5, "");
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
