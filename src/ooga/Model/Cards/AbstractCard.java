package ooga.Model.Cards;

import javafx.scene.image.Image;

public abstract class AbstractCard implements Playable {
    private int CardNumber; //number set to 0 if numbers are not relevant
    private Image CardImageFront;
    private Image getCardImageBack;
    private String CardValue; //uses shape value
    private int ID;
    @Override
    public void setID(int num) {
        ID= num;
    }

    @Override
    public void setNumber(int num) {
        CardNumber = num;
    }

    @Override
    public void setImage(Image image) {
        CardImageFront = image;
    }

    @Override
    public void setValue(String value) {
        CardValue = value;
    }

    @Override
    public int getNumber() {
        return CardNumber;
    }

    @Override
    public Image getImage() {
        return CardImageFront;
    }

    @Override
    public String getValue() {
        return CardValue;
    }

    @Override
    public int getID() {
        return ID;
    }

}
