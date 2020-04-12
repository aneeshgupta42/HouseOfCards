package ooga.Model.Cards;

import javafx.scene.image.ImageView;

public interface Playable {
    boolean isFaceUp();

    void setFaceUp(boolean faceUp);

    void setID(int num);

    void setNumber(int num);

    void setFrontImageView(ImageView image);

    void setBackImageView(ImageView image);

    void setValue(String value);

    int getNumber();

    ImageView getImageView();

    String getValue();

    int getID();

}
