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
    }

    @Test
    void updateProtocol() {
    }

    @Test
    void updateScore() {
    }
}