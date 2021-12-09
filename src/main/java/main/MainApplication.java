package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    /**
     * this is the first window that opens after you run the program
     *
     * @return opens the first window
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderMain = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene sceneMain = new Scene(fxmlLoaderMain.load(), 600, 400);
        stage.setTitle("Main");
        stage.setScene(sceneMain);
        stage.setResizable(false);
        stage.show();
//        FXMLLoader fxmlLoaderSearch = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
//        Scene sceneSearch = new Scene(fxmlLoaderSearch.load(), 300, 329);
//        stage.setTitle("Search");
//        stage.setScene(sceneSearch);
//        stage.show();
//        FXMLLoader fxmlLoaderTicket = new FXMLLoader(MainApplication.class.getResource("ticket.fxml"));
//        Scene sceneTicket = new Scene(fxmlLoaderTicket.load(), 600, 205);
//        stage.setTitle("Ticket");
//        stage.setScene(sceneTicket);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}