package io.hexlet.javafx.repository;

import io.hexlet.javafx.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class VendorProductsRepository {
    private static final Connection connection = DatabaseManager.connection;

    public static String getDiscountByPartner(String partnerName) {
        String sql = "SELECT * FROM partner_products WHERE partner_name = ?";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, partnerName);
            var resultSet = ps.executeQuery();
            int salesCount = 0;
            while (resultSet.next()) {
                salesCount += resultSet.getInt("quantity");
            }
            byte discount = 0;
            if (salesCount > 10000 && salesCount <= 50000) {
                discount = 5;
            } else if (salesCount > 50000 && salesCount <= 300000) {
                discount = 10;
            } else if (salesCount > 300000) {
                discount = 15;
            }
            return discount + "%";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
