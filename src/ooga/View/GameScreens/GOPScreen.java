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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private List<String> playerNames = new ArrayList<>();
    private String logo = "viewAssets/truth.jpg";
    private Label playerLabel = new Label();
    private String style = "-fx-border-color: black;-fx-background-color: rgba(125, 30, 105, 1);-fx-padding: 2 2 2 2 ";

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
    public Circle generateLogo(){
        Circle hypno = new Circle(90);
        Image hypnoImage = new Image(this.getClass().getClassLoader().getResourceAsStream(logo));
        ImagePattern hypnoImagePattern = new ImagePattern(hypnoImage);
        hypno.setFill(hypnoImagePattern);
        return hypno;
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

    public GOPScreen(GameController setUpController) {
        gameControl = setUpController;
        jsonData= gameControl.initializeGame(GameTypes.GOP);
        differentDecks = (Map<Integer, List<Integer>>) setUpController.requestCards();
        playerNames = setUpController.getPlayerNames();
        initializeImageMap(differentDecks);
        addCards(gameControl);

    }

    private void addCards(GameController setUpController) {
        setStylingForLabel();
        gameScene = new Group();
        Circle logo = generateLogo();
        logo.setLayoutX(580); logo.setLayoutY(100);
        gameScene.getChildren().addAll(logo);
        choosePlayer();
        setUpButtons(gameScene);
        double i = 0;
        double j = 0;
        for (Integer key : indexMapped.keySet()) {
            List<PartyCards> playingCards = indexMapped.get(key);
            setUponScreen(playingCards, 0, 0, i, j, 200, 200);
            i+=650;
        }

    }

    private void choosePlayer() {
        int player = getRandomInt();
        System.out.println(player);
        playerLabel.setText("Player Chosen is "+ playerNames.get(player-1) + " Score: "+ gameControl.getPlayerScore(playerNames.get(player-1)));
        playerLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        if(!gameScene.getChildren().contains(playerLabel)){
        gameScene.getChildren().add(playerLabel);}
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
        return (int) ((Math.random() * ((playerNames.size()- 1) + 1)) + 1);
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
                List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
                    for (PartyCards cardObj : listOfCards) {
                        if(cardObj.getScene() == card)
                        cardObj.changeFace(true);
                        changeVbox(cardObj);
                        card.toFront();
                    }
                    if(!tappedCards.contains(card)) tappedCards.add(card);
                    chooseWinner();

            }});
//                card.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                tappedCards.add(card);
//                chooseWinner();
//                List<PartyCards> listOfCards = indexMapped.get(card.getIndex());
//                    for (PartyCards cardObj : listOfCards) {
//                        if(cardObj.getScene() == card)
//                        cardObj.changeFace(true);
//                        changeVbox(cardObj);
//                        card.toFront();
//                    }
//                    card.setCursor(Cursor.HAND);
//            }
//        });
    }

    private void changeVbox(PartyCards card){
        card.clearCard();
    }

    private void chooseWinner(){
        // TODO : backend stuff
        setupButtons(10, 10);


    }
    private void setStylingForLabel() {
        playerLabel.setLayoutX(450);
        playerLabel.setLayoutY(250);
        playerLabel.setTextFill(Color.WHITE);
        playerLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
    }
    private void setupButtons( double XPos, double YPos){
        int initialDistance = 0;
        List<String> buttonWords = new ArrayList();
        buttonWords.add("Done");
        buttonWords.add("Not Done");
        for (int i = 0; i <=1; i++) {
            ButtonFactory gameButton = new ButtonFactory(buttonWords.get(i), XPos + initialDistance, YPos);
            gameButton.setOnAction(e -> {
                List<Object> cardsChosen = new ArrayList<>();
                for (VboxFactory cards : tappedCards) {
                    cardsChosen.add(cards.getIndex());
                }
                String[] playerTurn = playerLabel.getText().split(" ");
                cardsChosen.add(gameButton.getText());
                cardsChosen.add(playerTurn[3]);
                gameControl.updateProtocol(cardsChosen);
                gameScene.getChildren().remove(gameButton);
                for (VboxFactory card : tappedCards) {
                    if (gameScene.getChildren().remove(card) == false) {
                        endGame();
                    }
                    gameScene.getChildren().removeAll(tappedCards);
                }
                gameScene.getChildren().remove(buttonHolder);
                choosePlayer();
            });
            if (buttonHolder.getChildren().size() <= differentDecks.keySet().size() ) {
                addToHbox(gameButton);
            }


        }


    }

    private void addToHbox(ButtonFactory gameButton) {
        if(buttonHolder.getChildren().size()== differentDecks.keySet().size()){
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
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream("viewAssets/red.jpg"));
        ImagePattern backgroundPattern = new ImagePattern(background);
        setCommonButtons(ui);
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}