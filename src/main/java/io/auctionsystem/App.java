package io.auctionsystem;

import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.GsonHandling;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class App extends Application {

    private final DataSingleton data = DataSingleton.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        // Loading data in RAM
        GsonHandling.loadGson(data.getAuctionSystem());

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Login | Auction System");
        stage.setScene(scene);
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            System.out.println("Stage is closing");
            // Save file
            try {
                GsonHandling.saveGson(data.getAuctionSystem());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stage is closed\nData is saved");
        });
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchScene(Stage stage, String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXML/" + fxml));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}