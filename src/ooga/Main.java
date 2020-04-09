package ooga;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.View.UserInterface;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller.DeckType;
import ooga.View.ButtonFactory;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main  {

    /**
     * Start of the program.
     */
    public static void main (String[] args) {

//        Application.launch(UserInterface.class, args);
//        launch(args);
        Platform.startup(() -> {
            UserInterface myInterface = new UserInterface();
            myInterface.start(new Stage());
        });
    }

}
