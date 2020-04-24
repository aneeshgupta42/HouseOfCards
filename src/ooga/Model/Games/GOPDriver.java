package ooga.Model.Games;

import ooga.Controller.GameController;

import java.util.List;

public class GOPDriver extends GameDriver {
    public GOPDriver(GameController controller, List<String>playerNames) {
        super(controller);
        makePlayers(playerNames);
        makePiles();
    }

    @Override
    protected void makePiles() {

    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    public List<Object> updateProtocol(List<Object> args) {
        return null;
    }

    @Override
    public void updateScore(int score, int playerIndex) {

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
