package ooga.Model.Games;

import ooga.Model.Cards.CardDeck;
import ooga.Model.Players.Player;

public abstract class GameDriver implements Game {
    private CardDeck deck;
    private Player [] playerList;

    @Override
    public void makePlayer(String userName) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
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
