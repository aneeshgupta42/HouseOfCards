package ooga.Model.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardFactory {

   public static void initializeDeck(CardDeck deck, DeckType deckType, CardColors color){
      String path = deckType.toString().toLowerCase();
      ResourceBundle resource = getResourceBundleFromPath(path);
      int size = resource.keySet().size();
      for (int i = 0; i < size; i++){
         String imageName = Collections.list(resource.getKeys()).get(i);
         String [] attributes = imageName.split("_");
         Image image = new Image("data/cardDecks/" + path + "/" + imageName);
         AbstractCard card = makeCard(deckType, color, image, attributes, i);
         deck.addCard(card);
      }
      }

   private static AbstractCard makeCard(DeckType deckType, CardColors color, Image image, String[] attributes, int id) {
      AbstractCard card;
      switch (deckType){
         case HUMANITY:
            card = null;
            break;
         case UNO:
            card = null;
            break;
         default: //for poker cards
            ImageView backImage = new ImageView("data/cardDecks/poker" + color.toString().toLowerCase() + "_back.png");
            card = new PokerCard(new ImageView(image), backImage, Integer.parseInt(attributes[0]), attributes[1], id);

      }
      return card;
   }

   private static ResourceBundle getResourceBundleFromPath(String path) {
      try {
         File file = new File("data/cardDecks/" + path + "/" + path);
         URL[] urls = {file.toURI().toURL()};
         ClassLoader loader = new URLClassLoader(urls);
         return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
      } catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }
   }


