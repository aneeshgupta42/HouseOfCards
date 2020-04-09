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

    /***
     * For the start screen
     * @return VBox with Bricks-qualities key
     */
    private VBox GetBricksKey(){
        Image regular = new Image(this.getClass().getClassLoader().getResourceAsStream("brick3.gif"));
        Label reg = new Label("Regular Brick - 1 Hit");
        reg.setGraphic(new ImageView(regular));

        Image hard = new Image(this.getClass().getClassLoader().getResourceAsStream("brick5.gif"));
        Label h = new Label("Hard Brick - 2+ Hits");
        h.setGraphic(new ImageView(hard));

        Image permanent = new Image(this.getClass().getClassLoader().getResourceAsStream("brick7.gif"));
        Label perm = new Label("Permanent Brick - Infinite hits");
        perm.setGraphic(new ImageView(permanent));

        Image power = new Image(this.getClass().getClassLoader().getResourceAsStream("brick10.gif"));
        Label pow = new Label("PowerUp Brick - 1 Hit, and gives power");
        pow.setGraphic(new ImageView(power));

        Image hydra = new Image(this.getClass().getClassLoader().getResourceAsStream("brick2.gif"));
        Label hyd = new Label("Hydra Brick - Splits into 2 Children (1 Hit each)");
        hyd.setGraphic(new ImageView(hydra));
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(reg, h, perm, pow, hyd);
        vbox.setLayoutX(90); vbox.setLayoutY(270);
        return vbox;
    }

    public Rectangle generateLogo(){
        Rectangle hypno = new Rectangle(50, 50);
        Image hypnoImage = new Image(this.getClass().getClassLoader().getResourceAsStream("hypno.gif"));
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
        Button cont = new Button("Solitaire");
        cont.setLayoutX(210); cont.setLayoutY(450);
//        cont.setOnAction(e -> mainView.advanceScene(mainView.getGameScene()));
        cont.setOnAction(e -> System.out.println("Start Solitaire"));

        Label Header = new Label("?House of Cards¿");
        Header.setFont(new Font("Garamond", 30));
        Header.setTextFill(Color.DARKCYAN);
        Header.setLayoutX(140); Header.setLayoutY(10);

        Rectangle logo = generateLogo();
        logo.setLayoutX(230); logo.setLayoutY(50);

        Label rules = new Label("GAME RULES\n" +
                "\t 1. Use arrow keys to move paddle to guide ball to pop bricks\n" +
                "\t 2. You get three lives per level\n" +
                "\t 3. There are different types of bricks in this game\n" +
                "\t 4. Use powerups such as Stretch, Swift, or Life+ \n"+
                "\t 4. In the middle of the level controls change (such as arrow keys)\n"+
                "\t\t Adapt to this change and finish the level!");

        rules.setLayoutX(40); rules.setLayoutY(120);
        rules.setFont(new Font("Garamond", 15));
        VBox vbox = GetBricksKey();
        Group startRoot = new Group();
        startRoot.getChildren().addAll(Header,logo, rules, cont, vbox);
        startScene = new Scene(startRoot, mainView.getWidth(), mainView.getHeight(), background);
    }

    public Scene getStartScene() {
        return startScene;
    }
}

