package ooga.Controller;

import ooga.Model.Games.*;
import ooga.View.GameScreens.GameScreen;
import ooga.View.utils.GameException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
//import ooga.View.Game;
//import ooga.View.GameScreens.GameScreen;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

//TODO: deal with makePlayer
public class GameController {
    //    private static final List<String> DEFAULT_GAMES = List.of(
//            SolitaireDriver.class.getName(), HumanityDriver.class.getName(),
//            MemoryDriver.class.getName(), GOPDriver.class.getName());
    GameDriver currentGame;
    List<String> playerNames;
    String gameName;
    GameScreen currentScreen;

    public GameController() {
        playerNames = new ArrayList<>();
    }

    public GameScreen getGameScreen(String gameName, List<String> playerNames) {
        this.gameName = gameName.toLowerCase();
        Map<String, Object> ret = readJSON("gameScreen", this.gameName);
        String gameScreenClass = (String) ret.get("className");
        GameScreen gameScreen = null;
        this.playerNames = playerNames;
        try {
            Constructor<?> constructor = Class.forName(gameScreenClass).getDeclaredConstructors()[0];
            gameScreen = (GameScreen) constructor.newInstance((GameController) this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new GameException("Constructor Error");
        }
        return gameScreen;
    }

    public Map<String, Object> initializeGame(GameTypes type) {
        Map<String, Object> m = readJSON("gameDriver", this.gameName);
        String driverClassName = (String) m.get("className");

        try {
            Constructor<?> constructor = Class.forName(driverClassName).getDeclaredConstructors()[0];
            currentGame = (GameDriver) constructor.newInstance(this, playerNames);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            new GameException(e.getMessage());
        }
        //use reflections to make an instance of the appropriate game class and assign to currentGame
        Map<String, Object> ret = readJSON("view", type.toString().toLowerCase().strip());
        return ret;
    }


    private Map<String, Object> readJSON(String dataRequestedFor, String gameName) {
        String path = "data/gameFiles/" + gameName + ".json";
        Map<String, Object> ret = null;
        try {
            ret = JSONUtil.getData(dataRequestedFor, path);
        } catch (IOException e) {
            new GameException(e.getMessage());
        } catch (ParseException e) {
            new GameException(e.getMessage());
        }
        return ret;
    }

    public List<Object> updateProtocol(List<Object> args) {
        List<Object> ret = currentGame.updateProtocol(args);
        return ret;
    } //return a list when win condition is met. Score is updated


    public Map<Integer, List<Integer>> requestCards() {
        //for solitaire this is a Map<Integer, List<Integer>>
        return currentGame.sendCards();
    }

    public String getImagePath(int cardID) {
        return currentGame.getCardImagePath(cardID);
    }

    public boolean isFaceUp(int cardID) {
        return currentGame.IsCardFaceUp(cardID);
    }

    public void setToFaceUp(int cardID) {
        currentGame.setFaceUp(cardID);
    }

    public List<String> getPlayerNames() {
        return currentGame.getPlayerNames();
    }

    public int getPlayerScore(String playerName) {
        return currentGame.getPlayerScore(playerName);
    }


    //TODO: getHighScores

    public void updateHighScore() {
        List<Object> data = getWinner();
        String score = String.valueOf(data.get(0));
        String playerName = (String) data.get(1);
        String path = "data/highScores/highScores.json";
        int oldScore = 0;
        try {
            Map<String, Object> m = JSONUtil.getData(gameName, path);
            for (String s : m.keySet()) {
                oldScore = Integer.parseInt((String) m.get(s));
            }
        } catch (IOException e) {
            new GameException(e.getMessage());
        } catch (ParseException e) {
            new GameException(e.getMessage());
        }
        if (Integer.parseInt(score) < oldScore) {
            return;
        }
        try {
            JSONUtil.modifyHighScore(gameName, playerName, score, path);
        } catch (IOException e) {
            new GameException(e.getMessage());
        } catch (ParseException e) {
            new GameException(e.getMessage());
        }
    }

    public Map<String, List<String>> getHighScores() {
        String path = "data/highScores/highScores.json";
        Map<String, List<String>> ret = new HashMap<>();
        try {
            Map<String, Object> m = JSONUtil.getData("", path);
            for (String game : m.keySet()) {
                Map<String, String> entry = (Map<String, String>) m.get(game);
                List<String> returnList = new ArrayList<>();
                for (String name : entry.keySet()) {
                    returnList.add(name);
                    returnList.add(entry.get(name));
                }
                ret.put(game, returnList);
            }

        } catch (IOException e) {
            new GameException(e.getMessage());
        } catch (ParseException e) {
            new GameException(e.getMessage());
        }
        return ret;
    }


    public List<Object> getWinner() {
        List<Integer> scores = new ArrayList<>();
        List<Object> sendInformation = new ArrayList<>();
        Map<Integer, String> mappingPlayers = new HashMap<>();
        for (String player : playerNames) {
            int score = getPlayerScore(player);
            scores.add(score);
            mappingPlayers.put(score, player);
        }
        Collections.sort(scores);
        sendInformation.add(scores.get(scores.size()-1));
        sendInformation.add(mappingPlayers.get(scores.get(scores.size()-1)));
        return sendInformation;

    }

    public void makePlayers(List<String> playerName) {
        this.playerNames = playerName;
    }

    public String getValue(int cardID) {
        return currentGame.getCardValue(cardID);
    }

    public void pauseGame() {
    }

    public void resumeGame() {
    }

    public static void main(String[] args) {
        GameController test = new GameController();
        test.gameName = "humanity";
        Map<String, List<String>> temp = test.getHighScores();
        for (String s : temp.keySet()) {
            System.out.println(s);
            System.out.println(temp.get(s));
        }
//        test.getGameScreen("Solitaire");
//        Map<String, Object> m = test.initializeGame(GameTypes.SOLITAIRE);
//        for (String s : m.keySet()){
//            System.out.println(s + ": ");
//            System.out.println(m.get(s));
//        }

    }
}
