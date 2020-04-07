package ooga.Model;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card implements Playable{
    private int CardNumber;
    private Image CardImage;
    private String CardValue;
    private Label number = new Label();
    private Label value = new Label();
    private Label IDLabel= new Label();
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
    public void SetImage(Image image) {
        CardImage = image;
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
        return CardImage;
    }

    @Override
    public String getValue() {
        return CardValue;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Group getGroup() {
        Group singleCard = new Group();
        number.setText(String.valueOf(CardNumber));
        IDLabel.setText(String.valueOf(ID));
        ImageView cardImageView = new ImageView();
        cardImageView.setImage(CardImage);
        value.setText(CardValue);
        singleCard.getChildren().addAll(number,cardImageView,value,IDLabel);
        return singleCard;
    }
}
