package ooga.View;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class PartyCards {
    private VboxFactory cardHolder;
    private Label cardText;
    private Rectangle imageViewLike;
    private String styling= "-fx-padding: 10;\" +\n" +
            "                \"-fx-border-style: solid inside;\" +\n" +
            "                \"-fx-border-width: 2;\" +\n" +
            "                \"-fx-border-insets: 5;\" +\n" +
            "                \"-fx-border-radius: 5;\" +\n" +
            "                \"-fx-border-color: blue;";

    public PartyCards(Integer pileIndex){
        cardHolder = new VboxFactory(10, pileIndex,styling);
        cardText = new Label();
        imageViewLike = new Rectangle(50,70);
        cardHolder.getChildren().addAll(cardText,imageViewLike);
    }

    public VboxFactory getScene(){
        if(cardHolder.getFace()){
            cardHolder.getChildren().addAll(cardText,imageViewLike);
        } else if(cardHolder.getFace()==false && cardHolder.getChildren().size()>0){
            cardHolder.getChildren().removeAll();
        }
        return cardHolder;
    }

    public void changeFace(){
        cardHolder.setFace();
    }

    public void setText(String prompt){
        cardText.setText(prompt);
    }
    public void clearCard(){
        cardHolder.resetCard(cardHolder, cardText,imageViewLike);

    }



}
