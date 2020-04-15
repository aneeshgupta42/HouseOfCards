package ooga.Model.Games;

import ooga.Controller.GameController;
import ooga.Model.Cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.transform.SourceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SolitaireDriverTest {

    SolitaireDriver test;

    @BeforeEach
    void setUp() {
        SolitaireDriver test = new SolitaireDriver(new GameController());
    }

    @Test
    void sendCards() {
        boolean cond = true;
        List<Integer> allIds = new ArrayList<>();
        Map<Integer, List<Integer>> temp = (Map<Integer, List<Integer>>) test.sendCards();
        int totalCards = 0;
        for (Integer index : temp.keySet()){
            allIds.addAll(temp.get(index));
            totalCards += temp.get(index).size();
        }
        assertTrue(totalCards == allIds.size());
    }
}