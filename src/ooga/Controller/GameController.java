package ooga.Controller;

import ooga.Model.Games.GameDriver;
import ooga.Model.Games.HumanityDriver;
import ooga.Model.Games.SolitaireDriver;
import ooga.Model.Games.UnoDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GameController {
    private static final List<String> DEFAULT_GAMES = List.of(
            SolitaireDriver.class.getName(), HumanityDriver.class.getName(), UnoDriver.class.getName());

    GameDriver currentGame;

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
            currentGame =(GameDriver) constructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //use reflections to make an instance of the appropriate game class and assign to currentGame
    }

    public void updateProtocol(){} //return a boolean when win condition is met. Score
    public void makePlayer(String playerName){
        currentGame.makePlayer(playerName);
    }
    public void pauseGame(){}
    public void resumeGame(){}

}
