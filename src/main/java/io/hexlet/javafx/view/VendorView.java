package io.hexlet.javafx.view;

import io.hexlet.javafx.model.Vendor;
import io.hexlet.javafx.repository.VendorProductsRepository;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VendorView {
    private final Border defaultBorder = Border.stroke(Color.BLACK);
    private final Border mouseInBorder = Border.stroke(Color.LIGHTGRAY);

    public BorderPane getNode(Vendor vendor) {
        BorderPane infoCard = new BorderPane();
        VBox vBox = new VBox();
        var vBoxChildren = vBox.getChildren();
        vBoxChildren.add(new Label(vendor.getType() + " | " + vendor.getName()));
        vBoxChildren.add(new Label(vendor.getDirector()));
        vBoxChildren.add(new Label(vendor.getPhone()));
        vBoxChildren.add(new Label(vendor.getRating().toString()));
        infoCard.setLeft(vBox);
        BorderPane discountBorderPane = new BorderPane();
        discountBorderPane.setPadding(new Insets(0, 20, 0, 0));
        Label discount = new Label(getDiscount(vendor));
        discountBorderPane.setTop(discount);
        infoCard.setRight(discountBorderPane);
        infoCard.setPadding(new Insets(10));
        infoCard.setBorder(defaultBorder);
        infoCard.setOnMouseClicked(mouseEvent -> VendorDetailView.render(vendor));
        infoCard.setOnMouseEntered(mouseDragEvent -> infoCard.setBorder(mouseInBorder));
        infoCard.setOnMouseExited(mouseDragEvent -> infoCard.setBorder(defaultBorder));
        BorderPane pane = new BorderPane(infoCard);
        pane.setPadding(new Insets(10));
        return pane;
    }

    private static String getDiscount(Vendor vendor) {
        return VendorProductsRepository.getDiscountByPartner(vendor.getName());
    }
}
