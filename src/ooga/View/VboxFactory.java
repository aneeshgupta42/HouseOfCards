package ooga.View;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class VboxFactory extends VBox {
    private Integer Boxindex;
    private Boolean face;
    public VboxFactory(VboxFactory copy){
        face = false;
    }
    public VboxFactory(Integer index){
        setIndex(index);
        face= false;
    }
    public VboxFactory(Integer spacing, Integer index, String styling){
        setSpacing(spacing);
        setIndex(index);
        setStyle(styling);
        if(index==0){
            face= true;
        } else {
            face = false;
        }
    }

    private void setIndex(Integer index){
        Boxindex = index;
    }

    public Integer getIndex(){
        return Boxindex;
    }

    public Boolean getFace(){
        return face;
    }

    public void setFace(){
        face= !face;
    }
    public void setFace(Boolean value){
        face= value;
    }

    public void resetCard(VboxFactory cardHolder, Label cardText, Rectangle imageViewLike, ImageView image) {
        if (!cardHolder.getFace() && cardHolder.getChildren().size() > 1) {
            cardHolder.getChildren().removeAll(cardText, imageViewLike);
            cardHolder.getChildren().add(image);

        } else if (cardHolder.getFace() && cardHolder.getChildren().size() <= 1) {
            cardHolder.getChildren().addAll(cardText, imageViewLike);
            cardHolder.getChildren().remove(image);
        }
    }

    public void resetCard(){resetCard(this,(Label)this.getChildren().get(0), (Rectangle)this.getChildren().get(1), (ImageView)this.getChildren().get(2));}
}
