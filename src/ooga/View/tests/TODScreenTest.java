package ooga.View.tests;

import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.GameScreens.TODScreen;
import ooga.View.SplashScreen;
import ooga.View.UserInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TODScreenTest {
    GameController controller = new GameController();
    UserInterface ui = new UserInterface();
    @Test
    void setUpButtons() {
    }

    @Test
    void getCardID() {
    }

    @Test
    void setCommonButtons() {
    }

    @Test
    void endGame() {
    }

    @Test
    void generateLogo() {
        Platform.startup(() -> {
            controller.initializeGame(GameTypes.TOD);
            TODScreen tod = new TODScreen(controller);
            assertTrue(tod.generateLogo() instanceof javafx.scene.shape.Circle);
            System.out.println("Generated logo");
        });
    }

    @Test
    void getScene() {
        Platform.startup(()->{
            controller.initializeGame(GameTypes.TOD);
            TODScreen tod = new TODScreen(controller);
            assertTrue(tod.getScene(ui) instanceof javafx.scene.Scene );
        });
    }
}