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
import ooga.Model.Cards.Playable;
import ooga.View.UserInterface;
import ooga.View.utils.CardSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SolitaireScreen extends GameScreen {
    private List<ImageView> cards;
    private ImageView dummyCard;
    private Group gameScene;
    private String backImagePath = "cardDecks/poker/red_back.png";
    private static final double YOFFSET = 20;
    private Delta dragDelta = new Delta();
    private GameController gameControl;
    double initial_pos;
    double initial_y;
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
    public SolitaireScreen(GameController setUpController) {
        gameControl = setUpController;
        gameControl.initializeGame(GameTypes.SOLITAIRE);
        differentDecks = (Map<Integer, List<Integer>>) setUpController.requestCards();
//        System.out.println(differentDecks.toString());
        initializeImageMap(differentDecks);
        addCards(gameControl);
    }

    private ImageView getIDImage(int id) {
        String imagePath = gameControl.getImagePath(id);
        Image cardImage = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
        return new ImageView(cardImage);
    }

    private void setCardFace(int id, boolean faceUp){
        ImageView card = idImage.get(id);
        Image cardImage;
        if(faceUp){
            String imagePath = gameControl.getImagePath(id);
            cardImage = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
        }
        else{
            cardImage = new Image(getClass().getClassLoader().getResourceAsStream(backImagePath));
        }
        card.setImage(cardImage);
    }

    private void initializeImageMap(Map<Integer, List<Integer>> deckMap) {
        for (Integer pile : deckMap.keySet()) {
            List<ImageView> imageList = new ArrayList<>();
            for (Integer id : deckMap.get(pile)) {
                ImageView cardImage = getIDImage(id);
                idImage.put(id, cardImage);
                imageList.add(cardImage);
            }
            imageMap.put(pile, imageList);
        }
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
                setUponScreen(playingCards, YOFFSET, 0, l, j, 20, 10, index);
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
        int pileSize = playingCards.size();
        int lastId = playingCards.get(pileSize-1);
        for (Integer cardID : playingCards) {
            ImageView cardImage = idImage.get(cardID);
            // TODO: use this way to keep bottom pule always back face up?
            setCardFace(cardID, cardID == lastId && (getCardPile(differentDecks, cardImage) !=0));
            cardImage.setFitWidth(60);
            cardImage.setFitHeight(90);
            cardImage.setLayoutX(XPos + i - cardImage.getLayoutBounds().getMinX());
            cardImage.setLayoutY(YPos + j- cardImage.getLayoutBounds().getMinY());
            setUpListeners(cardImage);
            List<ImageView> images = new ArrayList<>();
            images.add(cardImage);
            indexMapped.put(index, images);
            j = j + v;
            i = i + v1;
            gameScene.getChildren().add(cardImage);
        }

    }


    private void setUpListeners(ImageView cardImage) {
        int pile = getCardPile(differentDecks, cardImage);
        if (pile != 0) {
            cardImage.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    initial_pos = cardImage.getLayoutX();
                    initial_y = cardImage.getLayoutY();
//                System.out.println("Press coords for ID:" + getCardID(idImage, cardImage));
//                System.out.println(initial_pos);
//                System.out.println(initial_y);
                    // record a delta distance for the drag and drop operation.
                    CardSet cardSet = new CardSet(cardImage, idImage, differentDecks);
                    dragDelta.x = cardImage.getLayoutX() - mouseEvent.getSceneX();
                    //TODO: I didn't know what this was, so commented out:
//                    System.out.println(cardImage.getY());
//                    System.out.println(cardImage.getX());
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
                        boolean success = checkIntersection(cardSet, differentDecks, initial_pos, initial_y);
                        if (!success) {
                            cardSet.setLayoutX(initial_pos - cardImage.getLayoutBounds().getMinX());
                            cardSet.setLayoutY(initial_y - cardImage.getLayoutBounds().getMinY());
                        } else {
                            differentDecks = (Map<Integer, List<Integer>>) gameControl.requestCards();
                            List<Integer> sourcePile = differentDecks.get(pileFrom);
                            setCardFace(sourcePile.get(sourcePile.size() - 1), true);
                        }
                    } else {
                        cardSet.setLayoutX(initial_pos - cardImage.getLayoutBounds().getMinX());
                        cardSet.setLayoutY(initial_y - cardImage.getLayoutBounds().getMinY());
                    }
                }
            });
            cardImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    CardSet cardSet = new CardSet(cardImage, idImage, differentDecks);
                    if (checkBounds(mouseEvent.getSceneX(), mouseEvent.getSceneY())) {
                        //change this to cardSet
                        cardSet.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                        cardSet.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                        cardSet.toFront();
                    } else {
                        cardSet.setLayoutX(initial_pos - cardImage.getLayoutBounds().getMinX());
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
        List<Integer> drawPileIDs = differentDecks.get(0);
        List<Integer> dealingIDs = (List<Integer>)gameControl.updateProtocol(null).get(0);
        //TODO: instead of this use back end's list of 10 cards
//        for(int i = 0; i<10; i++){
//            int cardID = drawPileIDs.get(i);
//            addCardToPile(i+1, cardID);
//        }
        int targetPile = 1;
        for(Integer id: dealingIDs){
            int cardID = (Integer) id;
            addCardToPile(targetPile, cardID);
            targetPile++;
        }
        differentDecks = (Map<Integer, List<Integer>>) gameControl.requestCards();
        for(Integer id: dealingIDs){
            int cardID = (Integer) id;
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

    private boolean checkIntersection(CardSet currentCardSet, Map<Integer, List<Integer>> differentDecks, double xpos, double ypos) {
        //Diff pile numbers
        ImageView currentCard = currentCardSet.getHeadCard();
        Integer sourceCardIndex = getCardPile(differentDecks, currentCard);
        for (Integer index : differentDecks.keySet()) {
            if(index==sourceCardIndex) continue;
            List<Integer> currentPile = differentDecks.get(index);
            // checks for intersection
            ImageView targetCard = idImage.get(currentPile.get(currentPile.size() - 1));
            if (!currentCard.equals(targetCard)) {
                // Change the logic for checking intersections
                if ((currentCard.getBoundsInParent().intersects(targetCard.getBoundsInParent()))) {
                    List<Object> cardWorking = new ArrayList<>();
                    int stackFrom = getCardPile(differentDecks, currentCard);
                    cardWorking.add(stackFrom);
                    int stackTo = getCardPile(differentDecks, targetCard);
                    cardWorking.add(stackTo);
                    for (Integer id : differentDecks.get(stackFrom)) {
                        if (idImage.get(id).equals(currentCard)) {
                            cardWorking.add(differentDecks.get(stackFrom).indexOf(id));
                        }
                    }
                    currentCardSet.setLayoutX(targetCard.getLayoutX()- currentCard.getLayoutBounds().getMinX());
                    currentCardSet.setLayoutY(targetCard.getLayoutY()+YOFFSET- currentCard.getLayoutBounds().getMinY());
//                    currentCardSet.setX(targetCard.getX());
//                    currentCardSet.setY(targetCard.getY()+YOFFSET);
//                    System.out.println("Post landing");
//                    System.out.println("ID:" + getCardID(idImage, targetCard));
//                    System.out.println(targetCard.getLayoutX());
//                    System.out.println(targetCard.getLayoutY());
//                    System.out.println("ID:" + getCardID(idImage, currentCard));
//                    System.out.println(currentCard.getLayoutX());
//                    System.out.println(currentCard.getLayoutY());
                    List<Object> ret = gameControl.updateProtocol(cardWorking);
                    boolean success = (Integer) ret.get(0) == 1;
                    //System.out.println("Update protocol: " + (Integer) ret.get(0));
                    return ((Integer) ret.get(0) == 1);
                }
            }
        }
        return false;
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

    private Integer getCardID(Map<Integer, ImageView>map, ImageView card){
        for (Integer check : map.keySet()) {
            if (map.get(check).equals(card)) {
                return check;
            }
        }
        System.out.println("id not found");
        return -1;
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


}
