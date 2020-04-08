package ooga.Model.Cards;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Playable {
    void setID(int num);

    void setNumber(int num);

    void setImage(Image image);

    void setValue(String value);

    int getNumber();

    Image getImage();

    String getValue();

    int getID();

    abstract ImageView getImageView();

}
