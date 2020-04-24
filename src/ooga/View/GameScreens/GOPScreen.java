package ooga.View.GameScreens;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.ButtonFactory;
import ooga.View.PartyCards;
import ooga.View.UserInterface;
import ooga.View.VboxFactory;

import java.util.*;

//TODO: changed to getImageView, front or back card depending on faceUp boolean
//TODO: requestCards will return a map with key being the pile number, and value being a cardDeck. pile 0 has 50 cards
public class GOPScreen extends GameScreen {
    private Group gameScene;
    private String backImagePath = "cardDecks/poker/red_back.png";
    private GameController gameControl;
    private Map<Integer, List<PartyCards>> indexMapped = new HashMap<>();
    //what we get
    private Delta dragDelta = new Delta();
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    //we'll make this (pile: List of Images)
    private Map<Integer, List<ImageView>> imageMap = new HashMap<>();
    private Map<String, Object> jsonData = new HashMap<>();
    private List<VboxFactory> tappedCards = new ArrayList<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();
    private HBox buttonHolder = new HBox(50);
    private Label playerLabel = new Label();
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
    public GOPScreen(GameController setUpController) {
        gameControl = setUpController;
        jsonData= gameControl.initializeGame(GameTypes.HUMANITY);
        differentDecks = (Map<Integer, List<Integer>>) setUpController.requestCards();
        initializeImageMap(differentDecks);
        addCards(gameControl);
    }

    private void addCards(GameController setUpController) {
        gameScene = new Group();
        choosePlayer();
        setUpButtons(gameScene);
        //TODO: change this to receive a map instead
        double i = 0;
        double j = 0;
        double l = 0;
        for (Integer key : indexMapped.keySet()) {
            List<PartyCards> playingCards = indexMapped.get(key);
            setUponScreen(playingCards, 0, 0, i, j, 500, 200);
            j+=100;
        }

    }

    private void choosePlayer() {
        playerLabel.setText("Player "+ getRandomInt());
        playerLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        gameScene.getChildren().add(playerLabel);
    }


    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }

    private void setUponScreen(List<PartyCards> playingCards, double v, double v1, double i, double j, double XPos, double YPos) {
        for (PartyCards card : playingCards) {
            VboxFactory cardSet = card.getScene();
            cardSet.setPrefWidth(80);
            cardSet.setPrefHeight(110);
            cardSet.setLayoutX(XPos + i -cardSet.getLayoutBounds().getMinX());
            cardSet.setLayoutY(YPos + j- cardSet.getLayoutBounds().getMinY());
            setUpListeners(cardSet);
            j = j + v;
            i = i + v1;

            gameScene.getChildren().add(cardSet);
        }

    }

    private int getRandomInt(){
        return (int) ((Math.random() * ((differentDecks.keySet().size() - 2) + 1)) + 1);
    }


    private void setUpListeners(VboxFactory card) {
        card.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    card.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                    card.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                    card.toFront();
                    card.setFace();
                    card.setStyle(style);
                    tappedCards.add(card);

            }});
                card.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tappedCards.add(card);
                chooseWinner();
                List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
                    for (PartyCards cardObj : listOfCards) {
                        if(cardObj.getScene() == card)
                        cardObj.changeFace(true);
                        changeVbox(cardObj);
                        card.toFront();

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

    }
    private void changeVbox(PartyCards card){
        card.clearCard();
    }

    private void chooseWinner(){
        // TODO : backend stuff
        setupButtons(10, 10);


    }
    private void setupButtons( double XPos, double YPos){
        int initialDistance = 0;
        for (int i = 1; i <= differentDecks.keySet().size() - 1; i++) {
            ButtonFactory gameButton = new ButtonFactory("Player " + i, XPos + initialDistance, YPos);
            gameButton.setOnAction(e -> {
                List<Object> cardsChosen = new ArrayList<>();
                for (VboxFactory cards : tappedCards) {
                    cardsChosen.add(cards.getIndex());
                }
                String[] buttonName = gameButton.getText().split(" ");
                Integer playerIndex = Integer.parseInt(buttonName[1]);
                cardsChosen.add(playerIndex);
                gameControl.updateProtocol(cardsChosen);
                gameScene.getChildren().remove(gameButton);
                for (VboxFactory card : tappedCards) {
                    System.out.println(tappedCards);
                    if (gameScene.getChildren().remove(card) == false) {
                        endGame();
                    }
                    gameScene.getChildren().removeAll(tappedCards);
                }
                gameScene.getChildren().remove(buttonHolder);
                choosePlayer();
            });
            if (buttonHolder.getChildren().size() <= differentDecks.keySet().size() - 1) {
                addToHbox(gameButton);
            }


        }


    }

    private void addToHbox(ButtonFactory gameButton) {
        if(buttonHolder.getChildren().size()== differentDecks.keySet().size()-1){
            buttonHolder.setLayoutX(100);
            buttonHolder.setLayoutY(500);
            if(!gameScene.getChildren().contains(buttonHolder)) {
                gameScene.getChildren().add(buttonHolder);
            }
            return;
        }
        buttonHolder.getChildren().add(gameButton);


    }

    private void endGame(){
        // take the user to the end screen
    }



    public Scene getScene(UserInterface ui) {
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/green_felt.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        setCommonButtons(ui);
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}