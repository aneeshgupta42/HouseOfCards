package ooga.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller.GameController;
import ooga.View.GameScreens.GameScreen;
import ooga.View.utils.WinScreen;

import java.util.List;

public class UserInterface extends Application implements Viewable {
    private double VIEW_WIDTH = 1200;
    private double VIEW_HEIGHT = 650;
    private Stage myStage;
    private static GameController passingController = new GameController();
    private Scene splash;

    @Override
    public void start(Stage primaryStage) {
        passingController = new GameController();
        myStage = primaryStage;
        SplashScreen splashScreen = new SplashScreen(this);
        Scene disp = splashScreen.getStartScene();
        splash = disp;
        myStage.setScene(disp);
        myStage.show();
    }

    @Override
    public void userScreen(String gameName, boolean darkMode){
        UserInput c= new UserInput(gameName, this, darkMode);
        myStage.setScene(c.getUserScene());
        myStage.show();
    }

    @Override
    public void initializeGame(String gameName, List<String> playerNames){
        myStage.setTitle("Game - "+ gameName);
        GameScreen gameScreen = passingController.getGameScreen(gameName, playerNames);
        Scene gameScene = gameScreen.getScene(this);
        myStage.setScene(gameScene);
        myStage.show();
    }

    @Override
    public String getTitle(){
        return myStage.getTitle();
    }
    @Override
    public GameController getController(){
        return passingController;
    }

    @Override
    public void setWinScreen(String gameName, String playerName, int playerScore){
        WinScreen winScreen = new WinScreen(this, gameName, playerName, playerScore, VIEW_WIDTH, VIEW_HEIGHT);
        myStage.setScene(winScreen.getScene());
        myStage.show();
    }

    @Override
    public double getWidth(){
        return VIEW_WIDTH;
    }

    @Override
    public double getHeight(){
        return VIEW_HEIGHT;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void setSplash(){
        myStage.setScene(splash);
    }
}
