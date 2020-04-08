package ooga.Model.Cards;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PokerCard extends AbstractCard {
    public PokerCard(){
        super();
    }

    @Override
    public Group getGroup() {
        Group singleCard = new Group();
//        getNumberLabel().setText(String.valueOf(getNumber()));
//        getNumberLabel().setText(String.valueOf(getID()));
        ImageView cardImageView = new ImageView();
        cardImageView.setImage(getImage());
//        getValueLabel().setText(getValue());
        singleCard.getChildren().addAll(cardImageView);
        return singleCard;
    }
}
