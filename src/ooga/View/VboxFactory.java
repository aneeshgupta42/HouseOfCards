package ooga.View;

import javafx.scene.control.Label;
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
        face = false;
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

    public void resetCard(VboxFactory cardHolder, Label cardText, Rectangle imageViewLike) {
        if (!cardHolder.getFace() && cardHolder.getChildren().size() > 0) {
            cardHolder.getChildren().removeAll();
        } else if (cardHolder.getFace() && cardHolder.getChildren().size() <= 0) {
            cardHolder.getChildren().addAll(cardText, imageViewLike);
        }
    }

    public void resetCard(){resetCard(this,(Label)this.getChildren().get(0), (Rectangle)this.getChildren().get(1));}
}
