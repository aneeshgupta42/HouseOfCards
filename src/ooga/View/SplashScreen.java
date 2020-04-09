package ooga.View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SplashScreen {
    private Paint background = Color.WHITESMOKE;
    private Scene startScene;
    private String logo = "cardDecks/poker/aces.png";



    public Rectangle generateLogo(){
        Rectangle hypno = new Rectangle(200, 120);
        Image hypnoImage = new Image(this.getClass().getClassLoader().getResourceAsStream(logo));
        ImagePattern hypnoImagePattern = new ImagePattern(hypnoImage);
        hypno.setFill(hypnoImagePattern);
        return hypno;
    }

    /***
     * set up the start screen
     * and return it as a scene
     * @param mainView
     */
    public SplashScreen(UserInterface mainView){
        Group startRoot = new Group();
        startScene = new Scene(startRoot, mainView.getWidth(), mainView.getHeight(), background);
        double width = mainView.getWidth();
        double height = mainView.getHeight();
        Button cont = new Button("Solitaire");
        cont.setLayoutX(width/2-70); cont.setLayoutY(height/2+100);
//        cont.setOnAction(e -> mainView.advanceScene(mainView.getGameScene()));
        cont.setOnAction(e ->
        {
            System.out.println("Start Solitaire");
            mainView.initializeGame("Solitaire");
        });
        startRoot.getChildren().addAll(cont);

        Label Header = new Label("?House of Cards¿");
        Header.setFont(new Font("Garamond", 30));
        Header.setTextFill(Color.DARKCYAN);
        startRoot.getChildren().addAll(Header);
        Header.setLayoutX(width/2-100); Header.setLayoutY(10);

        Rectangle logo = generateLogo();
        logo.setLayoutX(width/2-logo.getWidth()/2); logo.setLayoutY(100);
        startRoot.getChildren().addAll(logo);


        Label rules = new Label("GAME RULES\n" +
                "\t 1. Lorem ipsum dolor sit amet, consectetur adipiscing elit\n" +
                "\t 2. , sed do eiusmod tempor incididunt utl\n" +
                "\t 3. labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "\t 4.  quis nostrud exercitation ullamco laboris nisi ut al \n"+
                "\t");

        rules.setLayoutX(width/2-100); rules.setLayoutY(300);
        rules.setFont(new Font("Garamond", 15));
        startRoot.getChildren().addAll(rules);
    }

    public Scene getStartScene() {
        return startScene;
    }
}

