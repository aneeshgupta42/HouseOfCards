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
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.View.UserInterface;

import java.util.List;

public class SolitaireScreen extends GameScreen{
    private List<ImageView> cards;
    private ImageView dummyCard;
    private Group gameScene;
    public SolitaireScreen(GameController setUpController){
        setUpController.initializeGame(GameTypes.SOLITAIRE);
        addCards(setUpController);


//        Image dummy = new Image(getClass().getClassLoader().getResourceAsStream("cardDecks/poker/1_C.png"));
//        dummyCard = new ImageView(dummy);
//        dummyCard.setX(100); dummyCard.setY(100);
//        dummyCard.setFitWidth(65);
//        dummyCard.setFitHeight(105);

//        dummyCard.setOn
    }

    private void addCards(GameController setUpController) {
       gameScene = new Group();
        CardDeck cards = (CardDeck) setUpController.requestCards();
        List<Playable> playingCards = cards.getCards();
        System.out.println(playingCards);
        int j=10;
        for(int i=0;i<playingCards.size();i++){
            playingCards.get(i).setFaceUp(false);
            playingCards.get(i).getFrontImageView().setFitWidth(110);
            playingCards.get(i).getFrontImageView().setFitHeight(65);
            playingCards.get(i).getFrontImageView().setX(300);
            j=j+10;
            playingCards.get(i).getFrontImageView().setY(20+j);
            gameScene.getChildren().add(playingCards.get(i).getFrontImageView());

        }

    }

//    private void generateCards(){
//        for
//    }

    public Scene getScene(UserInterface ui){

        Group startRoot = new Group();
//        startRoot.getChildren().add(dummyCard);
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/green_felt.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}
