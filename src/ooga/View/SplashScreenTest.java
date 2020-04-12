package ooga.View;

import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.Controller.GameController;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.font.GlyphMetrics;

//import org.testfx.framework.junit.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

class SplashScreenTest {

    @Test
    void generateLogo() {
        Platform.startup(() -> {
            UserInterface myInterface = new UserInterface();
            myInterface.start(new Stage());
            SplashScreen splash = new SplashScreen(myInterface);
            assertTrue(splash.generateLogo() instanceof javafx.scene.shape.Rectangle);
            System.out.println("Generated logo");
        });
    }


    @Test
    void getStartScene() {

    }
}