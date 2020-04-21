package ooga.Controller;

import javafx.scene.image.ImageView;
import ooga.Model.Games.GameDriver;
import ooga.Model.Games.HumanityDriver;
import ooga.Model.Games.SolitaireDriver;
import ooga.Model.Games.UnoDriver;
import ooga.View.GameScreens.GameScreen;
import org.json.simple.parser.ParseException;
//import ooga.View.Game;
//import ooga.View.GameScreens.GameScreen;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GameController {
    private static final List<String> DEFAULT_GAMES = List.of(
            SolitaireDriver.class.getName(), HumanityDriver.class.getName(), UnoDriver.class.getName());

    GameDriver currentGame;
    GameScreen currentScreen;

    public GameController(){

    }

    public void initializeGame(GameTypes type){
        int index;
        switch (type){
            case SOLITAIRE:
                index = 0; break;
            case HUMANITY:
                index = 1; break;
            case UNO:
                index = 2; break;
            default:
                index = 0; break;
        }

        try {
            Constructor<?> constructor = Class.forName(DEFAULT_GAMES.get(index)).getDeclaredConstructors()[0];
            currentGame =(GameDriver) constructor.newInstance(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //use reflections to make an instance of the appropriate game class and assign to currentGame
    }

    //TODO: Front end needs to implement this
    public void giveGameScreen (GameScreen game){
        currentScreen = game;
    }

    private void testJson(){
        String path = "data/solitaire.json";
        Map<String, Object> ret = null;
        try {
            ret = JSONReader.getData(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (String s : ret.keySet()){
            System.out.println(s + ": ");
            System.out.println(ret.get(s));
        }

    }

    public List<Object> updateProtocol(List<Object> args){
        List<Object> ret = currentGame.updateProtocol(args);
        return ret;
    } //return a list when win condition is met. Score is updated


    public Object requestCards (){
        //for solitaire this is a Map<Integer, List<Integer>>
        return currentGame.sendCards();
    }
    public String getImagePath(int cardID) {
        return currentGame.getCardImagePath(cardID);
    }
    public boolean isFaceUp(int cardID){
        return currentGame.IsCardFaceUp(cardID);
    }
    public void setToFaceUp(int cardID){
        currentGame.setFaceUp(cardID);
    }

    public void makePlayer(String playerName){
        currentGame.makePlayer(playerName);
    }
    public String getValue(int cardID){
        return currentGame.getCardValue(cardID);
    }
    public void pauseGame(){}
    public void resumeGame(){}

    public static void main(String[] args) {
        GameController test = new GameController();
        test.testJson();
    }
}
