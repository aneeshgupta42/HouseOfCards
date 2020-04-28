# API_CHANGES.md
### Aneesh Gupta, Himanshu Jain, Tharun Raj
#### ag468, hkj4, tm272



### Backend:
#### Original:
- Game API:

        void makeUser();

        User getUser();

        boolean checkWin();

        void updateScore(int score);

        void changeLevel(int level);

        void makePlayer(User user);

        void pauseGame();

        void resumeGame();

        void startGame();
        
- Playable API:

        void makeCard(String cardType);

        void setNumber(int num);

        void SetImage(Image image);

        void setValue(String value);

        int getNumber();

        Image getImage();

        String getValue();

        int getID();
        
- Deck API:

        void addCard(int ID);

        void shuffleDeck();
        
- User API:

        boolean checkWin();

        int getID();

        String getScore();

Updated:
 - Game API:

    It was required since we changed the flow of our game and we also tried making the user experience better by using this class to communicate with the frontend. This is done using the controller but it gets values from the frontend and does logic handling. 

         void makePlayers(List<String> userName);
         - makes a player using the name entered so that the score can be updated

        List<String> getPlayerNames();
        - returns a list of all the players for a game for use by the frontend

        boolean checkWin();
        - checks if the game can be ended in solitaire

            /**
             * logic to update a game based on the current state when this method is invoked.
             * Main game logic resides here
             **/
        List<Object> updateProtocol(List<Object> args);

        void updateScore(int score, String playerName);
        - updates the score so that we can get a winner

        Object sendCards();
        - sends the cards that can be used by the frontend

        String getCardImagePath(int ID);
        - imagepath is stored in the backend and this is sent to the frontend using this

        int getPlayerScore(String playerName);
        - returns the player score so that the frontend could display this

        boolean IsCardFaceUp(int cardID);
        - checks whether the face should be up or down. Used for soiltaire

        void setFaceUp(int cardID);
        - sets the face up when the cards is clicked
- Playable API:
        
    This interface provides interactions with a `Card` object (which are things you can play, hence `Playable`). A card has multiple values associated with it; such as its unique ID, it's value, it's number, and whether it's face up or face down. The purpose of this interface is to provide access to set, configure, and read such values for any `Card` object.
    
          boolean isFaceUp();
          - checks whether the face of the card is up or down

        void setFaceUp(boolean faceUp);
        - sets the face to the desired value

        void setID(int num);
        - sets the ID that is unique for each card

        void setNumber(int num);
        - sets the number for the card 

        void setValue(String value);
        - sets a value which is useful for Humanity 
        
        int getNumber();
        - gives us the number on the card

        /**
         * Get the path of the image
         * @return
         */
        String getCardImagePath();
        - the path is being stored in the backend so this returns the path for that to be used by the frontend

        void setCardImagePath(String cardFrontImagePath);
        - the value is set in the backend using this

        String getValue();
        - returns the value to be used by the frontend

        int getID();
        - returns the ID that is used to access the cards in the frontend
        
- Deck API:

    This allows us to make a `Deck` of cards that is passed to the frontend. We can even changes the cards inside that deck and access the properties of those decks using this API. This was required in order to reflect the changes being made in the frontend by the user. 
    
        void addCard(Playable card);
        - adds a card to the deck to make the deck

        Playable getCardWithID(int cardID);
        - the ID is being used to get the properties of a card by the frontend

        /**
         * returns a list of the card IDs in a deck
         * @return
         */
        List<Integer> getIDList();
        - returs all the cards available in the deck

        DeckType getDeckType();
        - allows the frontend to check which cards are being used

        void shuffleDeck();
        - makes sure that the cards are new everytime
        int getDeckSize();
        - returns the number of cards in the deck

        void addCard(List<Playable> cards);
        - adds multiple cards to the deck

        List<Playable> getCards();
        - returns the cards for the frontend

        /**
         * Removes numOfCards Playable cards from the deck and returns it in a list.
         * @param numOfCards
         * @return
         */
        List<Playable> popCards(int numOfCards);

        void copyCardToNewDeck(int cardID, CardDeck dest);
        - copied the older deck to the newer deck

        void removeCard(int cardID);
        - removes the card from the deck using the ID. Used for Humanity and Truth and Dare

        /**
         * return a deep copy of a cardDeck object. All Playable objects in the deck will be copied as well.
         * @return
         */
        CardDeck returnADeepCopy();
        - allows the backend to store a copy of all the cards

        boolean isCardPresent(int cardID);
        - checks whether the card is present or not using the ID
        
### Frontend:

#### Original:
- `Viewable` Interface:
    
        void moveCard (int id, int x, int y);

        void updateScore(int score);

        void displayCard (CardView cardView);

        void makeDeck (Deck deck);

        void gameMessage (String message);

        void getNewCard (Deck deck);

        void shuffleDeck (int deckId);

        void generateGame (int gameChoice);

        
- `GameInterface` Interface:

        void makeUser();

        User getUser();

        boolean checkWin();

        void updateScore(int score);

        void changeLevel(int level);

        void makePlayer(User user);


        
#### Updated:
- public interface `Viewable`: 

    Reason for change: we moved card specific function into the GameScreen for better encapsulation. The `Viewable` interface now provides interaction only with the UserInterface class, including functions to advance to a particular `GameScreen`, get the `SplashScreen`, `WinScreen`, and so on. 

        void userScreen(String gameName, boolean darkMode);

        void initializeGame(String gameName, List<String> playerNames);

        String getTitle();

        GameController getController();

        void setWinScreen(String gameName, String playerName, int playerScore);

        double getWidth();

        double getHeight();

        void setSplash();

- `GameScreen` interface:
    
    Created this interface to provide interaction with the actual game(s) themselves (earlier this was a part of `Viewable` interface), such as setting up button actions, intiating a win sequence, getting the ID of a card, and so on. We found this information more useful to represent as we progressed through sprints.
    
        public void setUpButtons(Group gameScene);

        public Integer getCardID(Map<Integer, ImageView> map, ImageView card);

        public abstract Scene getScene(UserInterface ui);


        public void setCommonButtons(UserInterface ui, GameController gameController, String gameName); 

        public void endGame(UserInterface ui, GameController gameController, String gameName); 

        public void showMessage (Alert.AlertType type, String message);
    
- `CardSet` interface:

    A `CardSet` is a unique object we created to represent a collection of cards. This includes a sequence of cards, such as _K, Q, J, 10, 9_ which can move together as they are a valid set. This interface provides the opportunit to move, initiate, set the face up or down - applied throughout thise set of cards.
       
        public void toFront();

        public void winProtocol(int completeSetNumber);

        public void setLayoutX(double finalX);
        public void setLayoutY(Double finalY);

        private Integer getKey(Map<Integer, ImageView> map, ImageView v); 


        public ImageView getHeadCard(); 
 
 
 - `VBoxFactory` interface:
     
     The only open source asset we could find for Cards Against Humanity was the text for questions and answers, in .txt files. We use this `VBoxFactory` class to generate the cards for C.A.H ourselves, by setting a text `Label` and a colored `Rectangle` in a `VBox` together to act as our cards. This is interactions for these objects.
    
        public Integer getIndex();

        public Boolean getFace();

        public void setFace();
        public void setFace(Boolean value);

        public void resetCard(VboxFactory cardHolder, Label cardText, Rectangle imageViewLike, ImageView image); 

        public void resetCard();
        
- `PartyCards` interface:

    `PartyCards` is a unique object we created to represent a collection of cards that we made on our own and didn't use any outside image. PartyCards basically represents the cards that are being used in Cards Against Humanity and Truth and Dare. These cards don't have a front-image so we had to make them on our own and for that purpose we used this class with a `VboxFactory` class. It allowed us to make objects that represented playable cards and set its properties. 
    
        public VboxFactory getScene();

        public void changeFace();
        public  void changeFace(Boolean value);

        public void  setText(String prompt);

        public void clearCard();

     



 