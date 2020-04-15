package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PokerCard extends AbstractCard {
    public PokerCard(String path, int cardNum, String cardValue, int id){
        setCardFrontImagePath(path);
        setNumber(cardNum);
        setValue(cardValue);
        setID(id);
    }
    public void test(){}
}
