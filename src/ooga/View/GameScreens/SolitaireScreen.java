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
import ooga.Model.Cards.Deck;
import ooga.Model.Cards.Playable;
import ooga.View.UserInterface;

import javax.sound.midi.SysexMessage;
import java.util.List;

//TODO: changed to getImageView, front or back card depending on faceUp boolean
//TODO: requestCards will return a map with key being the pile number, and value being a cardDeck. pile 0 has
public class SolitaireScreen extends GameScreen{
    private List<ImageView> cards;
    private ImageView dummyCard;
    private Group gameScene;
    private Deck[] differentDecks= new Deck[9];
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
        Deck cards = (Deck) setUpController.requestCards();
        cards.shuffleDeck();
        List<Playable> playingCards = cards.getCards();
        setUpCards(playingCards);
        setUponScreen();
        int j=10;
        for(int i=0;i<playingCards.size();i++){
            playingCards.get(i).setFaceUp(false);
            playingCards.get(i).getFrontImageView().setFitWidth(60);
            playingCards.get(i).getFrontImageView().setFitHeight(20);
            playingCards.get(i).getFrontImageView().setX(0);
            playingCards.get(i).getFrontImageView().setY(0+j);
            j=j+10;
            gameScene.getChildren().add(playingCards.get(i).getFrontImageView());

        }

    }

    private void setUponScreen() {
        for(int i=0;i<differentDecks.length;i++){
            for(int j=0;j<differentDecks[i].getCards().size();j++){
                if(i==0){
                    setUpPot(differentDecks[i].getCards().get(i));
                }
            }
        }
    }

    private void setUpPot(Playable playable) {
        playable.setFaceUp(false);
        playable.getBackImageView().setX(0);
        playable.getBackImageView().setY(0);
        gameScene.getChildren().add(playable.getBackImageView());
    }

    private void setUpCards(List<Playable> playingCards) {
        setupMainDeck(playingCards);
        int shiftX = 0;
        int shiftY = 0;
        for (int i=1;i<differentDecks.length;i++){
            for(int j=0;j<(playingCards.size()/differentDecks.length)-1;j++){
                differentDecks[i].addCard(playingCards.get(j));
                if(j==7){
                    playingCards.get(j).setFaceUp(true);
                    playingCards.get(j).getFrontImageView().setX(0+shiftX);
                    playingCards.get(j).getFrontImageView().setY(0+shiftY);
                    shiftY = shiftY+10;
                    gameScene.getChildren().add(playingCards.get(j).getFrontImageView());
                } else{
                    playingCards.get(j).setFaceUp(false);
                    playingCards.get(j).getFrontImageView().setX(0+shiftX);
                    playingCards.get(j).getFrontImageView().setY(0+shiftY);
                    shiftY = shiftY+10;
                    gameScene.getChildren().add(playingCards.get(j).getBackImageView());
                }
                playingCards.remove(playingCards.get(j));
            }
            shiftX = shiftX+30;
        }

    }

    private void setupMainDeck( List<Playable> playingCards) {
        for (int i=0;i<48;i++){
            differentDecks[0].addCard(playingCards.get(i));
            playingCards.remove(playingCards.get(i));
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
