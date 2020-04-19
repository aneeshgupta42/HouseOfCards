package ooga.View.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSet {
    private List<ImageView> cardsList;
    private ImageView headCard;

    public CardSet(ImageView topCard, Map<Integer, ImageView> idImage, Map<Integer, List<Integer>> diffDecks) {
        headCard = topCard;
        cardsList = new ArrayList<>();
        int topID = getKey(idImage, topCard);
        for (Integer pile : diffDecks.keySet()) {
            List<Integer> currPile = diffDecks.get(pile);
            if (currPile.contains(topID)) {
                for (int i = currPile.indexOf(topID); i < currPile.size(); i++) {
                    int id = currPile.get(i);
                    cardsList.add(idImage.get(id));
                }
            }
        }
    }

    public void toFront(){
        for(ImageView card: cardsList){
            card.toFront();
        }
    }

    public void winProtocol(){
        System.out.println(cardsList);
        for (ImageView card: cardsList){
            card.setLayoutY(400-card.getLayoutBounds().getMinY()); card.setLayoutX(400-card.getLayoutBounds().getMinX());
        }

    }

    public void setLayoutX(double finalX){
        for(ImageView card: cardsList) {
            card.setLayoutX(finalX);
        }
    }

    public void setLayoutY(Double finalY){
        Double headInitY = headCard.getLayoutY();
        for(ImageView card: cardsList){
            card.setLayoutY(finalY + card.getLayoutY()-headInitY);
        }

    }

    public double getLayoutX(){
        return 0;
    }

    public double getLayoutY(){
        return 0;
    }


    private Integer getKey(Map<Integer, ImageView> map, ImageView v) {
        for (Integer check : map.keySet()) {
                if (map.get(check).equals(v)) {
                    return check;
                }
            }
        System.out.println("key not found");
        return -1;
    }

    public ImageView getHeadCard() {
        return headCard;
    }
}




