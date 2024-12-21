package io.hexlet.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private DatabaseManager databaseManager;
    private static Stage stage;

    public static void refreshMainWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vendors-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Application.stage.setTitle("Vendors");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    @Override
    public void start(Stage stage) {
        Application.stage = stage;
        databaseManager = new DatabaseManager();
        databaseManager.openConnection();
        refreshMainWindow();
    }

    @Override
    public void stop() throws Exception {
        databaseManager.closeConnection();
        System.out.println("DATABASE CONNECTION WAS STOP!");
        super.stop();
    }
}