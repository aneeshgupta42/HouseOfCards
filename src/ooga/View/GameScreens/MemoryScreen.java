package ooga.View.GameScreens;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.util.concurrent.TimeUnit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.UserInterface;
import ooga.View.utils.CardSet;

import java.util.*;

public class MemoryScreen extends GameScreen{
    private Group gameScene;
    private Map<String, Object> gameData;
    private List<String> playerNames;
    private static final String CARDWIDTH = "cardWidth";
    private static final String CARDHEIGHT = "cardHeight";
    private static final String SCENEWIDTH = "sceneWidth";
    private static final String SCENEHEIGHT = "sceneHeight";
    private static final String CARDPILEX = "cardPileX";
    private static final String CARDPILY = "cardPileY";
    private static final String GAMEBACK = "gameBackground";
    private static final String BACKIMAGE = "backImagePath";
    private static final String BASEIMAGE = "baseImagePath";
    private static final double YOFFSET = 20;
    private GameController gameControl;
    private int numCompleteSets = 0;
    private List<Object> currentPair = new ArrayList<>();
    private List<ImageView> currentPairImg = new ArrayList<>();
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();
    private Map<Integer, String> idImagePath = new HashMap<>();

    public MemoryScreen(GameController setUpController) {
        gameControl = setUpController;
        gameData = gameControl.initializeGame(GameTypes.MEMORY);
        initDiffDecks();
        initializeImageMap(differentDecks);
        addCards();
    }

    private void initDiffDecks(){
        currentPair = new ArrayList<>();
        currentPairImg = new ArrayList<>();
        differentDecks = gameControl.requestCards();
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
        if(faceUp){
            imagePath = idImagePath.get(id);
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
                idImage.put(id, getIDImage(id));
                idImagePath.put(id, gameControl.getImagePath(id));
            }
        }
    }

    private void addCards() {
        gameScene = new Group();
        setUpButtons(gameScene);
        //TODO: change this to receive a map instead
        double i = 0;
        double j = 0;
        int index = 0;
        for (Integer key : differentDecks.keySet()) {
            //playingCards is a list of IDs for that the pile "key"
            List<Integer> playingCards = differentDecks.get(key);
            double drawPileX = Double.parseDouble((String)gameData.get(CARDPILEX));
            double drawPileY = Double.parseDouble((String)gameData.get(CARDPILY));
            setUponScreen(playingCards, 0, 80, i, j, drawPileX, drawPileY, index);
            index++;
            j = j+150;
        }
    }

    private void setUponScreen(List<Integer> playingCards, double v, double v1, double i, double j, double XPos, double YPos, int index) {
        int pileSize = playingCards.size();
        int lastId = playingCards.get(pileSize-1);
        for (Integer cardID : playingCards) {
            ImageView cardImage = idImage.get(cardID);
            // TODO: use this way to keep bottom pule always back face up?
            setCardFace(cardID, false);
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
        if(getCardID(idImage, cardImage)<0) return;
            cardImage.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int cardID = getCardID(idImage, cardImage);
                    if(! (currentPair.size()==2)) {
                        setCardFace(cardID, true);
                        if(!currentPair.contains(cardID)) {
                            currentPair.add(cardID);
                            currentPairImg.add(cardImage);
                        }
                    }
                    else{
//                        setCardFace((Integer) currentPair.get(0), false);
//                        setCardFace((Integer) currentPair.get(1), false);
//                        currentPair = new ArrayList<>();
//                        currentPairImg = new ArrayList<>();
//                        setCardFace(cardID, true);
//                        System.out.println("card face up id" + cardID);
//                        currentPair.add(cardID);
//                        currentPairImg.add(cardImage);
                    }
                    cardImage.setCursor(Cursor.HAND);
                }
            });
            cardImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
               @Override
                public void handle(MouseEvent mouseEvent){
                   if(currentPair.size()==2){
                       try {
                           TimeUnit.MILLISECONDS.sleep(500);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       boolean success = checkUpdate(currentPair);
                       initDiffDecks();
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

    private boolean checkUpdate(List<Object> currentPair) {
                    List<Object> ret = gameControl.updateProtocol(currentPair);
                    boolean success = (Integer) ret.get(0) == 1;
                    System.out.println(success);
                    if(success) {
                        gameScene.getChildren().removeAll(currentPairImg);
                    }
                    else{
                        setCardFace((Integer) currentPair.get(0), false);
                        setCardFace((Integer) currentPair.get(1), false);
                    }
                    if(ret.size()==2){

                    }
                    return success;
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

}
