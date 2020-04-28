# DESIGN.md
### Aneesh Gupta, Himanshu Jain, Tharun Raj
#### ag468, hkj4, tm272

## Team Roles and Responsibilities

 * Team Member #1: Aneesh Gupta
    - **Games Worked on**: _Solitaire, Memory, Concentration_
    - **Responsibilities**: Frontend framework for classes, moving between scenes, visual representation for the games I worked on. Also worked towards creating the front update logic that communicates with the backend through the controller (For eg. placing on card on another, selecting cards, etc.). Also wrote tests for both frontend and backend classes.
        
 * Team Member #2: Tharun Raj
    - **Games worked on**: Solitaire, Memory, Humanity, Truth or Dare.
    - **Reponsibilities**: Implemented the backend Framework for the model and the controller, making cards, processing gameplay functionalities i.e. implementing the update protocols for games, updating the scores, designing data structures to fit our design requirements.
        

 * Team Member #3: Himanshu Jain
     - **Games worked on**: Solitaire, Humanity, Truth or Dare
     - **Responsibilities**: Implemented the frontend screens for the above games. I also made tests for the Frontend as well as the backend. I worked on creating a pipeline between the backend and the frontnend. I worked on the abstract classes in the frontend and the Factory classes for different javafx components that were used. I also worked on a json file for game Humanity. 



## Design goals

#### What Features are Easy to Add

* Adding new games is easy:

    * To add a new game, we would need to do the following: 
        1. Add the name of the game to the Enum holding the game types. This button's value should correspond to a datafile you create for this game
        2. Within this datafile `newgame.json`, you can specify which driver and screen class you wish to use. 
        3. You can also extend `GameScreen` and `GameDriver` to make custom game drivers and UIs.
        4. No conditional within the code needs to update -- everything runs from the jSON file.
        


* Adding in new parameters to read in from jSON source files is easy to add.
    * The only work required for this is adding the specific value required to the json file under a specific key string, and then getting said parameter value from the map that is passed on. The program supports an unlimited number of paramaters from the json.

* Social profiles
    * High scores were easy to add since we used a json file to keep track of all the high scores for each game, and since we already had the functionaly to laod and process json files, this feature was an easy add. We can extend the existing infrastructure to function to work with more data about the user, such as their social profile - win stats, buddies, top games, etc.

* Saving and writing out game config
    * Capabilities to write out the game's current state using jSON/XML files is easy to add. A jSON reader-writer class already exists, and we would just call the writer when the game is exited.



## High-level Design

#### Core Classes
- The `GameScreen` class:
    - This class provides an abstract platform for displaying the View component for all the different games. The output of this class is a Scene object that is then putup on our primary stage when the game is intialized. Different games have significantly varying front end representations - we have `SolitaireScreen`, `MemoryScreen`, `CAHScreen`, etc. All of them share some basic characteristics, like a Restart, and Exit button, as well as a `getScene()` function and so on. These commonalities are housed in the `GameScreen` parent class to make extension easier. It also has some Factory classes to make components like a button and VBox so that we are able to add extra functionality. 
    
- The `GameDriver` class:
    - This class essentially runs the backend of each game. An instance of a game-specific driver class that extend GameDriver is instantiated in the GameController. Its main responsibilities are to implement gameplay functionalities, such as running the update protocol when the front end calls for it, updating the scores appropriately and initializing the cards and players required in a game.

- The `GameController` class:
    - Serves as the link between the front-end and back-end classes. Also responsible to process data from json files which includes instantiating the appropriate classes for a game based on the data. Implements writing the high scores as well.
    - This class takes in immutable interfaces representing the class it has to connect and interact with, to ensure good data privacy and structure encapsulation.

## Assumptions that Affect the Design

#### Features Affected by Assumptions
-    We expect the game to be tap and pass so we have used that assumption in implementing the different games we have. For Humanity, we are displaying the cards of only 1 at once so we believe that the players would be able to choose their cards one by one. This also allowed us to save space on the screen because displaying all the cards at once would have led to problems. 
-    We also believe that in playing Humanity, the group would have a judge who will choose the winner. The implementation of humanity is such that there are no cards for the judge and the judge is supposed to monitor the game as well as choose the question card. 
-    For Memory, we expect it to be single player game so we don't expect more than 1 player to join the game as we have implemented Concentration for that. 
-    Solitaire is single player as that is the nature of the game. Truth or Dare and Humanity work with a single player but they are not supposed to be played like that. 
-    For adding a new game, we expect the developer to have the required datafiles present and have built out corresponding driver and screen classes if required. These would be required if the new game is really different in its functionality. 


## New Features HowTo

#### Easy to Add Features
* Adding new games is easy:

    * To add a new game, we would need to do the following: 
        1. Create a button for the required game
        2. This button's value should correspond to a datafile you create for this game
        3. Within this datafile `newgame.json`, you can specify which driver and screen class you wish to use. 
        4. You can also extend `GameScreen` and `GameDriver` to make custom game drivers and UIs.
        5. No conditional within the code needs to update -- everything runs from the jSON file.

* Adding in new parameters to read in from jSON source files is easy to add.

    * The only work required for this is adding the specific value required to the json file under a specific key string, and then getting said parameter value from the map that is passed on. The program supports an unlimited number of paramaters from the json.

* Social profiles

    * High scores were easy to add since we used a json file to keep track of all the high scores for each game, and since we already had the functionaly to laod and process json files, this feature was an easy add. We can extend the existing infrastructure to function to work with more data about the user, such as their social profile - win stats, buddies, top games, etc.

* Saving and writing out game config

    * Capabilities to write out the game's current state using jSON/XML files is easy to add. A jSON reader-writer class already exists, and we would just call the writer when the game is exited.


#### Other Features not yet Done
Some features we wanted to implement given mroe time:
#### Engine
- _Artificial players_. Allow single players to play "against the computer" by making smart players that can make intelligent moves but still be beatable.


#### Data
- Feature to safe entire player profiles to a database so that player preferences for each game could be loaded when a user logs in.


#### Player
- A multiplayer experience over a web server so that multiple devices could be used to play games.
- Allow the player to edit the Game Screen's layout using his own datafiles. This will involve using a user-provided properties file to write out elements in our UI when prompted.




