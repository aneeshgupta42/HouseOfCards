package ooga.Controller;

import ooga.Model.Games.GameDriver;

public class GameController {
    GameDriver currentGame;
    public void initializeGame(GameTypes type){
        //use reflections to make an instance of the appropriate game class and assign to currentGame
    }

    public void updateProtocol(){} //return a boolean when win condition is met. Score
    public void makeUser(String playerName){
        currentGame.makePlayer(playerName);
    }
    public void pauseGame(){}
    public void resumeGame(){}

}
