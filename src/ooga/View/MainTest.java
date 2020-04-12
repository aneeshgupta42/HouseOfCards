package ooga.View;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import ooga.Main;
import ooga.View.ViewTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Show alpha version of TestFX driving GUI for testing.
 *
 * @author Robert C. Duvall
 */
public class MainTest extends ViewTest {
    // allow easy access within tests to elements of GUI
    private Labeled myLabel;
    private Button myButton;
    private ToggleButton myRadio;
    private CheckBox myCheck;
    private Label myTextField;
    private Slider mySlider;
    private ColorPicker myPicker;
    private ComboBox<String> myCombo;


    // run this method BEFORE EACH test to set up application in a fresh state
    @BeforeEach
    public void setUp () throws Exception {
        // start GUI new for each test
        launch(UserInterface.class);
//        start()
        // different ways to find elements in GUI
        // by ID
//        myLabel = lookup("#label").queryLabeled();
//        myPicker = lookup("#picker").queryAs(ColorPicker.class);
        // by path of IDs
//        myTextField = lookup("#pane #inputField").queryTextInputControl();
//         by being the only one of its kind
        myTextField = lookup(".label").queryAs(Label.class);
        myButton = lookup(".button").queryAs(Button.class);
        // by being the only one of its kind within another element
//        myCombo = lookup(".grid-pane .combo-box").queryComboBox();
        // by complete text in button
//        myButton = lookup("Solitaire").queryButton();
        // by individual buttons in group
//        myRadio = lookup("#radio-a").queryAs(ToggleButton.class);
//        myCheck = lookup("#check-b").queryAs(CheckBox.class);
        // clear text field, just in case
//        myTextField.clear();
    }


    // tests for different kinds of UI components
    @Test
    public void testTextFieldAction () {
        String expected = "ENTER test!";
        clickOn(myTextField).write(expected).write(KeyCode.ENTER.getChar());
        assertEquals(expected, myTextField.getText());
    }

    @Test
    public void testButtonAction () throws InterruptedException {
//        String expected = "CLICK test!";
//        clickOn(myTextField).write(expected);
        System.out.println("Getting here");
        clickOn(myButton);

    }

    @Test
    public void testSliderAction () {
        String expected = "50.0";
        setValue(mySlider, 50);
        assertEquals(expected, myLabel.getText());
    }

    @Test
    public void testColorPickerAction () {
        Color expected = Color.RED;
        setValue(myPicker, expected);
        assertEquals(expected.toString(), myLabel.getText());
    }

    @Test
    public void testComboBoxAction () {
        String expected = "b";
        select(myCombo, expected);
        assertEquals(expected, myLabel.getText());
    }

    @Test
    public void testRadioSelection () {
        String expected = "a";
        clickOn(myRadio);
        assertEquals(expected, myLabel.getText());
    }

    @Test
    public void testCheckBox () {
        List<String> expected = List.of("b : true", "b : false");
        for (String ans : expected) {
            clickOn(myCheck);
            assertEquals(ans, myLabel.getText());
        }
    }
}
