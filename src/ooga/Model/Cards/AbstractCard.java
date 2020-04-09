package ooga.Model.Cards;

import javafx.scene.image.ImageView;

public abstract class AbstractCard implements Playable {
    private int cardNumber; //number set to 0 if numbers are not relevant
    private ImageView cardFrontImage;
    private ImageView cardBackImage;
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
    public void setFrontImageView(ImageView image) {
        cardFrontImage = image;
    }

    @Override
    public void setBackImageView(ImageView image) {
        cardBackImage = image;
    }

    @Override
    public ImageView getBackImageView() {
        return cardBackImage;
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
    public ImageView getFrontImageView() {
        return cardFrontImage;
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
