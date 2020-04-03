# Use Cases
#### Team Number: 5
#### Team Name: Absolute Unit (Tests)
#### Members: Aneesh Gupta, Himanshu Jain, Tharun Raj

- Himanshu Jain 
    - Starting a game: The user chooses the game from the SplashScreen. The data file pertaining to that game is read in the Backend and the cards are presented on the Screen using the Model which gets the CardPack from backend and then passes that to the Frontend 
    - Adding a new simple card game: Add a data file if the cards in that game are different from normal cards. Add a button in the view for choosing that game and add the condition to read the right data file when the button is pressed. Make changes in the Controller if the logic is vastly different from the previous games implemented. 
    - Ending a game: The user clicks on the exit or restart buttons on top of the screen and these have onClick listeners attached to them which change the scene of the stage. 
    - Restarting the game: The user can restart the game from the gaming area as well as the exit screen. The buttons will have onClick listeners that change the view to the Splash Screen if they are clicked. 
    - More users can be added: New users can't be added once the game has started but this is done when the game starts. Multiplayer games will be directed to another window that allows them to choose the number of players that are going to be there. A new instance of the `User` class is created in the backend and this information is communicated to the Frontend as well. 
    - Making the Cards- the cards are loaded from the data file that contain information about everything that has to displayed on the cards. The data files change if the cards are different depending on the game. For eg: Cards against Humanity and Solitaire will be having different data files to read from
    - Deciding the winner of the game: The winner will be decided by the Game Engine. The Game Engine will be having the logic that the game has to use and this will be called in the `step` method of the main class so that the logic is checked after every round. 
    - Distribute cards to the different users: This will be done by the Game Engine as the cards and the user profiles will be made by the Backend and passed on to the Controller. The Controller will distribute cards using `addCard` method in the `User` class. The Controller would be able to remove cards using the same class as well. 

- Aneesh Gupta
    - Moving a card to a pile (Solitaire). The User can click on a card, and drag and drop it to a pile of cards on the screen.
    - Switching colors to dark mode. This will be achieved through a toggle switch on the front end.
    - Pause a game. The timer will pause, Game play checks for the timer status before allowing user to make a move/skip or forfeit their turn if timer is out.
    - Downloading current game.The user can save the current game status and configuration through a UI button. This can then trigger flows in control that write out the game type, score, cards configuration, and other specifics to XML files.
    - Shuffle the deck. The deck in the backend can be mantained as List of Card objects. We can then call `Random.shuffle()` on this List to shuffle the card objects.
    - Start multiple games. A button "Play New Game" allows user to choose and play a new game in either the same, or a new window. Creating a new window would be simply creating a new GameView component, that would instantiate a new a Control and a new Model
    - Display rules. A help icon can simply open up a snapshot/link to a page explaining rules of the game under play
    - Change language. Languages for the game text, buttons, etc. can be loaded in through properties files and the file can be changed for differing languages.

- Tharun Raj
    - The player can change the dificulty setting of the game while playing. This would be equivalent to changing the level of the game. When a button is clicked, a menu pops up that allows the user to change game specific settings such as number of suits played, timer etc.
    - Loading a previously saved game. The xml file used to save a previous game will be loaded when load game is clicked, and the data is read to set up the new game.
    - If an invalid file is chosen to load a new game, an error will be thrown, which will invoke a method in the `ErrorHandler` class which will throw a pop up that contains a custom error text.
    - When the user tries to make an invalid move during a game, the game engine which contains the logic of the game checks the validity of this move and prevents the move, usually the movement of certain card.
    - Changing background. There will be an UI option to change the background of the display of the game currently being played.
    - A player can click on exit game, which will take them to the splash screen to choose another game, enabling them to play multiple games repeatedly.
    - There will be a data file that holds the high score of all the different games of the platform. When a player successfully wins a game, if their score is higher than the current high score of the game, the high score data file will be edited to reflect the new high score.
    - If a timed game is played, when the timer runs out, a pop up will be shown that the end of the timer is reached. This pop up will also contain options to restart the game, or return to the splash screen to play another game.