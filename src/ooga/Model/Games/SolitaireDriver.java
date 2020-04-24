package ooga.Model.Games;

import ooga.Controller.DeckType;
import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;

import java.util.ArrayList;
import java.util.List;

public class SolitaireDriver extends GameDriver {
    int score;

    //many decks since solitaire contains decks everywhere
    // TODO: add a method to get the number of suits.
    public SolitaireDriver(GameController controller, List<String>playerNames) {
        super(controller);
        makePlayers(playerNames);
        makeDecks();
    }

    private void makeDecks() {
        allGameCards = new CardDeck(DeckType.POKER, new ArrayList<>());
        CardDeck completeDeck = new CardDeck(DeckType.POKER);
        CardDeck deckWithSpecifiedSuits = new CardDeck(DeckType.POKER, new ArrayList<>());

        for (Playable card : completeDeck.getCards()) {
            if (card.getValue().equals("S")) {
                deckWithSpecifiedSuits.addCard(card);
               // System.out.println("enter");
            }
        }
        for (int i = 0; i < 8; i++) {
            CardDeck copy = deckWithSpecifiedSuits.returnADeepCopy();
            for (Playable card : copy.getCards()){
                allGameCards.addCard(card);
            }
        }
        allGameCards.shuffleDeck();
        makePiles();
    }

    @Override
    protected void makePiles() {
        allGameCards.shuffleDeck();
        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                piles.putIfAbsent(0, new CardDeck(DeckType.POKER, allGameCards.popCards(50)));
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
    /**
     * Function to check the winning condition
     */
    public boolean checkWin() {
        return false;
    }

    @Override
    public List<Object> updateProtocol(List<Object> args) {
        //case when removeCompleteSets is called
        List<Object> ret = new ArrayList<>();
        if (args == null){
            List<Integer> id =  drawPileProtocol();
            ret.add(id);
            return ret;
        }
        if (args.size() == 2){
            removeCompleteSet((Integer) args.get(0), (Integer) args.get(1));
            return null;
        }
        int sourcePile = (Integer) args.get(0);
        int destPile = (Integer) args.get(1);
        int indexInSource = (Integer) args.get(2);

        boolean cond1 = checkSourcePileOrder(sourcePile, indexInSource);
        boolean cond2 = checkNumericalContinuity(sourcePile, indexInSource, destPile);

        if (cond1 && cond2){
            ret.add(1);
        }else{
            ret.add(0);
        }
//        System.out.println("cond1: " + cond1 + "cond2: " + cond2);
        updatePiles(cond1&&cond2, sourcePile, indexInSource, destPile);
        // Logic to check for a complete set;
        int indexOfCompleteSet = checkCompleteSet(destPile);
        //System.out.println("index: " + indexOfCompleteSet);
        if (indexOfCompleteSet != -1){ret.add(indexOfCompleteSet);}
        return ret;
    }



    private int checkCompleteSet(int destPile) {
        if (piles.get(destPile).getDeckSize() == 0){return -1;}
        List<Playable> pile = piles.get(destPile).getCards();
        int index = pile.size() - 1;
        int currentNum = 1;
        while (true) {
            int cardNumber = pile.get(index).getNumber();
            if (cardNumber != currentNum) {
                break;
            }
            if (cardNumber == 13) {
                return index;
            }
            index--;
            currentNum++;

        }
        return -1;
    }

    private void removeCompleteSet(int destPile, int index) {
        List<Playable> removePile = List.copyOf(piles.get(destPile).getCards());
        for (int i = index; i < removePile.size(); i++){
            piles.get(destPile).removeCard(removePile.get(i).getID());
        }
    }

    private void updatePiles(boolean cond, int sourcePile, int indexInSource, int destPile) {
        if (!cond){return;}
        List <Playable> cardsToBeRemoved = new ArrayList<>();
        for (int i = indexInSource ; i < piles.get(sourcePile).getDeckSize(); i++){
            int cardID = piles.get(sourcePile).getCards().get(i).getID();
            piles.get(sourcePile).copyCardToNewDeck(cardID, piles.get(destPile));
            cardsToBeRemoved.add(piles.get(sourcePile).getCards().get(i));
        }
        for (Playable card : cardsToBeRemoved){
            piles.get(sourcePile).removeCard(card.getID());
        }

    }

    private boolean checkSourcePileOrder(int sourcePile, int indexInSource) {
        CardDeck temp = piles.get(sourcePile);
        if (temp.getDeckSize() <= 1) {return true;}
        int cardNum = temp.getCards().get(indexInSource).getNumber();
        for (int i = indexInSource + 1; i < temp.getDeckSize(); i++){
            if (cardNum - temp.getCards().get(i).getNumber() == 1){
                cardNum = temp.getCards().get(i).getNumber();
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean checkNumericalContinuity(int sourcePile, int indexInSource, int destPile) {
        if (piles.get(destPile).getDeckSize() < 1){return true;}
        int sourceNum = piles.get(sourcePile).getCards().get(indexInSource).getNumber();
        int lastIndexOfDestPile = piles.get(destPile).getDeckSize() - 1;
        int destNum = piles.get(destPile).getCards().get(lastIndexOfDestPile).getNumber();
        //TODO: check faceUp
        return destNum - sourceNum == 1;
    }

    private List<Integer> drawPileProtocol() {
        List<Playable> drawCards = piles.get(0).popCards(10);
        List<Integer> ret = new ArrayList<>();
        for(int i = 1 ; i < 11; i++){
            Playable card = drawCards.remove(0);
            piles.get(i).addCard(card);
            ret.add(card.getID());
        }
        return ret;
    }



    @Override
    public void changeLevel(int level) {

    }

    @Override
    public void updateScore(int score, int playerIndex) {

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
        SolitaireDriver test = new SolitaireDriver(new GameController(), new ArrayList<>());
//        Map<Integer, List<Integer>> temp = new HashMap<>();
//        temp = (Map<Integer, List<Integer>>)test.sendCards();
//        for (Integer i : temp.keySet()){
//            System.out.println("INDEX = " + i);
//            for (int j : temp.get(i)) {
//                System.out.println("Value = " + j);
//            }
//            }

        for (int i = 0; i < test.piles.size(); i++) {
            System.out.println("Pile" + i + ":");
            System.out.println(test.piles.get(i));
        }
        test.updateProtocol(null);
        for (int i = 0; i < test.piles.size(); i++) {
            System.out.println("Pile" + i + ":");
            System.out.println(test.piles.get(i));
        }

        }


    }

