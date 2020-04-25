package ooga.View.tests;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller.GameController;
import ooga.View.UserInterface;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {
    GameController controller = new GameController();

    @Test
    void initializeGame() {
        UserInterface ui = new UserInterface();
        Platform.startup(() -> {
            ui.start(new Stage());
            List<String> playerNames = new ArrayList<>();
            playerNames.add("TestName");
            ui.initializeGame("Solitaire", playerNames);
            System.out.println(ui.getTitle());
            assertEquals(ui.getTitle(), "Game - Solitaire");
        }); }

    @Test
    void getController() {
    }

    @Test
    void setWinScreen() {
    }

    @Test
    void setSplash() {
    }
}