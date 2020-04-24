package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Deck;
import ooga.Model.Cards.Playable;

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

    public static void main(String[] args) {
        MemoryDriver test = new MemoryDriver(new GameController(), new ArrayList<>());
        for (Integer i : test.piles.keySet()){
            System.out.println("Pile:"+ i);
            System.out.println(test.piles.get(i));
        }
    }
}
