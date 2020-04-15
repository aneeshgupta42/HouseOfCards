package ooga.Model.Cards;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Controller.CardColors;
import ooga.Controller.DeckType;
import ooga.Controller.GameTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class CardFactory {

   public static void initializeDeck(CardDeck deck, DeckType deckType, CardColors color){
      AtomicInteger atomicInteger = new AtomicInteger();
      int cardID = atomicInteger.incrementAndGet();
      String path = deckType.toString().toLowerCase().strip();
      ResourceBundle resource = getResourceBundleFromPath(path);
      int size = resource.keySet().size();
      for (int i = 0; i < size; i++){
         String propFileKey = Collections.list(resource.getKeys()).get(i);
         String imageName = resource.getString(propFileKey);

         String [] removeFileExtension = imageName.split("\\.");
         String [] attributes = removeFileExtension[0].split("_");
         //System.out.println("data/cardDecks/" + path + "/" + imageName);
         JFXPanel jfxPanel = new JFXPanel(); //for testing
         ImageView image = new ImageView("cardDecks/" + path + "/" + imageName);
         Playable card = makeCard(deckType, color, image, attributes, cardID);
         deck.addCard(card);
      }
      }

      public static Playable makeACopy(Playable card, DeckType deckType){
         Playable ret;
         ImageView frontImage;
         ImageView backImage;
         AtomicInteger atomicInteger = new AtomicInteger();
         int cardID = atomicInteger.incrementAndGet();
         switch (deckType){
//            case UNO:
//            case HUMANITY:
            default:
               frontImage = new ImageView(card.getFrontImageView().getImage());
               backImage = new ImageView(card.getBackImageView().getImage());
               ret = new PokerCard(frontImage, backImage, card.getNumber(), card.getValue(), cardID);
         }
         return ret;
      }

   private static Playable makeCard(DeckType deckType, CardColors color, ImageView image, String[] attributes, int id) {
      Playable card;
      switch (deckType){
         case HUMANITY:
            card = null;
            break;
         case UNO:
            card = null;
            break;
         default: //for poker cards
            ImageView backImage = new ImageView("cardDecks/poker/" + color.toString().toLowerCase() + "_back.png");
            card = new PokerCard(image, backImage, Integer.parseInt(attributes[0]), attributes[1], id);

      }
      return card;
   }

   private static ResourceBundle getResourceBundleFromPath(String path) {
      try {
         //System.out.println("data/cardDecks/" + path + "/" + path);
         File file = new File("data/cardDecks/" + path);
         URL[] urls = {file.toURI().toURL()};
         ClassLoader loader = new URLClassLoader(urls);
         return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
      } catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }
   }


