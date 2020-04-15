package ooga.View.GameScreens;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.Model.Cards.CardDeck;
import ooga.Model.Cards.Playable;
import ooga.View.UserInterface;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: changed to getImageView, front or back card depending on faceUp boolean
//TODO: requestCards will return a map with key being the pile number, and value being a cardDeck. pile 0 has 50 cards
public class CAHScreen extends GameScreen {
    private List<ImageView> cards;
    private ImageView dummyCard;
    private Group gameScene;
    private Delta dragDelta = new Delta();
    private GameController gameControl;
    private Map<Integer, List<ImageView>> indexMapped = new HashMap<>();
    //what we get
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    //we'll make this (pile: List of Images)
    private Map<Integer, List<ImageView>> imageMap = new HashMap<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();


    /***
     * Get: Map of Integer (pile number) : List<IDs> in that pile
     * Want: Map of Integer (pile number): List<ImageViews>
     * Want: Map of ID -> ImageView
     *
     * Change:
     * How we put on cards, and how we check for intersection.
     *
     * Basic pipeline:
     *
     * Front (attempts to) moves card from Pile A to Pile B.
     * This calls updateProtocol (which takes in indA, indB, ind.within.A)
     *
     * ***/

    private ImageView getIDImage(int id){
        String imagePath = gameControl.getImagePath(id);
        Image cardImage = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
        return new ImageView(cardImage);
    }


    private void initializeImageMap(Map<Integer, List<Integer>> deckMap){
        for(Integer pile: deckMap.keySet()){
            List<ImageView> imageList= new ArrayList<>();
            for (Integer id: deckMap.get(pile)){
                ImageView cardImage = getIDImage(id);
                idImage.put(id, cardImage);
                imageList.add(cardImage);
            }
            imageMap.put(pile, imageList);
        }
    }

    public CAHScreen(GameController setUpController) {
        gameControl = setUpController;
        differentDecks = (Map<Integer, List<Integer>>) setUpController.requestCards();
        initializeImageMap(differentDecks);
        gameControl.initializeGame(GameTypes.SOLITAIRE);
        addCards(gameControl);
    }

    private void addCards(GameController setUpController) {
        gameScene = new Group();
        setUpButtons(gameScene);
        //TODO: change this to receive a map instead
        double i = 0;
        double j = 0;
        double l = 0;
        int index = 0;
        for (Integer key : differentDecks.keySet()) {
            //playingCards is a list of IDs for that the pile "key"
            List<Integer> playingCards = differentDecks.get(key);
            if (playingCards.size() > 30) {
                setUponScreen(playingCards, 0.2, 0.1, i, j, 850, 500, index);
            } else {
                setUponScreen(playingCards, 20, 0, l, j, 20, 10, index);
            }
            i = i + 100;
            l = l + 100;
            index++;
            j = 0;
        }
//        List<Playable> playingCards = cards.getCards();
//        setUpCards(playingCards);
//        setUponScreen();
//        int j=10;
//        for(int i=0;i<playingCards.size();i++){
//            playingCards.get(i).setFaceUp(false);
//          /*  playingCards.get(i).getFrontImageView().setFitWidth(60);
//            playingCards.get(i).getFrontImageView().setFitHeight(20);
//            playingCards.get(i).getFrontImageView().setX(0);
//            playingCards.get(i).getFrontImageView().setY(0+j);
//            j=j+10;
//            gameScene.getChildren().add(playingCards.get(i).getFrontImageView());*/
//
//        }

    }


    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }

    private void setUponScreen(List<Integer> playingCards, double v, double v1, double i, double j, double XPos, double YPos, int index) {
        for (Integer cardID : playingCards) {
            ImageView cardImage = idImage.get(cardID);
            cardImage.setFitWidth(60);
            cardImage.setFitHeight(90);
            cardImage.setX(XPos + i);
            cardImage.setY(YPos + j);
            setUpListeners(cardImage);
            List<ImageView> images = new ArrayList<>();
            images.add(cardImage);
            indexMapped.put(index, images);
//            cardImage.setOnDragDetected();
            j = j + v;
            i = i + v1;
            gameScene.getChildren().add(cardImage);
        }

    }

    private void setUpListeners(ImageView cardImage) {
        double initial_pos = cardImage.getX();
        double initial_y = cardImage.getY();
        cardImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = cardImage.getLayoutX() - mouseEvent.getSceneX();
                //TODO: I didn't know what this was, so commented out:
//                updateProtocol(cardImage);
                dragDelta.y = cardImage.getLayoutY() - mouseEvent.getSceneY();
                cardImage.setCursor(Cursor.MOVE);
            }
        });
        cardImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkBounds(mouseEvent.getX(), mouseEvent.getY())) {
                    cardImage.setCursor(Cursor.HAND);
                    checkIntersection(cardImage, differentDecks, initial_pos,initial_y);
                } else {
                    cardImage.setX(initial_pos);
                    cardImage.setY(initial_y);
                }
            }
        });
        cardImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkBounds(mouseEvent.getSceneX(), mouseEvent.getSceneY())) {
                    cardImage.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                    cardImage.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                    cardImage.toFront();
                } else {
                    cardImage.setLayoutX(initial_pos);
                    cardImage.setLayoutY(initial_y);
                    mouseEvent.setDragDetect(false);
                }
            }
        });
        cardImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImage.setCursor(Cursor.HAND);
            }
        });
    }

    private void checkIntersection(ImageView currentCard, Map<Integer, List<Integer>> differentDecks, double xpos, double ypos) {
        //Diff pile numbers
        for (Integer index : differentDecks.keySet()) {
            List<Integer> currentPile = differentDecks.get(index);
            // checks for intersection
            ImageView pileLast = idImage.get(currentPile.get(currentPile.size()-1));
            if (!currentCard.equals(pileLast)) {
                // Change the logic for checking intersections
                if ((currentCard.getBoundsInParent().intersects(pileLast.getBoundsInParent()))) {
                    List<Object> cardWorking = new ArrayList<>();
                    int stackFrom = getKey(indexMapped, currentCard);
                    cardWorking.add(stackFrom);
                    int stackTo = getKey(indexMapped, pileLast);
                    cardWorking.add(stackTo);
                    for (Integer id : differentDecks.get(stackFrom)) {
                        if (idImage.get(id).equals(currentCard)) {
                            cardWorking.add(differentDecks.get(stackFrom).indexOf(id));
                        }
                    }
                    System.out.println(cardWorking);
                    gameControl.updateProtocol(cardWorking);
                }
            }
        }
    }

    private Integer getKey(Map<Integer, List<ImageView>> map, ImageView v) {
        for (Integer check : map.keySet()) {
            for (ImageView imageIterate : map.get(check)) {
                if (imageIterate.equals(v)) {
                    System.out.println(v);
                    return check;
                }
            }
        }
        return 0;
    }

    private boolean checkBounds(double v, double v1) {
        if (v <= 1200 && v1 <= 650 && v >= 0 && v1 >= 0) {
            return true;
        }
        return false;
    }

    private void setUpPot(Playable playable) {
        playable.setFaceUp(false);
      /*  playable.getBackImageView().setX(0);
        playable.getBackImageView().setY(0);
        gameScene.getChildren().add(playable.getBackImageView());*/
    }

//    private void setUpCards(List<Playable> playingCards) {
//        setupMainDeck(playingCards);
//        int shiftX = 0;
//        int shiftY = 0;
//        for (int i=1;i<differentDecks.length;i++){
//            for(int j=0;j<(playingCards.size()/differentDecks.length)-1;j++){
//                differentDecks[i].addCard(playingCards.get(j));
//               /* if(j==7){
//                    playingCards.get(j).setFaceUp(true);
//                    playingCards.get(j).getFrontImageView().setX(0+shiftX);
//                    playingCards.get(j).getFrontImageView().setY(0+shiftY);
//                    shiftY = shiftY+10;
//                    gameScene.getChildren().add(playingCards.get(j).getFrontImageView());
//                } else{
//                    playingCards.get(j).setFaceUp(false);
//                    playingCards.get(j).getFrontImageView().setX(0+shiftX);
//                    playingCards.get(j).getFrontImageView().setY(0+shiftY);
//                    shiftY = shiftY+10;
//                    gameScene.getChildren().add(playingCards.get(j).getBackImageView());
//                }*/
//                playingCards.remove(playingCards.get(j));
//            }
//            shiftX = shiftX+30;
//        }
//
//    }
////
//    private void setupMainDeck( List<Playable> playingCards) {
//        for (int i=0;i<48;i++){
//            differentDecks[0].addCard(playingCards.get(i));
//            playingCards.remove(playingCards.get(i));
//        }
//    }

//    private void generateCards(){
//        for
//    }

    public Scene getScene(UserInterface ui) {
        Group startRoot = new Group();
//        startRoot.getChildren().add(dummyCard);
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/green_felt.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }

    private void moveCard(Playable card) {

    }

    private static ResourceBundle getResourceBundleFromPath(String path) {
        try {
            //System.out.println("data/cardDecks/" + path + "/" + path);
            File file = new File("data/cardDecks/" + path);
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
