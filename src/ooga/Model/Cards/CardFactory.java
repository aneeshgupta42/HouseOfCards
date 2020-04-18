package ooga.Model.Cards;

import ooga.Controller.DeckType;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class CardFactory {
   static AtomicInteger atomicInteger = new AtomicInteger();
   public static void initializeDeck(CardDeck deck, DeckType deckType){
      String path = deckType.toString().toLowerCase().strip();
      ResourceBundle resource = getResourceBundleFromPath(path);
      int size = resource.keySet().size();
      for (int i = 0; i < size; i++){
         int cardID = atomicInteger.incrementAndGet();
         String propFileKey = Collections.list(resource.getKeys()).get(i);
         String imageName = resource.getString(propFileKey);

         String [] removeFileExtension = imageName.split("\\.");
         String [] attributes = removeFileExtension[0].split("_");
         //System.out.println("data/cardDecks/" + path + "/" + imageName);
         String imagePath = "cardDecks/" + path + "/" + imageName;
         Playable card = makeCard(deckType, imagePath, attributes, cardID);
         deck.addCard(card);
      }
      }

      public static Playable makeACopy(Playable card, DeckType deckType){
         Playable ret;
         int cardID = atomicInteger.incrementAndGet();
         switch (deckType){
//            case UNO:
//            case HUMANITY:
            default:
               ret = new PokerCard(card.getCardFrontImagePath(), card.getNumber(), card.getValue(), cardID);
         }
         return ret;
      }

   private static Playable makeCard(DeckType deckType, String imagePath, String[] attributes, int id) {
      Playable card;
      switch (deckType){
         case HUMANITY_QUES:
            card = null;
            break;
         case HUMANITY_ANS:
            card = null;
            break;
         case UNO:
            card = null;
            break;
         default: //for poker cards
            card = new PokerCard(imagePath, Integer.parseInt(attributes[0]), attributes[1], id);

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


