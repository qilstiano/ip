package einstein.gui;

import javafx.application.Application;

/**
 * A launcher class for the Einstein task management system GUI.
 * This class serves as a workaround for classpath issues when launching JavaFX applications.
 */
public class Launcher {

    /**
     * The main entry point for the Einstein GUI application.
     * This method launches the JavaFX application by calling the Main class.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
