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
import ooga.View.UserInterface;
import ooga.View.utils.CardSet;
import java.util.*;
import java.util.List;

public class SolitaireScreen extends GameScreen {
    private Group gameScene;
    private Map<String, Object> gameData;
    private List<String> playerNames;
    private static final String CARDWIDTH = "cardWidth";
    private static final String CARDHEIGHT = "cardHeight";
    private static final String SCENEWIDTH = "sceneWidth";
    private static final String SCENEHEIGHT = "sceneHeight";
    private static final String DRAWPILEX = "drawPileX";
    private static final String DRAWPILEY = "drawPileY";
    private static final String CARDPILEX = "cardPileX";
    private static final String CARDPILY = "cardPileY";
    private static final String GAMEBACK = "gameBackground";
    private static final String BACKIMAGE = "backImagePath";
    private static final String BASEIMAGE = "baseImagePath";
    private static final double YOFFSET = 20;
    private static final double XOFFSET = 100;
    private static final double DRAWDELX = 0.1;
    private static final double DRAWDELY = 0.2;
    private Delta dragDelta = new Delta();
    private GameController gameControl;
    private double initial_x;
    private double initial_y;
    private int numCompleteSets = 0;
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();

    public SolitaireScreen(GameController setUpController) {
        gameControl = setUpController;
        gameData = gameControl.initializeGame(GameTypes.SOLITAIRE);
        playerNames = gameControl.getPlayerNames();
        System.out.println(playerNames);
        initDiffDecks();
        initializeImageMap(differentDecks);
        addCards();
    }

    private void initDiffDecks(){
        differentDecks = gameControl.requestCards();
        for(int i = -10; i<0; i++){
            List<Integer> pile = differentDecks.get(i*-1);
            pile.add(0, i);
        }
    }

    private Image imageGetter(String path){
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
    }

    private ImageView getIDImage(int id) {
        String imagePath = gameControl.getImagePath(id);
        Image cardImage = imageGetter(imagePath);
        return new ImageView(cardImage);
    }

    private void setCardFace(int id, boolean faceUp){
        ImageView card = idImage.get(id);
        String imagePath;
        if(id<0){
            imagePath = (String) gameData.get(BASEIMAGE);
        }
        else if(faceUp){
            imagePath = gameControl.getImagePath(id);
        }
        else{
            imagePath = (String) gameData.get(BACKIMAGE);
        }
        Image cardImage = imageGetter(imagePath);
        card.setImage(cardImage);
    }

    private void initializeImageMap(Map<Integer, List<Integer>> deckMap) {
        for (Integer pile : deckMap.keySet()) {
            for (Integer id : deckMap.get(pile)) {
                ImageView cardImage;
                if(id<0){
                    String baseImagePath = (String) gameData.get(BASEIMAGE);
                    Image card = imageGetter(baseImagePath);
                    cardImage = new ImageView (card);
                }
                else{
                    cardImage = getIDImage(id);
                }
                idImage.put(id, cardImage);
            }
        }
    }

    private void addCards() {
        gameScene = new Group();
        setUpButtons(gameScene);
        double i = 0, j = 0, l = 0;
        for (Integer key : differentDecks.keySet()) {
            List<Integer> playingCards = differentDecks.get(key);
            if (key == 0) {
                double drawPileX = Double.parseDouble((String)gameData.get(DRAWPILEX));
                double drawPileY = Double.parseDouble((String)gameData.get(DRAWPILEY));
                setUponScreen(playingCards, DRAWDELY, DRAWDELX, i, j, drawPileX, drawPileY);
            } else {
                double cardPileX = Double.parseDouble((String)gameData.get(CARDPILEX));
                double cardPileY = Double.parseDouble((String)gameData.get(CARDPILY));
                setUponScreen(playingCards, YOFFSET, 0, l, j, cardPileX, cardPileY);
            }
            i = i + XOFFSET; l = l + XOFFSET;
            j = 0;
        }
    }

    private class Delta {
        double x, y;
    }

    private void setUponScreen(List<Integer> playingCards, double v, double v1, double i, double j, double XPos, double YPos) {
        int pileSize = playingCards.size();
        int lastId = playingCards.get(pileSize-1);
        for (Integer cardID : playingCards) {
            ImageView cardImage = idImage.get(cardID);
            setCardFace(cardID, cardID == lastId && (getCardPile(differentDecks, cardImage) !=0));
            cardImage.setFitWidth(Double.parseDouble((String)gameData.get(CARDWIDTH)));
            cardImage.setFitHeight(Double.parseDouble((String)gameData.get(CARDHEIGHT)));
            cardImage.setLayoutX(XPos + i - cardImage.getLayoutBounds().getMinX());
            cardImage.setLayoutY(YPos + j- cardImage.getLayoutBounds().getMinY());
            setUpListeners(cardImage);
            if(cardID>0) {
                j = j + v;
                i = i + v1;
            }
            gameScene.getChildren().add(cardImage);
        }

    }

    private void setUpListeners(ImageView cardImage) {
        int pile = getCardPile(differentDecks, cardImage);
        if(getCardID(idImage, cardImage)<0) return;
        if (pile != 0) {
            cardImage.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    initial_x = cardImage.getLayoutX();
                    initial_y = cardImage.getLayoutY();
                    dragDelta.x = cardImage.getLayoutX() - mouseEvent.getSceneX();
                    //TODO: I didn't know what this was, so commented out:
                    dragDelta.y = cardImage.getLayoutY() - mouseEvent.getSceneY();
                    cardImage.setCursor(Cursor.MOVE);
                }
            });
            cardImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    CardSet cardSet = new CardSet(cardImage, idImage, differentDecks);
                    if (checkBounds(mouseEvent.getX(), mouseEvent.getY())) {
                        cardImage.setCursor(Cursor.HAND);
                        int pileFrom = getCardPile(differentDecks, cardImage);
                        boolean success = checkUpdate(cardSet, differentDecks);
                        initDiffDecks();
                        if (!success) {
                            cardSet.setLayoutX(initial_x - cardImage.getLayoutBounds().getMinX());
                            cardSet.setLayoutY(initial_y - cardImage.getLayoutBounds().getMinY());
                        } else {
                            List<Integer> sourcePile = differentDecks.get(pileFrom);
                            setCardFace(sourcePile.get(sourcePile.size() - 1), true);
                        }
                    } else {
                        cardSet.setLayoutX(initial_x - cardImage.getLayoutBounds().getMinX());
                        cardSet.setLayoutY(initial_y - cardImage.getLayoutBounds().getMinY());
                    }
                }
            });
            cardImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    CardSet cardSet = new CardSet(cardImage, idImage, differentDecks);
                    if (checkBounds(mouseEvent.getSceneX(), mouseEvent.getSceneY())) {
                        cardSet.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                        cardSet.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                        cardSet.toFront();
                    } else {
                        cardSet.setLayoutX(initial_x - cardImage.getLayoutBounds().getMinX());
                        cardSet.setLayoutY(initial_y - cardImage.getLayoutBounds().getMinY());
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
        else{
            cardImage.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dealCards();
                }
            });
        }
    }

    private void dealCards(){
        List<Integer> dealingIDs = (List<Integer>)gameControl.updateProtocol(null).get(0);
        //TODO: instead of this use back end's list of 10 cards
//        for(int i = 0; i<10; i++){
//            int cardID = drawPileIDs.get(i);
//            addCardToPile(i+1, cardID);
//        }
        int targetPile = 1;
        for(Integer id: dealingIDs){
            int cardID = id;
            addCardToPile(targetPile, cardID);
            targetPile++;
        }
        initDiffDecks();
        for(Integer id: dealingIDs){
            int cardID = id;
            ImageView cardImage = idImage.get(cardID);
            setUpListeners(cardImage);
        }
    }

    private void addCardToPile(int pileNumber, int cardID){
        List<Integer> pile  = differentDecks.get(pileNumber);
        ImageView lastCard = idImage.get(pile.get(pile.size()-1));
        ImageView cardImage = idImage.get(cardID);
        setCardFace(cardID, true);
        cardImage.toFront();
        cardImage.setLayoutX(lastCard.getLayoutX()- cardImage.getLayoutBounds().getMinX());
        cardImage.setLayoutY(lastCard.getLayoutY()+YOFFSET- cardImage.getLayoutBounds().getMinY());
    }

    private boolean checkUpdate(CardSet currentCardSet, Map<Integer, List<Integer>> differentDecks) {
        ImageView currentCard = currentCardSet.getHeadCard();
        Integer sourceCardIndex = getCardPile(differentDecks, currentCard);
        for (Integer index : differentDecks.keySet()) {
            if(index.equals(sourceCardIndex)) continue;
            List<Integer> currentPile = differentDecks.get(index);
            ImageView targetCard = idImage.get(currentPile.get(currentPile.size() - 1));
            if (!currentCard.equals(targetCard)) {
                int targetID = getCardID(idImage, targetCard);
                if ((currentCard.getBoundsInParent().intersects(targetCard.getBoundsInParent()))) {
                    List<Object> cardWorking = new ArrayList<>();
                    int stackFrom = getCardPile(differentDecks, currentCard);
                    cardWorking.add(stackFrom);
                    int stackTo = getCardPile(differentDecks, targetCard);
                    cardWorking.add(stackTo);
                    int currIndex = differentDecks.get(stackFrom).indexOf(getCardID(idImage, currentCard))-1;
                    cardWorking.add(currIndex);
                    List<Object> ret = gameControl.updateProtocol(cardWorking);
                    boolean success = (Integer) ret.get(0) == 1;
                    if(success) {
                        currentCardSet.setLayoutX(targetCard.getLayoutX() - currentCard.getLayoutBounds().getMinX());
                        if (targetID < 0) {
                            currentCardSet.setLayoutY(targetCard.getLayoutY() - currentCard.getLayoutBounds().getMinY());
                        } else {
                            currentCardSet.setLayoutY(targetCard.getLayoutY() + YOFFSET - currentCard.getLayoutBounds().getMinY());
                        }
                    }
                    if(ret.size()==2){
                        int KingPositionInDest = (Integer) ret.get(1);
                        completeSet(stackTo, KingPositionInDest);
                        List<Object> removeCompSet = new ArrayList<>();
                        removeCompSet.add(stackTo);
                        removeCompSet.add(KingPositionInDest);
                        gameControl.updateProtocol(removeCompSet);
                    }
                    return success;
                }
            }
        }
        return false;
    }

    private void completeSet(int stackTo, int KingDestIndex){
        numCompleteSets++;
        initDiffDecks();
        List<Integer> destPile = differentDecks.get(stackTo);
        int kingID = destPile.get(KingDestIndex+1);
        ImageView kingImage = idImage.get(kingID);
        CardSet cardSet = new CardSet(kingImage, idImage, differentDecks);
        int cardBeforeID = differentDecks.get(stackTo).get(KingDestIndex);
        setCardFace(cardBeforeID, true);
        cardSet.winProtocol(numCompleteSets);
    }

    private Integer getCardPile(Map<Integer, List<Integer>> diffDecks, ImageView card) {
        Integer cardID = getCardID(idImage, card);
        for (Integer pile : diffDecks.keySet()) {
            for (Integer id : diffDecks.get(pile)) {
                if (id.equals(cardID)) {
                    return pile;
                }
            }
        }
        System.out.println("pile not found");
        return -1;
    }

    @Override
    public Scene getScene(UserInterface ui) {
        String gameBackground = (String) gameData.get(GAMEBACK);
        Image background = imageGetter(gameBackground);
        ImagePattern backgroundPattern = new ImagePattern(background);
        return new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
    }

    private boolean checkBounds(double x, double y) {
        double sceneWidth = Double.parseDouble((String)gameData.get(SCENEWIDTH));
        double sceneHeight = Double.parseDouble((String)gameData.get(SCENEHEIGHT));
        return (x <= sceneWidth && y <= sceneHeight && x >= 0 && y >= 0);
    }

}
