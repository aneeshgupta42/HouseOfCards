package ooga.View.utils;

import javafx.scene.control.Alert;

import static javafx.application.Platform.exit;

public class GameException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public GameException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on an issue in our code.
     */
    public GameException(Alert.AlertType type, String message)
    {
        new Alert(type, message).showAndWait();
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public GameException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public GameException(Throwable cause) {
        super(cause);
    }
}
