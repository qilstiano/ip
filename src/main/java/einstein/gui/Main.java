package einstein.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import einstein.Einstein;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Einstein einstein = new Einstein("data/einstein.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setEinstein(einstein);  // inject the Einstein instance
            stage.show();

            stage.setMinHeight(220);
            stage.setMinWidth(417);
            // stage.setMaxWidth(417); // Add this if you didn't automatically resize elements
            fxmlLoader.<MainWindow>getController().setEinstein(einstein);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



