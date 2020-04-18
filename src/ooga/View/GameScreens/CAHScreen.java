package ooga.View.GameScreens;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.ButtonFactory;
import ooga.View.PartyCards;
import ooga.View.UserInterface;
import ooga.View.VboxFactory;

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
    private Map<Integer, List<PartyCards>> indexMapped = new HashMap<>();
    //what we get
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    //we'll make this (pile: List of Images)
    private Map<Integer, List<ImageView>> imageMap = new HashMap<>();
    private List<VboxFactory> tappedCards = new ArrayList<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();
    private String styling= "-fx-padding: 10;\" +\n" +
            "                \"-fx-border-style: solid inside;\" +\n" +
            "                \"-fx-border-width: 2;\" +\n" +
            "                \"-fx-border-insets: 5;\" +\n" +
            "                \"-fx-border-radius: 5;\" +\n" +
            "                \"-fx-border-color: red;";


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
            List<PartyCards> cardList= new ArrayList<>();
            for (Integer promptInt: deckMap.get(pile)){
                PartyCards makingCard = new PartyCards(pile);
               // String prompt = gameControl.getString(promptInt);
               // makingCard.setText(prompt);
                cardList.add(makingCard);
            }
            indexMapped.put(pile, cardList);
        }
    }

    //TODO: initializeGame before requestCards
    //TODO: Get a Map of (Integer, List<Integer>) instead?
    public CAHScreen(GameController setUpController) {
        gameControl = setUpController;
        gameControl.initializeGame(GameTypes.HUMANITY);
        differentDecks = (Map<Integer, List<Integer>>) setUpController.requestCards();
        initializeImageMap(differentDecks);
        addCards(gameControl);
    }

    private void addCards(GameController setUpController) {
        gameScene = new Group();
        setUpButtons(gameScene);
        //TODO: change this to receive a map instead
        double i = 0;
        double j = 0;
        double l = 0;
        for (Integer key : indexMapped.keySet()) {
            List<PartyCards> playingCards = indexMapped.get(key);
            if (playingCards.size() > 30) {
                setUponScreen(playingCards, 0.2, 0.1, i, j, 850, 500);
            } else {
                setUponScreen(playingCards, 70, 0, l, j, 20, 10);
            }
            i = i + 100;
            l = l + 100;
            j = 0;
        }
    }


    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }

    private void setUponScreen(List<PartyCards> playingCards, double v, double v1, double i, double j, double XPos, double YPos) {
        for (PartyCards card : playingCards) {
            VboxFactory cardSet = card.getScene();
            cardSet.setPrefWidth(60);
            cardSet.setPrefHeight(90);
            cardSet.setLayoutX(XPos+i);
            cardSet.setLayoutY(YPos + j);
            setUpListeners(cardSet);
            j = j + v;
            i = i + v1;
            gameScene.getChildren().add(cardSet);
        }

    }

    private void setUpListeners(VboxFactory card) {
        final VboxFactory[] cardChosen = {new VboxFactory(card.getIndex())};
        card.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(card.getIndex()!=0) {
                    card.setStyle(styling);
                    card.setCursor(Cursor.MOVE);
                    tappedCards.add(card);
                    if(card.getIndex()==differentDecks.keySet().size()){
                        chooseWinner();
                    }
                }
            }
        });
        card.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(card.getIndex()!=0){
                    if(!tappedCards.contains(card)) {
                        card.setFace();
                        card.resetCard();
                    }
                }
        }});
        card.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (card.getIndex()==0) {
                    card.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                    card.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                    card.toFront();
                    card.setFace();
                    card.resetCard();
                } else {
                    return;
                }
            }
        });
        card.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(card.getIndex()!=0) {
                    List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
                    for (PartyCards card : listOfCards) {
                        card.changeFace();
                        changeVbox(card);
                    }


                }
                card.setCursor(Cursor.HAND);
            }
        });
    }
    private void changeVbox(PartyCards card){
        card.clearCard();
    }

    private void chooseWinner(){
        // TODO : backend stuff
        // logic might be a bit off here but make a button for each player and then hide them when the host has chosen the winner
        for(int i=1;i<=differentDecks.keySet().size();i++){
            ButtonFactory choose= new ButtonFactory("Player "+ i, 35, 150);
            gameScene.getChildren().addAll(choose);
            choose.setOnAction(e->{
               // gameControl.chooseWinner(choose.getText(),tappedCards);
                choose.setVisible(false);
            });
        }
        for(VboxFactory card:tappedCards){
            if(gameScene.getChildren().remove(card)== false){
                endGame();
            }
            gameScene.getChildren().remove(card);
        }

    }

    private void endGame(){
        // take the user to the end screen 
    }


    private boolean checkBounds(double v, double v1) {
        if (v <= 1200 && v1 <= 650 && v >= 0 && v1 >= 0) {
            return true;
        }
        return false;
    }

    public Scene getScene(UserInterface ui) {
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/green_felt.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}
