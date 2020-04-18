package ooga.Model.Cards;

import javafx.scene.image.ImageView;

public abstract class AbstractCard implements Playable {
    private int cardNumber; //number set to 0 if numbers are not relevant
    private String cardFrontImagePath;
    private String cardValue; //uses shape value
    private int ID;
    private boolean faceUp;

    @Override
    public boolean isFaceUp() {
        return faceUp;
    }

    @Override
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
    @Override
    public void setID(int num) {
        ID= num;
    }

    @Override
    public void setNumber(int num) {
        cardNumber = num;
    }


    @Override
    public void setValue(String value) {
        cardValue = value;
    }

    @Override
    public int getNumber() {
        return cardNumber;
    }

    @Override
    public String getCardImagePath() {
        return cardFrontImagePath;
    }

    @Override
    public void setCardFrontImagePath(String cardFrontImagePath) {
        this.cardFrontImagePath = cardFrontImagePath;
    }

    @Override
    public String getValue() {
        return cardValue;
    }

    @Override
    public int getID() {
        return ID;
    }

}
