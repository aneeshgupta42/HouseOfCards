package ooga.usecases;

import ooga.api.*;
import javafx.scene.image.Image;


public class MockDemo{

    Viewable myView;
    Playable card;
    User user;
    Deck deck;
    Game game;

//    Use Case 1: Moving a card to a pile of cards
//    The user will have clicked on a card in the frontend
//    All this will be happening as a consequence of the onclick()
//    We will get the final X and Y as inputs from the mouse
    int id = card.getID();
    int x = 0;
    int y = 0;
    myView.moveCard(id, x, y);
    deck = myView.getDeck(x, y)
    deck.addCard(id);

//  Use case 2: Pausing a game: again, through a button
    game.pauseGame();

//  User case 3: Check Win
    if(game.checkWin()){
        myView.endGame();
        myView.gameMessage("Game won");
    }

//  User case 4: Add another game (multiple games at once)
    Game newGame = myView.generateGame();
    newGame.startGame();
//    This is where the new control, and backend will be initialized

//  User case 5: Download curren game
    myView.downloadGame(game);
//  This in turn would call
    game.saveToFile();
// This is where the XML writer would be intialized and written out

}