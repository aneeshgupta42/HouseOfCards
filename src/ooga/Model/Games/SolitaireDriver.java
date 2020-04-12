package ooga.Model.Games;

import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Deck;
import ooga.Model.Cards.Playable;
import ooga.Model.Players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolitaireDriver extends GameDriver {
    private CardColors DEFAULT_COLOR = CardColors.BLUE;
    private Player player;
    private CardDeck allGameCards;
    private Map<Integer, CardDeck> piles;
    private GameController controller;
    int score;

    //many decks since solitaire contains decks everywhere
    // TODO: add a method to get the number of suits.
    public SolitaireDriver(GameController controller) {
        this.controller = controller;
        makeDecks();
    }

    private void makeDecks() {
        CardDeck[] gameDecks = new CardDeck[8];
        allGameCards = new CardDeck(DeckType.POKER);
        for (int i = 0; i < gameDecks.length; i++) {
            gameDecks[i] = new CardDeck(GameTypes.SOLITAIRE, DEFAULT_COLOR);
            for (Playable card : gameDecks[i].getCards()) {
                if (card.getValue().equals("S")) {
                    allGameCards.addCard(card);
                }
            }
        }
        makePiles();
    }

    private void makePiles() {
        piles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                piles.putIfAbsent(0, new CardDeck(DeckType.POKER, allGameCards.popCards(48)));
            } else if (i == 1 || i == 2 || i == 3 || i == 4) {
                piles.putIfAbsent(i, new CardDeck(DeckType.POKER, allGameCards.popCards(6)));
                piles.get(i).getCards().get(5).setFaceUp(true);
            } else {
                piles.putIfAbsent(i, new CardDeck(DeckType.POKER, allGameCards.popCards(5)));
                piles.get(i).getCards().get(4).setFaceUp(true);
            }
        }
    }

    @Override
    public Object sendCards() {
        return piles;
    }

    @Override
    public void makePlayer(String userName) {
        player = new Player(userName);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    /**
     * Function to check the winning condition
     */
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

    public static void main(String[] args) {
        SolitaireDriver test = new SolitaireDriver(new GameController());
        for (int i = 0; i < test.piles.size(); i++){
            System.out.println("Pile" + i + ":");
            System.out.println(test.piles.get(i));
//            for (Playable card : test.piles.get(i).getCards()){
//                System.out.println(card.isFaceUp());
//            }
        }

    }
}
