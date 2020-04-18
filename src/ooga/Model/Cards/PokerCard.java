package ooga.Model.Cards;

public class PokerCard extends AbstractCard {
    public PokerCard(String path, int cardNum, String cardValue, int id){
        setCardImagePath(path);
        setNumber(cardNum);
        setValue(cardValue);
        setID(id);
    }
    public void test(){}
}
