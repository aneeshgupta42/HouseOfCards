package ooga.api;

import javafx.scene.image.Image;

public interface Playable {
    void makeCard(String cardType);

    void setNumber(int num);

    void SetImage(Image image);

    void setValue(String value);

    int getNumber();

    Image getImage();

    String getValue();
}

