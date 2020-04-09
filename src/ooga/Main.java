package ooga;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller.DeckType;
import ooga.View.ButtonFactory;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group gameScreen = new Group();
        ButtonFactory resetButton = new ButtonFactory("Restart", 45,120,20,0,0);
        ButtonFactory exitButton = new ButtonFactory("Exit", 45,120,20,0,40);
        gameScreen.getChildren().addAll(resetButton,exitButton);
        Scene gameScene = new Scene(gameScreen,1000,1000);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }
}
