package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;

import java.util.List;

public class HumanityDriver extends GameDriver{
    public HumanityDriver(GameController controller) {
        super(controller);
    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    protected void makePiles() {
        piles.putIfAbsent(0, new CardDeck(DeckType.HUMANITY_QUES));
        piles.putIfAbsent(1, new CardDeck(DeckType.HUMANITY_ANS));
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
}
