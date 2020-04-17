package ooga.Model.Games;

import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.transform.SourceLocator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SolitaireDriverTest {

    SolitaireDriver test;

    @BeforeEach
    void setUp() {
        test = new SolitaireDriver(new GameController());
    }

    @Test
    void sendCards() {
        boolean cond = true;
        List<Integer> allIds = new ArrayList<>();
        Map<Integer, List<Integer>> temp = (Map<Integer, List<Integer>>) test.sendCards();
        boolean retValue = true;
        int totalCards = 0;
        for (Integer index : temp.keySet()){
            allIds.addAll(temp.get(index));
            totalCards += temp.get(index).size();
        }
        Set<Integer>uniqueIDs = new HashSet<>(allIds);
        assertTrue(totalCards == uniqueIDs.size());
    }
}