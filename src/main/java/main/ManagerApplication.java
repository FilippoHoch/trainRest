package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerApplication extends Application {

    /**
     * this is the first window that opens after you run the program
     *
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderMain = new FXMLLoader(MainApplication.class.getResource("editDatabase.fxml"));
        Scene sceneManager = new Scene(fxmlLoaderMain.load(), 198, 149);
        stage.setTitle("Modifica Database");
        stage.setScene(sceneManager);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}