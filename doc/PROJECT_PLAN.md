# Project Plan
#### Team Number: 5
#### Team Name: Absolute Unit (Tests)
#### Members: Aneesh Gupta, Himanshu Jain, Tharun Raj

- Himanshu Jain 
    - Front-end and Model Components for the game 
    - Communicating with the team members 
    - The `User` class and the `UserHolder` class
    - The `Card` and the `CardFactory`
    - The `Visualizer` class for making the screen for the game 
    - Use Cases: Change the Game using Buttons, making the cards, making the users, keeping a list of all the users that are playing the game, keeping a pack of all the cards that the users hold, Communicating with the Model to remove and add cards based on the Card's value, reading in the data files and making the cards based on that 

- Aneesh Gupta 
    - Front-end and Game Engine
    - Game logic for the game that is being played. This will communicate directly with the backend for removing and adding cards so the APIs for communicating 
    - Building the scene for the game and animating the different cards that are present on the screen 
    - Using Model's API for getting information from the Frontend and detecting gestures 
    - Use Cases: Detect Gestures on the Buttons presented by the User, Decide the Winner of the Game, End the Game, Add an User, Distribute Cards to the different Users 

- Tharun Raj 
    - Abstract framework classes for the different Games and Game Engine 
    - Identifies the Similarities and Differences in the games we implement and builds the framework accordingly
    - `SplashScreen` class and the `endScreen` class 
    - Workflow of the Game 
    - Use Cases: End Screen displays when the game ends, game can be restarted from the end screen, Splash Screen is displayed when the application is launched, Users are able to choose different games from the SplashScreen, Adding a new game to the application 