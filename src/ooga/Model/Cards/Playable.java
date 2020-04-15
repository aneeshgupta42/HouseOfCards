package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Playable {
    boolean isFaceUp();

    void setFaceUp(boolean faceUp);

    void setID(int num);

    void setNumber(int num);

    void setValue(String value);

    int getNumber();

    /**
     * Get the image path of
     * @return
     */
    String getCardFrontImagePath();

    void setCardFrontImagePath(String cardFrontImagePath);

    String getValue();

    int getID();

}
