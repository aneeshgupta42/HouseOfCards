# Design Plan
#### Team Number: 5
#### Team Name: Absolute Unit (Tests)
#### Members: Aneesh Gupta, Himanshu Jain, Tharun Raj

[Wireframe for the Program](https://www.figma.com/file/rWYArM9a9ZIE6ug4nEWgLp/Absolute-Unit-Tests?node-id=0%3A1)
### Introduction
- The aim of this project is to develop a software for a gaming suite that enables users to play a variety of games within the program. The genre of our project will be card games. 
- The primary goal of this project is to use Object Oriented Programming principles to build the program that implements a substantial amount of abstraction which would make it feasible to add extensions to the program, as well as any other game in the genre. 
- The idea is to study and identify the similar charecteristics and objects within card games, and build the program around those findings to produce a robust piece of software that enables further additions of similar games with the given APIs a straightfoward task.
- We also aim to consider games that vary significantly enough so that this program will be able to support building and running a good variety of card games.
- We believe that building an interesting game and then adding functionalities would make the overall gaming experience much more exciting and interesting. 

### Overview
- The overall design pattern follows a Model-View-Controller pattern. We can, at a high level, visualize the game engine to be the Model, the front-end and game-view is the View, and the Controller is the communication interface between these two components.
- The games are going to be of 2 genres: Simple Card Games (Solitaire, Blackjack), Party Games (Cards Against Humanity etc)
- Data Files are going to contain data about the type of the card and the images that are going to be on the cards. These data files will be similar for most of the card games except the games that would be similar to the game Cards against Humanity as the cards would be different for that particular game. 
- We will use properties files for in game texts to avoid hardcoding strings.

- Model: 
    - It would have a class named `UserHolder` to maintain a list of all the users that are playing a game. 
        - Methods: makeUser, getUser
    - The class `User` will be holding all the information for a particular user. This includes the user's current state and the cards the user has 
        - Methods: addCardforUser, removeCardforUser, changeState
        - Instance Variables: currentState, cardsUserHas 
    - A model would have a class named `Card` which would be creating the different cards of a pack. Instances of this class would be created for playing the game, using a `CardFactory` class. 
        - Methods: setNumber, SetImage, setValue, getNumber, getImage, getValue 
        - Instance Variables: cardNumber (Integer), cardImage(Imageview), cardValue(Integer)
        - cardNumber is used for the Simple Card games and determines the number on the card. cardImage sets the image on the card and cardValue determines what the card is going to do in the game. This is mainly used for the Party Games like Cards Against Humanity. 
    - A class named `CardPack` will contain all the cards that have been made by the `CardFactory`
        - Method: addCard, getCardPack, removeCard
        - Instance Variables: cardPack (ArrayList of `Card`)
    - A class named `CardFactory` would be made to read everything from the data files and make the cards accordingly. The data files would change depending on the game that is being played but we don't expect much to change except when the game being played is something similar to Cards Against Humanity. This class will make a new `Card` instance and then add it to the instance of `CardPack` using `addCard` method. 
    - This module will contain the game engine which would include the gameplay logic, winning logic and score tracking functionalities.
        - Methods: `checkWin`, `updateScore`, `changeLevel`, `makePlayer`(calls a method in the model to store new player information)
        - These methods would be overridden for each specific game we implement thus providing a framework to add games into the platform.

- View:
    - This module will handle the front-end elements of the game. We will be using javaFX to design this module
    - There would be a factory class for buttons that would be present on the view since we don't want any one else to have access to it. 
    - There would a `splashScreen` class that will be presented when the user logs in. 
        - Method: createSplashScreen, returnSplashScreen, createButtons, createText
        - Instance Variable: SplashScreen, Buttons 
    - This SplashScreen will have 5 buttons to allow the user to choose the game that he/she wants to play 
    - Each button will then call the `generateGame()` method in Control, to configure the `GameDriver` in the backend accordingly.
    - Once the game startups, we will have varioud UI elements, such as a score display, menu panel, help button, settings (dark mode, sound, voice, font size, etc.)
    - Users interact with the game by clicking on cards, and this can call methods in the control to simulate the requried UI updates (based on the Game's specific logic)
        - Methods: `updateCard`, `updateScore`, `getNewCard`, `shuffleDeck`
- Controller: 
    - This is where the communication between the backend and the frontend takes place. Most elements of the game screen, such as cards, have both a front end, and a backend components, such as a Card, and a CardView. Interactions, movements, updates, etc. are transmitted through the Controller.
    - This will take in Immutable interfaces representations of the frontend and backend classes, and provide the backend with controls to make updates to and control the frontend. We can also have the Backend send us a 'Command' object, which the control then parses and generates an action.
    - Parsing user inputs, whether in the form of interactions through UI, or string input, and generate corresponding `Game` classes.
        - Methods: `parseCommand`, `generateGame`,


### Design Details
- Model:
    - Framework: There will be an abstract class named `GameDriver` to add new games feasibly. The respective methods would possibly include `checkWin`, `updateScore`, `changeLevel`, `makePlayer` which would be overridden specific to the game being implemented. The gameflow would then depend on this logic.
        - Another important element of the framework would include the card packs used for the game. The view will call `generateGame` in the controller, and this function will call the specific `GameDriver` class to initialize the cardPacks used using `CardFactory` and start the game.
        - `checkWin` will determine the end of a game if a player satisfies the winning conditions of a specific game.
- View:
    - Essentially being the front-end component of the game, the View module would detect UI interations from players and invoke appropriate methods using the controller's external APIs to handle the inputs.
- Controller:
    - The view will call methods from the controller using its external APIs to invoke methods from the backend for the appropriate user input.
    - One prospective design plan is that the Controller itself isn't an API, but basically the pipeline that connects and calls the frontend and backend APIs. We plan on figuring out the exact methods and classes within the Control component as we build out the frontend and backend.

### Example Games
- Solitaire - It would be a simple card game that we plan to implement. This
- Blackjack
- Crazy Eights
- Snap
- Uno - This is a significant variation as this has a separate card pack, and playing rules
- Cards Against Humanity - This is an inherently multiplayer game, involves question cards, answer cards, and a judge that chooses the best answer.


### Design Considerations
- One design consideration that we discussed at length was the design model we were going to use. We all agreed that the Model-View-Controller implementation would be the best way to structure this project since that will give us a clear distinction between the different parts of the project to work on. This would also help in building the common abstract framework of the different games since we could approach problems encountered while building the framework in a methodical and systematic manner.