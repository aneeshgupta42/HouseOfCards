package ooga.View.GameScreens;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: changed to getImageView, front or back card depending on faceUp boolean
//TODO: requestCards will return a map with key being the pile number, and value being a cardDeck. pile 0 has 50 cards
public class CAHScreen extends GameScreen {
    private Group gameScene;
    private Delta dragDelta = new Delta();
    private GameController gameControl;
    private Map<Integer, List<PartyCards>> indexMapped = new HashMap<>();
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    private Map<String, Object> jsonData = new HashMap<>();
    private List<VboxFactory> tappedCards = new ArrayList<>();
    private static final String BACK_IMAGE_PATH = "backImagePath_humanity";
    private String style = "-fx-border-color: black;-fx-background-color: rgba(155, 30, 15, 1);-fx-padding: 2 2 2 2 ";
    private static final String VERTICAL_Z_KEY ="verticalZ";
    private static final String HORIZ_Z_KEY="horizZ";
    private static final String X_ZERO_KEY = "Xzero_index";
    private static final String Y_ZERO_KEY="Yzero_index";
    private static final String VERTICAL_N_KEY= "verticalN";
    private static final String HORIZ_N_KEY="horizN";
    private static final String X_INDEX_KEY= "Xindex";
    private static final String Y_INDEX_KEY="Yindex";
    private static final String  DISTANCE_KEY="Distance";
    private static final String WIDTH_KEY="width";
    private static final String HEIGHT_KEY="height";
    private static final String BUTTON_X_KEY= "ButtonX";
    private static final String BUTTON_Y_KEY="ButtonY";
    private static final String BUTTON_TEXT_KEY="button_label";
    private static final String BACKGROUND_KEY="background_string";
    private static final String BUTTON_DISTANCE="Button_Distance";
    private static final String EMPTY =" ";
    private static UserInterface ui= new UserInterface();
    private int round =0;
    private List<String> names = new ArrayList<>();
    private HBox buttonHolder = new HBox(50);
    private int round_Number = 1;
    private Label playerLabel = new Label(" ");

    private ImageView getIDImage(int id){
        String imagePath = gameControl.getImagePath(id);
        Image cardImage = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
        return new ImageView(cardImage);
    }


    private void initializeImageMap(Map<Integer, List<Integer>> deckMap){
        for(Integer pile: deckMap.keySet()){
            List<PartyCards> cardList= new ArrayList<>();
            //read in from the JSON
            Image cardImage = new Image(getClass().getClassLoader().getResourceAsStream((String)jsonData.get(BACK_IMAGE_PATH)));
            for (Integer promptInt: deckMap.get(pile)){
                PartyCards makingCard = new PartyCards(pile, cardImage);
                 String prompt = gameControl.getValue(promptInt);
                 makingCard.setText(prompt);
                cardList.add(makingCard);
            }

            indexMapped.put(pile, cardList);


        }
    }

    private void changeCards(int roundNumber){
        round = roundNumber;
        checkRound();
        List<PartyCards> cardsToPresent = indexMapped.get(round);
        for(PartyCards card: cardsToPresent){
            if(!tappedCards.contains(card.getScene())) {
                card.getScene().setVisible(!card.getScene().isVisible());
                playerLabel.setVisible(card.getScene().isVisible());
                playerLabel.setText(names.get(round-1) + "   "+ gameControl.getPlayerScore(names.get(round-1)));
            }
        }
    }
    private void checkRound(){
        if(round>differentDecks.keySet().size()-1) {
            round = 1;
            round_Number++;
        }
        if(round_Number==10){
            endGame();
        }
    }


    public CAHScreen(GameController setUpController) {
        gameControl = setUpController;
        jsonData = gameControl.initializeGame(GameTypes.HUMANITY);
        differentDecks = setUpController.requestCards();
        names= gameControl.getPlayerNames();
        initializeImageMap(differentDecks);
        addCards(gameControl);
    }

    private void addCards(GameController setUpController) {
        gameScene = new Group();
        setStylingForLabel();
        gameScene.getChildren().add(playerLabel);
        setUpButtons(gameScene);
        //TODO: change this to receive a map instead
        double i = 0;
        double j = 0;
        double l = 0;
        for (Integer key : indexMapped.keySet()) {
            List<PartyCards> playingCards = indexMapped.get(key);
            if (key==0) {
                setUponScreen(playingCards, Double.parseDouble((String)jsonData.get(VERTICAL_Z_KEY)), Double.parseDouble((String)jsonData.get(HORIZ_Z_KEY)), i, j, Double.parseDouble((String)jsonData.get(X_ZERO_KEY)), Double.parseDouble((String)jsonData.get(Y_ZERO_KEY)));
            } else {
                setUponScreen(playingCards, Double.parseDouble((String)jsonData.get(VERTICAL_N_KEY)), Double.parseDouble((String)jsonData.get(HORIZ_N_KEY)), l, j, Double.parseDouble((String)jsonData.get(X_INDEX_KEY)), Double.parseDouble((String)jsonData.get(Y_INDEX_KEY)));
            }
            i = 0;
            l = 0;
            j = j+Double.parseDouble((String)jsonData.get(DISTANCE_KEY));
        }
    }

    private void setStylingForLabel() {
        playerLabel.setLayoutX(500);
        playerLabel.setLayoutY(150);
        playerLabel.setTextFill(Color.WHITE);
        playerLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
    }


    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }

    private void setUponScreen(List<PartyCards> playingCards, double v, double v1, double i, double j, double XPos, double YPos) {
        for (PartyCards card : playingCards) {
            VboxFactory cardSet = card.getScene();
             cardSet.setPrefWidth(Double.parseDouble((String)jsonData.get(WIDTH_KEY)));
            cardSet.setPrefHeight(Double.parseDouble((String)jsonData.get(HEIGHT_KEY)));
            cardSet.setLayoutX(XPos + i -cardSet.getLayoutBounds().getMinX());
            cardSet.setLayoutY(YPos + j- cardSet.getLayoutBounds().getMinY());
            if(cardSet.getIndex()!=0) {
                cardSet.setVisible(false);
            }
            setUpListeners(cardSet);
            j = j + v;
            i = i + v1;

            gameScene.getChildren().add(cardSet);
            gameScene.setOnKeyPressed(e-> handleKeyPressed(e.getCode()));

    }}

    private void handleKeyPressed(KeyCode code){
        if(code==KeyCode.E){
            changeCards(++round);
        } else if(code==KeyCode.N){
            changeCards(round);
            changeCards(++round);
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
            }
        });
        card.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
                            setupButtons(Double.parseDouble((String)jsonData.get(BUTTON_X_KEY)), Double.parseDouble((String)jsonData.get(BUTTON_Y_KEY)));

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
            }
        });
    }
    private void changeVbox(PartyCards card){
        card.clearCard();
    }

    private void chooseWinner(){
        // TODO : backend stuff
        //setUpTappedCards();


    }
    private void setupButtons( double XPos, double YPos){
            double distanceBetweenButtons = Double.parseDouble((String) jsonData.get(BUTTON_DISTANCE));
            int initialDistance = 0;
            for (int i = 1; i <= differentDecks.keySet().size() - 1; i++) {
                ButtonFactory gameButton = new ButtonFactory(names.get(i-1), XPos + initialDistance, YPos);
                gameButton.setOnAction(e -> {
                    changeCards(differentDecks.keySet().size() - 1);
                    List<Object> cardsChosen = new ArrayList<>();
                    for (VboxFactory cards : tappedCards) {
                        cardsChosen.add(cards.getIndex());
                    }
                    cardsChosen.add(gameButton.getText());
                    gameControl.updateProtocol(cardsChosen);
                    gameScene.getChildren().remove(gameButton);

                    for (VboxFactory card : tappedCards) {
                        if (gameScene.getChildren().remove(card) == false) {

                        }
                        gameScene.getChildren().remove(card);
                    }
                    gameScene.getChildren().remove(buttonHolder);
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
        endGame(ui, gameControl, "Cards Against Humanity");
    }



    public Scene getScene(UserInterface ui) {
        Image background = new Image(this.getClass().getClassLoader().getResourceAsStream((String)jsonData.get(BACKGROUND_KEY)));
        ImagePattern backgroundPattern = new ImagePattern(background);
        this.ui = ui;
        setCommonButtons(ui, gameControl, "Cards Against Humanity");
        Scene solitaireScene = new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
        return solitaireScene;
    }
}
