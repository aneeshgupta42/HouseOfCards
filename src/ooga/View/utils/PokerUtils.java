package ooga.View.utils;

import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public class PokerUtils {
    public PokerUtils(){

    }
    public Integer getCardID(Map<Integer, ImageView>map, ImageView card){
        for (Integer check : map.keySet()) {
            if (map.get(check).equals(card)) {
                return check;
            }
        }
        System.out.println("id not found");
        return -1;
    }
}
