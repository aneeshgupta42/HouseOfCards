package ooga.View.utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ooga.View.ButtonFactory;
import ooga.View.UserInterface;

public class WinScreen {
    private UserInterface userInterface;
    private String game;
    private String player;
    private Double sceneWidth;
    private Double sceneHeight;
    private int score;
    private Group root;
    private double xpos=20;
    private double ypos=600;
    private double endXpos=1030;

    public WinScreen(UserInterface ui, String gameName, String playerName, int playerScore, double sceneW, double sceneH){
        userInterface = ui;
        game = gameName;
        sceneWidth = sceneW;
        sceneHeight = sceneH;
        player = playerName;
        score = playerScore;
        root = new Group();
        constructScene();
    }

    private void constructScene(){
        Label Header = new Label(player + " WINS!!");
        Header.setFont(new Font("Garamond", 50));
        Header.setTextFill(Color.ALICEBLUE);
        Header.setLayoutX(sceneWidth/2-100); Header.setLayoutY(150);

        Label Game = new Label("Game played: " + game);
        Game.setFont(new Font("Garamond", 50));
        Game.setTextFill(Color.BISQUE);
        Game.setLayoutX(sceneWidth/2-100); Game.setLayoutY(300);

        Label Score = new Label("Score achieved: " + score);
        Score.setFont(new Font("Garamond", 50));
        Score.setTextFill(Color.FORESTGREEN);
        Score.setLayoutX(sceneWidth/2-100); Score.setLayoutY(500);
        root.getChildren().addAll(Score, Game, Header);
        setUpButtons();
    }

    public void setUpButtons(){
        ButtonFactory exitButton = new ButtonFactory("Exit", xpos,ypos );
        ButtonFactory restartButton = new ButtonFactory("Restart",endXpos,ypos);
        restartButton.setOnMouseClicked(e-> userInterface.setSplash());
        root.getChildren().addAll(exitButton,restartButton);
    }

    public Scene getScene(){
        return new Scene(root, sceneWidth, sceneHeight, Color.CORAL);
    }

}
