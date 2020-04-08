package ooga.Model.Cards;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PokerCard extends AbstractCard {
    public PokerCard(Image image, int cardNum, String cardValue, int id){
        setNumber(cardNum);
        setValue(cardValue);
        setID(id);
    }


    public ImageView getImageView() {
        ImageView cardImageView = new ImageView();
        cardImageView.setImage(getImage());
        return cardImageView;
    }
}
