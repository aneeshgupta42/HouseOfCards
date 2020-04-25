package ooga.View;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class UserInput {
    private ButtonFactory backButton;
    private ButtonFactory nextButton;
    private ButtonFactory darkModeButton;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_FONT = 20;
    private Paint darkColor = Color.DARKGRAY;
    private Paint whiteColor = Color.WHITESMOKE;
    private static final String loadingPath = "viewAssets/loading.gif";
    private VBox playerNames = new VBox(30);;
    private TextArea name;
    private boolean isDarkMode;
    private Scene userScene;
    private Label namePrompt;  private UserInterface mainView;
    private Group user= new Group();
    private String game;
    private ComboBox<Integer> numOfPlayers;
    private List<Integer> numbers= Arrays.asList(1,2,3,4,5);

    public UserInput( String gameName, UserInterface ui, boolean darkMode){
        isDarkMode= darkMode;
        mainView = ui;
        game = gameName;
        backButton = new ButtonFactory("Back", BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_FONT, 0,0);
        nextButton = new ButtonFactory("Next", BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_FONT, 1080, 0);
        darkModeButton = new ButtonFactory("DarkMode", BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_FONT, 1080, 60);
        setUpNumberBox();
        VBox players = makeNewLabel(1);
        players.setLayoutX(200);
        players.setLayoutY(100);
        Label Header = new Label("?House of Cards¿");
        Header.setFont(new Font("Garamond", 30));
        Header.setTextFill(Color.DARKCYAN);
        Header.setLayoutX(1000/2-100); Header.setLayoutY(10);
        user.getChildren().addAll(numOfPlayers,backButton,nextButton, darkModeButton,players, Header);

        userScene = new Scene(user, 1200, 650);
        if(darkMode){
            userScene.setFill(darkColor);
        }else{
            userScene.setFill(whiteColor);
        }
        setUpNextButton(playerNames);
        returnButton();
    }

    private void setUpNumberBox() {
        numOfPlayers= new ComboBox<>();
        numOfPlayers.setLayoutX(0);
        numOfPlayers.setLayoutY(300);
        for(Integer num:numbers){
            numOfPlayers.getItems().add(num);
        }
        numOfPlayers.setOnAction(e-> makeNewLabel(numOfPlayers.getValue()));
    }

    private VBox makeNewLabel(Integer value) {
        for(int i=0;i<value;i++){
            VBox player = new VBox(10);
            namePrompt= new Label("Please enter your name");
            name= new TextArea();
            name.setPrefHeight(20);
            player.getChildren().addAll(namePrompt,name);
            playerNames.getChildren().addAll(player);
        }
        while(playerNames.getChildren().size() != value){
            playerNames.getChildren().remove(playerNames.getChildren().size()-1);
        }
    return playerNames;
    }

    public Scene getUserScene(){
        return userScene;
    }
    private void setUpNextButton(VBox players) {
        List<String> playerNames = new ArrayList<>();
        nextButton.setOnAction(e -> {
            for (int i = 0; i < players.getChildren().size(); i++) {
            VBox checkPlayerName = (VBox)players.getChildren().get(i);
            TextArea checkPlayerString= (TextArea) checkPlayerName.getChildren().get(1);
            playerNames.add(checkPlayerString.getText());
            if (checkPlayerString.getText().isEmpty()) {
                showMessage(Alert.AlertType.ERROR, "Please enter the player names");
                return;
            } else {
                if(playerNames.size()==players.getChildren().size()) {
                    mainView.initializeGame(game, playerNames);
                }
    }}});
}


    private void returnButton(){
        backButton.setOnAction(e->{
            mainView.setSplash();
        });
        darkModeButton.setOnAction(e->{
            if(isDarkMode){
                userScene.setFill(whiteColor);
                isDarkMode = false;
            }else{
                userScene.setFill(darkColor);
                isDarkMode = true;
            }

        });
    }
    private void showMessage(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }


}
