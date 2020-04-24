package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Players.Player;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;

public class GOPDriver extends GameDriver {
    public GOPDriver(GameController controller, List<String>playerNames) {
        super(controller);
        makePlayers(playerNames);
        makePiles();
    }

    @Override
    protected void makePiles() {
        piles.put(0, new CardDeck(DeckType.TRUTH));
        piles.put(1, new CardDeck(DeckType.DARE));
    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    public List<Object> updateProtocol(List<Object> args) {
        removeCardFromPiles((Integer) args.get(0));
        System.out.println(args.get(1));
        if(((String)args.get(args.size()-2)).equals("Done")){
            updateScore(1, (String) args.get(args.size()-1));
        }
        return null;
    }

    @Override
    public void updateScore(int score, String playerName) {
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
        GOPDriver test = new GOPDriver(new GameController(), new ArrayList<>());
        for(Integer i : test.piles.keySet()){
            System.out.println("Pile: " + i);
            System.out.println(test.piles.get(i));
        }
    }
}
