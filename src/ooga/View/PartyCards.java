package ooga.View;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class PartyCards {
    private VboxFactory cardHolder;
    private Label cardText;
    private Rectangle imageViewLike;
    private String styling="-fx-border-color: black;-fx-background-color: rgba(0, 0, 0, 0.8);-fx-padding: 2 2 2 2 ";

    public PartyCards(Integer pileIndex){
        cardHolder = new VboxFactory(10, pileIndex,styling);
        cardText = new Label();
        cardText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
        cardText.setWrapText(true);
        cardHolder.setAlignment(Pos.CENTER);
        if(pileIndex==0){
            cardText.setTextFill(Color.BLACK);
        }else {
        cardText.setTextFill(Color.WHITE);}
        imageViewLike = new Rectangle(50,4);
        cardHolder.getChildren().addAll(cardText,imageViewLike);
    }

    public VboxFactory getScene(){

        if(cardHolder.getFace() && cardHolder.getChildren().size()<=0){
            cardHolder.getChildren().addAll(cardText,imageViewLike);
        } else if(cardHolder.getFace()==false && cardHolder.getChildren().size()>0){
            cardHolder.getChildren().remove(cardText);
            cardHolder.getChildren().remove(imageViewLike);
        }
        return cardHolder;
    }

    public void changeFace(){
        cardHolder.setFace();
    }
    public  void changeFace(Boolean value){
        cardHolder.setFace(value);
    }

    public void  setText(String prompt){
        cardText.setText(prompt);

    }
    public void clearCard(){
        cardHolder.resetCard(cardHolder, cardText,imageViewLike);

    }



}
