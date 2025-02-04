module io.hexlet.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires static lombok;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens io.hexlet.javafx to javafx.fxml;
    exports io.hexlet.javafx;
    exports io.hexlet.javafx.view;
    opens io.hexlet.javafx.view to javafx.fxml;
    exports io.hexlet.javafx.model;
    opens io.hexlet.javafx.model to javafx.fxml;
    exports io.hexlet.javafx.controller;
    opens io.hexlet.javafx.controller to javafx.fxml;
    exports io.hexlet.javafx.repository;
    opens io.hexlet.javafx.repository to javafx.fxml;
}