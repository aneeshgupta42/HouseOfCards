package ooga.View;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class ButtonFactory extends Button {
    private static final String STYLE_COLOR = "lightgray";
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final String FONT_FORMAT ="Calibri";
    private static final int DEFAULT_BUTTON_WIDTH = 150;
    private static final int DEFAULT_BUTTON_HEIGHT =35;
    private static final int DEFAULT_FONT_SIZE_SMALL =10;
    /**
     * Constructor method that lays out the basic view of a button in our parser
     * @param text specifies text in button that corresponds with a value in the properties file
     * @param height specifies the height of the button
     * @param width specifies the width of the button
     * @param fontSize specifies the font size of the text
     */
    public ButtonFactory(String text, int height, int width, int fontSize, double XPos, double YPos) {
        setFont(Font.font(FONT_FORMAT, fontSize));
        setText(text);
        setPrefHeight(height);//45
        setPrefWidth(width);//190
        setPosition(XPos,YPos);
        mouseUpdateListener();
    }
    /**
     * Constructor method that lays out the basic view of a button in our parser
     * @param text specifies text in button that corresponds with a value in the properties file
     */
    public ButtonFactory(String text, double XPos, double YPos) {
        setFont(Font.font(FONT_FORMAT, DEFAULT_FONT_SIZE_SMALL));
        setText(text);
        setPrefHeight(DEFAULT_BUTTON_HEIGHT);//45
        setPrefWidth(DEFAULT_BUTTON_WIDTH);//190
        setPosition(XPos,YPos);
        mouseUpdateListener();
    }
    /**
     * Constructor method that lays out the basic view of a button in our parser
     * @param text specifies text in button that corresponds with a value in the properties file
     * @param height specifies the height of the button
     * @param width specifies the width of the button
     * @param fontSize specifies the font size of the text
     */
    public ButtonFactory(String text, int height, int width, int fontSize) {
        setFont(Font.font(FONT_FORMAT, fontSize));
        setText(text);
        setPrefHeight(height);//45
        setPrefWidth(width);//190
        mouseUpdateListener();
    }

    /**
     * Another ButtonFactory constructor, but without specifying the font size
     * @param text specifies text in button that corresponds with a value in the properties file
     * @param height specifies the height of the button
     * @param width specifies the width of the button
     */
    public ButtonFactory(String text, int height, int width){
        this(text, height, width, DEFAULT_FONT_SIZE);
    }

    private void setPosition(double XPosition, double YPosition){
        setLayoutX(XPosition);
        setLayoutY(YPosition);
    }
    private void mouseUpdateListener() {
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
