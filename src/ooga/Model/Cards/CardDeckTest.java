package ooga.Model.Cards;

import javafx.application.Platform;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;
import ooga.View.GameScreens.TODScreen;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {
    CardDeck deck = new CardDeck(DeckType.POKER);
    @Test
    void getCards() {
        Platform.startup(() -> {
            assertTrue(deck.getCards() instanceof java.util.List);
            System.out.println("Cards Generated");
        });
    }

    @Test
    void getCardWithID() {
        assertTrue(deck.getCardWithID(1) instanceof Playable);
        System.out.println("Getting Cards using the ID");
    }

    @Test
    void getIDList() {
        assertTrue(deck.getIDList() instanceof java.util.List);
        System.out.println("Getting the list of Integer IDs");
    }

    @Test
    void getDeckType() {
        assertTrue(deck.getDeckType() instanceof DeckType);
        System.out.println("DeckType() is working");
    }


    @Test
    void popCards() {
        List<Playable> check= deck.popCards(5);
        assertTrue(check.size() == 5 );
        System.out.println("Pop Cards is working fine");

    }

    @Test
    void returnADeepCopy() {
        CardDeck check = deck.returnADeepCopy();
        assertTrue(check.getDeckSize()== check.gameCards.size());
        System.out.println("A Copy is being made and stored");
    }

    @Test
    void testToString() {
        Boolean checkBool= true;
        String check = deck.toString();
        String[] checkArray = check.split("\n");
        for(int i=0;i<checkArray.length;i++ ){
            String[] words = checkArray[i].split(" ");
            Playable card = deck.gameCards.get(i);
            if(Integer.parseInt(words[1])!= card.getID()){
                checkBool=false;
            }
        }
        assertTrue(checkBool);
        System.out.println("The cards are successfully being written to strings ");
    }

    @Test
    void isCardPresent() {
        Boolean checkBool = true;
        for(int i=0;i<deck.gameCards.size();i++) {
            if(!deck.isCardPresent(deck.gameCards.get(i).getID())){
                checkBool=false;
            }
        }
        assertTrue(checkBool);
        System.out.println("All the cards are being checked");
    }
}