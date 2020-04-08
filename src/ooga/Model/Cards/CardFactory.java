package ooga.Model.Cards;

import ooga.Controller.GameTypes;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardFactory {

   public static void initializeDeck(CardDeck deck, GameTypes type){
      switch(type){
         case SOLITAIRE:

      }
      ResourceBundle resource = getResourceBundleFromPath("poker");
      int size = resource.keySet().size();
      for (int i = 0; i < size; i++){

      }
      }

   private static ResourceBundle getResourceBundleFromPath(String path) {
      try {
         File file = new File("data/cardDecks");
         URL[] urls = {file.toURI().toURL()};
         ClassLoader loader = new URLClassLoader(urls);
         return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
      } catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }
   }


