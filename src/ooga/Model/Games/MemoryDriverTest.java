package ooga.Model.Games;

import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDriverTest {
    MemoryDriver test;
    ArrayList<String> names = new ArrayList<>();
    @BeforeEach
    void setUp()
    {
        names.add("Tom");
        names.add("Dick");
        names.add("Harry");
        test = new MemoryDriver(new GameController(), names);
    }

    @Test
    void checkNameList(){
        assertEquals(test.getPlayerNames().size(), names.size());
    }


    @Test
    void checkGetPlayerScore(){
        for(String name:names){
            assertEquals(test.getPlayerScore(name), 0);
        }

    }


    @Test
    void checkIsCardFaceUp(){
        assertFalse(test.IsCardFaceUp(test.getPiles().get(0).getIDList().get(0)));
    }

    @Test
    void makePiles1() {
        test.makePiles();
        Map<Integer, CardDeck> testPiles = test.getPiles();
        assertEquals(testPiles.keySet().size(), 4);
    }

    @Test
    void makePiles2() {
        names.remove("Harry");
        names.remove("Dick");
        test = new MemoryDriver(new GameController(), names);
        test.makePiles();
        Map<Integer, CardDeck> testPiles = test.getPiles();
        assertEquals(testPiles.keySet().size(), 2);
    }

    @Test
    void checkWin() {
        assertFalse(test.checkWin());
    }

    @Test
    void checkGetCard() {
        for(Integer pile: test.getPiles().keySet()){
            for(Integer id: test.getPiles().get(pile).getIDList()){
                assertNotNull(test.getCard(id));
            }
        }
    }

    @Test
    void checkRemoval() {
        for(Integer pile: test.getPiles().keySet()){
            for(Integer id: test.getPiles().get(pile).getIDList()){
                test.removeCardFromPiles(id);
            }
        }
        for(Integer pile: test.getPiles().keySet()){
            assertEquals(test.getPiles().get(pile).getIDList().size(), 0);
            }
    }

    @Test
    void updateProtocol() {
        ArrayList<Object> args = new ArrayList<>();
        int originalScore = test.getPlayerScore("Harry");
        int a = test.getPiles().get(0).getIDList().get(0);
        int b = test.getPiles().get(1).getIDList().get(1);
        args.add(a); args.add(b);args.add("Harry");
        assertNotNull(test.getCard(a));
        assertNotNull(test.getCard(b));
        if((Integer) test.updateProtocol(args).get(0)==1){
            assertEquals(test.getPlayerScore("harry"), originalScore + 1);
        }
        else{
            assertEquals(test.getPlayerScore("harry"), originalScore);
        }
    }

    @Test
    void checkRemovalCard(){
        ArrayList<Object> args = new ArrayList<>();
        int a = test.getPiles().get(0).getIDList().get(0);
        int b = test.getPiles().get(1).getIDList().get(1);
        args.add(a); args.add(b);args.add("Harry");
        assertNotNull(test.getCard(a));
        assertNotNull(test.getCard(b));
        if((Integer) test.updateProtocol(args).get(0)==1){
            assertFalse(test.getPiles().get(0).getIDList().contains(a));
            assertFalse(test.getPiles().get(1).getIDList().contains(b));
        }
        else{
            assertTrue(test.getPiles().get(0).getIDList().contains(a));
            assertTrue(test.getPiles().get(1).getIDList().contains(b));
        }
    }

    @Test
    void updateScore() {
        test.updateScore(11, "Tom");
        test.updateScore(12, "Dick");
        test.updateScore(13, "Harry");
        assertEquals(test.getPlayerScore("Tom"), 11);
        assertEquals(test.getPlayerScore("Dick"), 12);
        assertEquals(test.getPlayerScore("Harry"), 13);
    }
}