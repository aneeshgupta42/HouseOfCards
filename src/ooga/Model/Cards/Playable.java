package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Playable {
    boolean isFaceUp();

    void setFaceUp(boolean faceUp);

    void setID(int num);

    void setNumber(int num);

    void setFrontImageView(ImageView image);

    ImageView getFrontImageView();

    ImageView getBackImageView();

    void setBackImageView(ImageView image);

    void setValue(String value);

    int getNumber();

    /**
     * returns the front image or the back image of a card depending on its faceUp boolean
     * @return
     */
    ImageView getImageView();

    String getValue();

    int getID();

}
