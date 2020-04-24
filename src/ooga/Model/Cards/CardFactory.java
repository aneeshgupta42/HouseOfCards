package ooga.Model.Cards;

import ooga.Controller.DeckType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CardFactory {
   static AtomicInteger atomicInteger = new AtomicInteger();
   public static void initializeDeck(CardDeck deck, DeckType deckType){
      switch (deckType) {
         case POKER:
            initializePoker(deck, deckType);
            break;
         case HUMANITY_ANS:
         case HUMANITY_QUES:
            initializeHumanity(deck, deckType);
            break;
      }
   }

   private static void initializeHumanity(CardDeck deck, DeckType deckType) {
      String nameOfDir = deckType.toString().toLowerCase().strip();
      String path = "cardDecks/" + nameOfDir + "/" + nameOfDir + ".txt";
      File file = new File("data/cardDecks/" + nameOfDir + "/" + nameOfDir + ".txt");
      Scanner sc = new Scanner(System.in);
      try {
         sc = new Scanner(file);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

      while (sc.hasNextLine()){
         String value = sc.nextLine();
         int cardID = atomicInteger.incrementAndGet();
         if (value.contains("_")){
            String [] ques = value.split("_");
            String finalValue = "";
            for (String s : ques){
               if (s.equals(ques[ques.length - 1])){finalValue += s;}
               else{finalValue += s + "_____";}
            }
            String [] attributes = new String[1];
            attributes[0] = finalValue;
            deck.addCard(makeCard(deckType, "", attributes, cardID));
         }
         else{
            String [] attributes = new String[1];
            attributes[0] = value;
            deck.addCard(makeCard(deckType, "", attributes, cardID));
         }
      }



   }

   private static void initializePoker(CardDeck deck, DeckType deckType){
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
               ret = new PokerCard(card.getCardImagePath(), card.getNumber(), card.getValue(), cardID);
         }
         return ret;
      }

   private static Playable makeCard(DeckType deckType, String imagePath, String[] attributes, int id) {
      Playable card;
      switch (deckType){
         case HUMANITY_ANS:
         case HUMANITY_QUES:
            card = new HumanityCard(attributes[0], id);
            break;
//         case UNO:
//            card = null;
//            break;
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


