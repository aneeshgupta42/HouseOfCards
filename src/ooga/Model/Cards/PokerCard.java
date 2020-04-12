package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PokerCard extends AbstractCard {
    public PokerCard(ImageView frontImage, ImageView backImage, int cardNum, String cardValue, int id){
        setFrontImageView(frontImage);
        setBackImageView(backImage);
        setNumber(cardNum);
        setValue(cardValue);
        setID(id);
    }
    public void test(){}
}
