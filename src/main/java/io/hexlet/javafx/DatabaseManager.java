package io.hexlet.javafx;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private static final Map<String, String> TABLE_NAME_AND_IMPORT_FILE_NAME = Map.of(
            "material_types", "Material_type_import.csv",
            "partner_products", "Partner_products_import.csv",
            "partners", "Partners_import.csv",
            "product_types", "Product_type_import.csv",
            "products", "Products_import.csv"
    );

    public DatabaseManager openConnection() {
        String url = getUrl();
        String username = System.getenv().getOrDefault("DATABASE_USERNAME", USERNAME);
        String password = System.getenv().getOrDefault("DATABASE_PASSWORD", PASSWORD);
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        createTables();
        importOperation();
    }

    private static void createTables() {
        try (var ps = connection.prepareStatement(loadSqlInit())) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadSqlInit() {
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("init.sql")) {
            try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void importOperation() {
        try {
            CopyManager copyManager = new CopyManager((BaseConnection) connection);
            TABLE_NAME_AND_IMPORT_FILE_NAME.forEach((tableName, fileName) -> {
                if (isTableEmpty(tableName)) {
                    try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(fileName)) {
                        String sql = String.format(
                                "COPY %s %s FROM STDIN WITH DELIMITER ';' HEADER;",
                                tableName,
                                getColumnNames(tableName)
                        );
                        copyManager.copyIn(sql, inputStream);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isTableEmpty(String tableName) {
        String sql = String.format("SELECT COUNT(*) FROM %s", tableName);
        try (var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getInt("COUNT") == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static String getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {
            var columns = connection.getMetaData().getColumns(
                    null,
                    null,
                    tableName,
                    null
            );
            while (columns.next()) {
                columnNames.add(columns.getString("COLUMN_NAME"));
            }
            columnNames.removeIf(column -> column.equals("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columnNames.stream().collect(Collectors.joining(",", "(", ")"));
    }

    private static String getUrl() {
        String address = System.getenv().getOrDefault("DATABASE_ADDRESS", ADDRESS);
        String name = System.getenv().getOrDefault("DATABASE_NAME", NAME);
        String host = System.getenv().getOrDefault("DATABASE_HOST", HOST);
        String port = System.getenv().getOrDefault("DATABASE_PORT", PORT);
        return String.format("%s://%s:%s/%s", address, host, port, name);
    }
}
