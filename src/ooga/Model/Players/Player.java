package ooga.Model.Players;

import ooga.Model.Cards.CardDeck;

public class Player {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private String name;
    private int score;
    private CardDeck playerHand;

    public Player(String name){
        this.name = name;
        score = 0;
    }

    public Player(String name, CardDeck deck){
        this.name = name;
        playerHand = deck;
    }

    public void setDeck (CardDeck deck){
        this.playerHand = deck;
    }
}
