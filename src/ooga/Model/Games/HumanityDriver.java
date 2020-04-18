package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.HumanityCard;

import java.util.List;

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
        piles.put(2, new CardDeck(DeckType.HUMANITY_ANS, piles.get(1).popCards(10)));
        piles.put(3, new CardDeck(DeckType.HUMANITY_ANS, piles.get(1).popCards(10)));
        piles.put(4, new CardDeck(DeckType.HUMANITY_ANS, piles.get(1).popCards(10)));
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
