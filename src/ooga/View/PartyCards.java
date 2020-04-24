package ooga.View;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.List;

public class PartyCards {
    private VboxFactory cardHolder;
    private Label cardText;
    private Rectangle imageViewLike;
    private ImageView imageView;
    private List<String> colors = List.of("rgba(0,0,0,1)", "rgba(100,100,50,1)", "rgba(200,200,150,1)", "rgba(150,200,150,1)","rgba(170,100,170,1)", "rgba(230,100,130,1)" );
    private String styling="-fx-border-color: black;-fx-padding: 2 2 2 2;-fx-background-color:  ";
    public PartyCards(Integer pileIndex, Image image){
        String colorBackground = chooseColor(pileIndex);
        cardHolder = new VboxFactory(10, pileIndex,colorBackground);
        cardText = new Label();
        imageView= new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(110);
        cardText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
        cardText.setWrapText(true);
        imageView.setImage(image);
        cardHolder.setAlignment(Pos.CENTER);
        if(pileIndex==0){
            cardText.setTextFill(Color.BLACK);
        }else {
        cardText.setTextFill(Color.WHITE);}
        imageViewLike = new Rectangle(50,4);
        cardHolder.getChildren().addAll(cardText,imageViewLike);
    }

    private String chooseColor(Integer index) {
       String colorsBack = styling+colors.get(index);
       return colorsBack;
    }

    public VboxFactory getScene(){
        if(cardHolder.getFace() && cardHolder.getChildren().size()<=0){
            cardHolder.getChildren().addAll(cardText,imageViewLike);
            cardHolder.getChildren().remove(imageView);
        } else if(cardHolder.getFace()==false && cardHolder.getChildren().size()>1 ){
            cardHolder.getChildren().remove(cardText);
            cardHolder.getChildren().remove(imageViewLike);
            cardHolder.getChildren().add(imageView);
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
        cardHolder.resetCard(cardHolder, cardText,imageViewLike,imageView);

    }



}
