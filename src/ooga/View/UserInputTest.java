package ooga.View;

import javafx.scene.Scene;
import ooga.Controller.GameController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {

    @Test
    void getUserScene() {
        GameController control = new GameController();
        UserInterface ui = new UserInterface();
        String gameName = "Solitaire";
        UserInput userInput = new UserInput(gameName, ui, true);
        assertTrue(userInput.getUserScene() != null);
    }
}