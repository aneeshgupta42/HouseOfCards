package ooga.View.GameScreens;

import javafx.application.Platform;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.UserInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolitaireScreenTest {
    GameController controller = new GameController();
    UserInterface ui = new UserInterface();

    @Test
    void testGetScene() {
        Platform.startup(()->{
            controller.initializeGame(GameTypes.SOLITAIRE);
            SolitaireScreen sol = new SolitaireScreen(controller);
            assertTrue(sol.getScene(ui) instanceof javafx.scene.Scene );
        });
    }
}