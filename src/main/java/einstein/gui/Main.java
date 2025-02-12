package einstein.gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String DEFAULT_FILE_PATH =  "data/einstein.txt";

    public Main(String filePath) {
    }

    public Main() {
        this(DEFAULT_FILE_PATH);
    }
        @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}
