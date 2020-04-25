package ooga.View.GameScreens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import ooga.Controller.GameController;
import ooga.View.ButtonFactory;
import ooga.View.UserInterface;

import java.util.List;
import java.util.Map;

public abstract class GameScreen {
    private ButtonFactory exitButton;
    private ButtonFactory restartButton;
    private double xpos = 20;
    private double ypos = 600;
    private double endXpos = 1030;

    public void setUpButtons(Group gameScene) {
        exitButton = new ButtonFactory("Exit", xpos, ypos);
        restartButton = new ButtonFactory("Restart", endXpos, ypos);
        gameScene.getChildren().addAll(exitButton, restartButton);
    }

    public Integer getCardID(Map<Integer, ImageView> map, ImageView card) {
        for (Integer check : map.keySet()) {
            if (map.get(check).equals(card)) {
                return check;
            }
        }
        System.out.println("id not found");
        return -1;
    }

    public abstract Scene getScene(UserInterface ui);


    public void setCommonButtons(UserInterface ui, GameController gameController, String gameName) {
        restartButton.setOnMouseClicked(e -> ui.setSplash());
        exitButton.setOnMouseClicked(e -> {
            endGame(ui, gameController, gameName);

        });
    }

    public void endGame(UserInterface ui, GameController gameController, String gameName) {
        List<Object> winner = gameController.getWinner();
        gameController.updateHighScore();
        ui.setWinScreen(gameName, (String) winner.get(1), (Integer) winner.get(0));
    }

    public void showMessage (Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }


//    public void userScreen(String gameName){
//        UserInput c= new UserInput(gameName, this);
//        myStage.setScene(c.getUserScene());
//        myStage.show();
//    }

}
