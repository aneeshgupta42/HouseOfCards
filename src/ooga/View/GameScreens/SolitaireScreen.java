package ooga.View.GameScreens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.ImagePattern;
import ooga.View.UserInterface;

import java.util.List;

public class SolitaireScreen extends GameScreen{
    List<ImageView> cards;
    ImageView dummyCard;
    public SolitaireScreen(){
        Image dummy = new Image(getClass().getClassLoader().getResourceAsStream("cardDecks/poker/AS.png"));
        dummyCard = new ImageView(dummy);
        dummyCard.setX(100); dummyCard.setY(100);
        dummyCard.setFitWidth(65);
        dummyCard.setFitHeight(105);
        dummyCard.setOn
    }

    private void generateCards(){
        for
    }

    public Scene getScene(UserInterface ui){
        Group startRoot = new Group();
        startRoot.getChildren().add(dummyCard);
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/green_felt.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        Scene solitaireScene = new Scene(startRoot, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}
