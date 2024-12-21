package io.hexlet.javafx.controller;

import io.hexlet.javafx.Application;
import io.hexlet.javafx.model.Vendor;
import io.hexlet.javafx.repository.VendorRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VendorPanelWindowController {
    private final Vendor vendor;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField nameField;

    @FXML
    private MenuButton menuVendorTypeList;

    @FXML
    private TextField directorField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ratingField;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        nameField.setText(vendor.getName());
        menuVendorTypeList.setText(vendor.getType());
        ratingField.setText(vendor.getRating() == null ? null : vendor.getRating().toString());
        addressField.setText(vendor.getAddress());
        directorField.setText(vendor.getDirector());
        phoneField.setText(vendor.getPhone());
        emailField.setText(vendor.getEmail());
        var items = menuVendorTypeList.getItems();
        List<String> types = VendorRepository.getAllTypes();
        types.forEach((t) -> {
            MenuItem menuItem = new MenuItem(t);
            menuItem.setOnAction(actionEvent -> {
                menuVendorTypeList.setText(t);
            });
            items.add(menuItem);
        });
        saveButton.setOnMouseClicked(mouseEvent -> {
            saveButton.requestFocus();
            saveVendor();
        });
        root.setOnMouseClicked(mouseEvent -> {
            root.requestFocus();
            saveButton.setText("Save");
            saveButton.setTextFill(Color.BLACK);
        });
    }

    private void saveVendor() {
        try {
            prepareVendor();
            if (vendor.getId() == null) {
                VendorRepository.create(vendor);
            } else {
                VendorRepository.update(vendor);
            }
            saveButton.setText("Success!");
            saveButton.setTextFill(Color.GREEN);
            Application.refreshMainWindow();
        } catch (NumberFormatException ex) {
            saveButton.setText("Error!");
            saveButton.setTextFill(Color.RED);
            ratingField.clear();
            ratingField.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:rgba(255,0,0,0.6);");
            ratingField.requestFocus();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The rating should be a not negative number!");
            alert.setHeaderText("Rating field parse error.");
            alert.setTitle("Incorrect form data");
            alert.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            saveButton.setText("Error!");
            saveButton.setTextFill(Color.RED);
        }
    }

    private void prepareVendor() throws NumberFormatException {
        int rating = Integer.parseInt(ratingField.getText());
        if (rating < 0) {
            throw new NumberFormatException();
        }
        vendor.setName(nameField.getText());
        vendor.setType(menuVendorTypeList.getText());
        vendor.setDirector(directorField.getText());
        vendor.setEmail(emailField.getText());
        vendor.setPhone(phoneField.getText());
        vendor.setAddress(addressField.getText());
        vendor.setRating(rating);
    }
}
