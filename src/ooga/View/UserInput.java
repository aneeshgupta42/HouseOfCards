package ooga.View;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.util.List;



public class UserInput {
    private ButtonFactory backButton;
    private ButtonFactory nextButton;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_FONT = 20;
    private VBox playerNames = new VBox(30);;
    private TextArea name;
    private Scene userScene;
    private Label namePrompt;
    private UserInterface mainView;
    private String game;
    private ComboBox<Integer> numOfPlayers;
    private List<Integer> numbers= Arrays.asList(1,2,3,4,5);

    public UserInput( String gameName, UserInterface ui){
        mainView = ui;
        game = gameName;
        Group user= new Group();
        backButton = new ButtonFactory("Back", BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_FONT, 0,0);
        nextButton = new ButtonFactory("Next", BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_FONT, 1080, 0);
        setUpNumberBox();
        VBox players = makeNewLabel(1);
        players.setLayoutX(200);
        players.setLayoutY(100);
        Label Header = new Label("?House of Cards¿");
        Header.setFont(new Font("Garamond", 30));
        Header.setTextFill(Color.DARKCYAN);
        Header.setLayoutX(1000/2-100); Header.setLayoutY(10);
        user.getChildren().addAll(numOfPlayers,backButton,nextButton, players, Header);
        userScene = new Scene(user, 1200, 650);
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
        nextButton.setOnAction(e -> {
            for (int i = 0; i < players.getChildren().size(); i++) {
            VBox checkPlayerName = (VBox)players.getChildren().get(i);
            System.out.print(checkPlayerName);
            TextArea checkPlayerString= (TextArea) checkPlayerName.getChildren().get(1);
            if (checkPlayerString.getText().isEmpty()) {
                showMessage(Alert.AlertType.ERROR, "Please enter the player names");
                return;
            } else {
                mainView.initializeGame(game);
        // go to the game scene
    }}});
    // gameController.makePlayer(textEntered);s
}



    private void returnButton(){
        backButton.setOnAction(e->{
            mainView.setSplash();
        });
    }
    private void showMessage(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }


}
