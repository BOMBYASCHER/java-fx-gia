package io.hexlet.javafx.controller;

import io.hexlet.javafx.model.Vendor;
import io.hexlet.javafx.repository.VendorRepository;
import io.hexlet.javafx.view.VendorDetailView;
import io.hexlet.javafx.view.VendorView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class VendorsMainWindowController {
    @FXML
    private VBox vendorsVBox;

    @FXML
    private Button addVendorButton;

    @FXML
    private void initialize() {
        refreshVendorsList();
    }

    public void refreshVendorsList() {
        VendorView vendorView = new VendorView();
        var vendors = VendorRepository.getAll();
        vendorsVBox.getChildren().clear();
        vendors.forEach((child) -> vendorsVBox.getChildren().add(vendorView.getNode(child)));
        addVendorButton.setOnMouseClicked(mouseEvent -> VendorDetailView.render(new Vendor()));
    }
}