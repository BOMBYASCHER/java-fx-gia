package io.hexlet.javafx.view;

import io.hexlet.javafx.Application;
import io.hexlet.javafx.controller.VendorPanelWindowController;
import io.hexlet.javafx.model.Vendor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VendorDetailView {
    public static void render(Vendor vendor) {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vendor-panel-view.fxml"));
        var controller = new VendorPanelWindowController(vendor);
        Scene scene;
        try {
            fxmlLoader.setController(controller);
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Vendor panel");
        stage.setScene(scene);
        stage.show();
    }
}