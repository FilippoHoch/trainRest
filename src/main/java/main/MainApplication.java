package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    /**
     * this is the first window that opens after you run the program
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderMain = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene sceneMain = new Scene(fxmlLoaderMain.load(), 600, 400);
        stage.setTitle("Main");
        stage.setScene(sceneMain);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}