package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.HumanityCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumanityDriver extends GameDriver{
    public HumanityDriver(GameController controller) {
        super(controller);
        makePiles();
    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    protected void makePiles() {
        piles.putIfAbsent(0, new CardDeck(DeckType.HUMANITY_QUES));
        piles.putIfAbsent(1, new CardDeck(DeckType.HUMANITY_ANS));
        makePlayer("dd");makePlayer("33");makePlayer("ee");
        piles.get(0).shuffleDeck();
        piles.get(1).shuffleDeck();
        for (int index = 2; index < playerList.size()+2; index++){
            piles.put(index, new CardDeck(DeckType.HUMANITY_ANS, piles.get(1).popCards(10)));
        }
    }

    @Override
    public Object sendCards() {
        Map<Integer, List<Integer>> ret = new HashMap<>();
        ret.put(0, piles.get(0).getIDList());
        for (int i = 2; i < piles.size(); i++){
            ret.put(i-1, piles.get(i).getIDList());
        }
        return ret;
    }

    @Override
    public List<Object> updateProtocol(List<Object> args) {
        return null;
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

    public static void main(String[] args) {
        HumanityDriver test = new HumanityDriver(new GameController());
        for (Integer i : test.piles.keySet()){
            System.out.println("Pile: " + i);
            System.out.println(test.piles.get(i));
        }
    }
}
