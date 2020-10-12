package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class app extends Application {
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/dictionaryGui.fxml"));
        stage.setTitle("Dictionary app");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
