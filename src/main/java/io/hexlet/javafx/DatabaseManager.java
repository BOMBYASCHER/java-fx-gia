package io.hexlet.javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DatabaseManager {
    public static Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/java-fx-gia-db";
    private static final String ADDRESS = "jdbc:postgresql";
    private static final String NAME = "java-fx-gia-db";
    private static final String HOST = "localhost";
    private static final String USERNAME = "java-fx-gia-user";
    private static final String PASSWORD = "javaFxUserPassword";
    private static final String PORT = "5432";

    public void openConnection() {
        String url = getUrl();
        String username = System.getenv().getOrDefault("DATABASE_USERNAME", USERNAME);
        String password = System.getenv().getOrDefault("DATABASE_PASSWORD", PASSWORD);
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadDatabaseSchema() {
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("h2.sql")) {
            try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getUrl() {
        String address = System.getenv().getOrDefault("DATABASE_ADDRESS", ADDRESS);
        String name = System.getenv().getOrDefault("DATABASE_NAME", NAME);
        String host = System.getenv().getOrDefault("DATABASE_HOST", HOST);
        String port = System.getenv().getOrDefault("DATABASE_PORT", PORT);
        return String.format("%s://%s:%s/%s", address, host, port, name);
    }
}
