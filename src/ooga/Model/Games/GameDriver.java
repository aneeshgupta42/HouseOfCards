package ooga.Model.Games;

import ooga.Model.Cards.CardDeck;
import ooga.Model.Players.Player;

public abstract class GameDriver implements Game {
    private CardDeck[] decks;
    private Player [] playerList;
}
