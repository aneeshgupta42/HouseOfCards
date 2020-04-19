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
    private String backImagePath = "cardDecks/poker/red_back.png";
    private GameController gameControl;
    private Map<Integer, List<PartyCards>> indexMapped = new HashMap<>();
    //what we get
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    //we'll make this (pile: List of Images)
    private Map<Integer, List<ImageView>> imageMap = new HashMap<>();
    private List<VboxFactory> tappedCards = new ArrayList<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();
    private String style = "-fx-border-color: black;-fx-background-color: rgba(255, 255, 255, 0.8);-fx-padding: 2 2 2 2 ";

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
            Image cardImage = new Image(getClass().getClassLoader().getResourceAsStream(backImagePath));
            for (Integer promptInt: deckMap.get(pile)){
                PartyCards makingCard = new PartyCards(pile, cardImage);
                 String prompt = gameControl.getValue(promptInt);
                 makingCard.setText(prompt);
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
            if (key==0) {
                setUponScreen(playingCards, 0.2, 0.1, i, j, 850, 450);
            } else if(key==2 || key==3 || key==4){
                setUponScreen(playingCards, 0, 70, l, j, 20, 0);
            }
            i = 0;
            l = 0;
            j = j+100;
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
            cardSet.setLayoutX(XPos + i -cardSet.getLayoutBounds().getMinX());
            cardSet.setLayoutY(YPos + j- cardSet.getLayoutBounds().getMinY());
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
                    List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
                    for (PartyCards card : listOfCards) {
                        card.changeFace(true);
                        changeVbox(card);
                    }


                }
                card.setCursor(Cursor.HAND);
//                if(card.getIndex()!=0) {
//                    card.setCursor(Cursor.MOVE);
//                    tappedCards.add(card);
//                    if(card.getIndex()==differentDecks.keySet().size()-1){
//                        chooseWinner();
//                    }
//                }
            }
        });
        card.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                if(card.getIndex()!=0){
//                    List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
//                    for (PartyCards card : listOfCards) {
//                        if(!tappedCards.contains(card.getScene()))
//                        card.getScene().setFace();
//                        changeVbox(card);
//                    }
//                }
        }});
        card.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (card.getIndex()==0) {
                    card.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                    card.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                    card.toFront();
                    card.setFace();
                    card.setStyle(style);
                    tappedCards.add(card);
                } else {
                    if(card.getIndex()!=0) {
                        card.setCursor(Cursor.MOVE);
                        card.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                        card.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                        tappedCards.add(card);
                        if(card.getIndex()==differentDecks.keySet().size()-1){
                            chooseWinner();
                        }
                    }
                    if(card.getIndex()!=0){
                        List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
                        for (PartyCards card : listOfCards) {
                            if(!tappedCards.contains(card.getScene()))
                            card.changeFace(false);
                            changeVbox(card);
                        }
                    }
                }
            }
        });
        card.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                if(card.getIndex()!=0) {
//                    List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
//                    for (PartyCards card : listOfCards) {
//                        card.changeFace();
//                        changeVbox(card);
//                    }
//
//
//                }
//                card.setCursor(Cursor.HAND);
            }
        });
    }
    private void changeVbox(PartyCards card){
        card.clearCard();
    }

    private void chooseWinner(){
        // TODO : backend stuff
       setupButtons(10, 10);


    }
    private void setupButtons( double XPos, double YPos){
        int distanceBetweenButtons=200;
        int initialDistance=0;
        for(int i=1;i<=differentDecks.keySet().size()-1;i++){
            ButtonFactory gameButton = new ButtonFactory("Player "+i, XPos+initialDistance, YPos );
           gameButton.setOnAction(e->{
               List<Object> cardsChosen = new ArrayList<>();
               for(VboxFactory cards:tappedCards){
                   cardsChosen.add(cards.getIndex());
               }
               String[] buttonName = gameButton.getText().split(" ");
               Integer playerIndex = Integer.parseInt(buttonName[1]);
               cardsChosen.add(playerIndex);
                gameControl.updateProtocol(cardsChosen);
                gameScene.getChildren().remove(gameButton);
                for(VboxFactory card:tappedCards){
                    if(gameScene.getChildren().remove(card)== false){
                        endGame();
                    }
                    gameScene.getChildren().remove(card);
                }
            });
            gameScene.getChildren().add(gameButton);
            initialDistance+=distanceBetweenButtons;

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
