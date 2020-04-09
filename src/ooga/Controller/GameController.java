package ooga.Controller;

import ooga.Model.Games.GameDriver;
import ooga.Model.Games.HumanityDriver;
import ooga.Model.Games.SolitaireDriver;

import java.lang.reflect.Constructor;
import java.util.List;

public class GameController {
    private static final List<List<String>> DEFAULT_GAMES = List.of(
            List.of(SolitaireDriver.class.getName(), HumanityDriver.class.getName()));
    GameDriver currentGame;
    public void initializeGame(GameTypes type){
        //Constructor<?> constructor = Class.forName().getDeclaredConstructors()[0];
        //use reflections to make an instance of the appropriate game class and assign to currentGame
    }

    public void updateProtocol(){} //return a boolean when win condition is met. Score
    public void makeUser(String playerName){
        currentGame.makePlayer(playerName);
    }
    public void pauseGame(){}
    public void resumeGame(){}

}
