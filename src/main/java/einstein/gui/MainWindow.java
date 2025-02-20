package einstein.gui;

import einstein.Einstein;
import einstein.ui.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Einstein einstein;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image einsteinImage = new Image(this.getClass().getResourceAsStream("/images/DaEinstein.png"));

    private Ui ui = new Ui(); // Create a Ui instance to access the welcome message

    /**
     * Initialise MainWindow.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showWelcomeMessage(); // Display the welcome message when the UI loads
    }

    /** Injects the Einstein instance */
    public void setEinstein(Einstein e) {
        einstein = e;
    }

    /**
     * Displays the welcome message in the dialog container.
     */
    private void showWelcomeMessage() {
        dialogContainer.getChildren().add(
                DialogBox.getEinsteinDialog(ui.showWelcome(), einsteinImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Einstein's reply
     * and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = einstein.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getEinsteinDialog(response, einsteinImage)
        );
        userInput.clear();
    }
}
