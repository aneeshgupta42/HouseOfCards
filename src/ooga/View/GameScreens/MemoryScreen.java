package ooga.View.GameScreens;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import ooga.Controller.GameController;
import ooga.Controller.GameTypes;
import ooga.View.UserInterface;

import java.util.*;

public class MemoryScreen extends GameScreen {
    private Group gameScene;
    private Map<String, Object> gameData;
    private List<String> playerNames;
    private UserInterface userInterface;
    private static final String CARDWIDTH = "cardWidth";
    private static final String CARDHEIGHT = "cardHeight";
    private static final String CARDPILEX = "cardPileX";
    private static final String CARDPILY = "cardPileY";
    private static final String GAMEBACK = "gameBackground";
    private static final String BACKIMAGE = "backImagePath";
    private static final String YOFFSET = "YOFFSET";
    private static final String XOFFSET = "XOFFSET";
    private GameController gameControl;
    private boolean cheatCodeActive = false;
    private int numCompletePairs = 0;

    private List<Object> currentPair = new ArrayList<>();
    private List<ImageView> currentPairImg = new ArrayList<>();
    private Map<Integer, List<Integer>> differentDecks = new HashMap<>();
    private Map<Integer, ImageView> idImage = new HashMap<>();
    private Map<Integer, String> idImagePath = new HashMap<>();

    public MemoryScreen(GameController setUpController) {
        gameControl = setUpController;
        gameData = gameControl.initializeGame(GameTypes.MEMORY);
        playerNames = gameControl.getPlayerNames();
        System.out.println(playerNames);
        initDiffDecks();
        initializeImageMap(differentDecks);
        addCards();
    }

    private void initDiffDecks() {
        currentPair = new ArrayList<>();
        currentPairImg = new ArrayList<>();
        differentDecks = gameControl.requestCards();
    }

    private Image imageGetter(String path) {
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
    }

    private ImageView getIDImage(int id) {
        String imagePath = gameControl.getImagePath(id);
        Image cardImage = imageGetter(imagePath);
        return new ImageView(cardImage);
    }

    private void setCardFace(int id, boolean faceUp) {
        ImageView card = idImage.get(id);
        String imagePath;
        if (faceUp||cheatCodeActive) {
            imagePath = idImagePath.get(id);
        } else {
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
        gameScene.setOnKeyPressed(e-> handleKeyPressed(e.getCode()));
        double i = 0;
        double j = 0;
        for (Integer key : differentDecks.keySet()) {
            //playingCards is a list of IDs for that the pile "key"
            List<Integer> playingCards = differentDecks.get(key);
            double drawPileX = Double.parseDouble((String) gameData.get(CARDPILEX));
            double drawPileY = Double.parseDouble((String) gameData.get(CARDPILY));
            double xOffset = Double.parseDouble((String) gameData.get(XOFFSET));
            double yOffset = Double.parseDouble((String) gameData.get(YOFFSET));
            setUponScreen(playingCards, 0, xOffset, i, j, drawPileX, drawPileY);
            j = j + yOffset;
        }
    }

    private void setUponScreen(List<Integer> playingCards, double v, double v1, double i, double j, double XPos, double YPos) {
        for (Integer cardID : playingCards) {
            ImageView cardImage = idImage.get(cardID);
            // TODO: use this way to keep bottom pule always back face up?
            setCardFace(cardID, false);
            cardImage.setFitWidth(Double.parseDouble((String) gameData.get(CARDWIDTH)));
            cardImage.setFitHeight(Double.parseDouble((String) gameData.get(CARDHEIGHT)));
            cardImage.setLayoutX(XPos + i - cardImage.getLayoutBounds().getMinX());
            cardImage.setLayoutY(YPos + j - cardImage.getLayoutBounds().getMinY());
            setUpListeners(cardImage);
            j = j + v;
            i = i + v1;
            gameScene.getChildren().add(cardImage);
        }
    }

    private void handleKeyPressed(KeyCode code){
        initDiffDecks();
        if(code==KeyCode.Q){
            cheatCodeActive = true;
            for(Integer pile: differentDecks.keySet()){
                for(Integer id: differentDecks.get(pile)){
                    setCardFace(id, true);
                }
            }

        }
    }


    private void setUpListeners(ImageView cardImage) {
        if (getCardID(idImage, cardImage) < 0) return;
        cardImage.setOnMousePressed(mouseEvent -> {
            int cardID = getCardID(idImage, cardImage);
            if (!(currentPair.size() == 2)) {
                setCardFace(cardID, true);
                if (!currentPair.contains(cardID)) {
                    currentPair.add(cardID);
                    currentPairImg.add(cardImage);
                }
            }
            cardImage.setCursor(Cursor.HAND);
        });
        cardImage.setOnMouseReleased(mouseEvent -> {
            if (currentPair.size() == 2) {
                try {
                    TimeUnit.MILLISECONDS.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean success = checkUpdate(currentPair);
                initDiffDecks();
            }
        });
        cardImage.setOnMouseEntered(mouseEvent -> cardImage.setCursor(Cursor.HAND));
    }

    private boolean checkUpdate(List<Object> currentPair) {
        List<Object> ret = gameControl.updateProtocol(currentPair);
        boolean success = (Integer) ret.get(0) == 1;
        if (success) {
            gameScene.getChildren().removeAll(currentPairImg);
            numCompletePairs++;
        }else { //can't keep this out of else because if img is removed u can't flip
            setCardFace((Integer) currentPair.get(0), false);
            setCardFace((Integer) currentPair.get(1), false);
        }
        if (ret.size() == 2) {
            userInterface.setWinScreen((String) gameData.get("gameName"), playerNames.get(0), numCompletePairs);
        }
        return success;
    }

    @Override
    public Scene getScene(UserInterface ui) {
        userInterface = ui;
        setCommonButtons(ui);
        String gameBackground = (String) gameData.get(GAMEBACK);
        Image background = imageGetter(gameBackground);
        setCommonButtons(ui);
        ImagePattern backgroundPattern = new ImagePattern(background);
        return new Scene(gameScene, ui.getWidth(), ui.getHeight(), backgroundPattern);
    }

}
