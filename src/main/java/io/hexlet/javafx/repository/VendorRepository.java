package io.hexlet.javafx.repository;

import io.hexlet.javafx.DatabaseManager;
import io.hexlet.javafx.model.Vendor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VendorRepository {
    private static final Connection connection = DatabaseManager.connection;

    public static List<Vendor> getAll() {
        String sql = "SELECT * FROM partners";
        try (var ps = connection.prepareStatement(sql)) {
            var resultSet = ps.executeQuery();
            var vendors = new ArrayList<Vendor>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String type_partner = resultSet.getString("type_partner");
                String name_partner = resultSet.getString("name_partner");
                String director = resultSet.getString("director");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String inn = resultSet.getString("inn");
                Integer rating = resultSet.getInt("rating");
                Vendor vendor = new Vendor(
                        id,
                        type_partner,
                        name_partner,
                        director,
                        email,
                        phone,
                        address,
                        inn,
                        rating
                );
                vendors.add(vendor);
            }
            return vendors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllTypes() {
        String sql = "SELECT DISTINCT type_partner FROM partners";
        try (var ps = connection.prepareStatement(sql)) {
            var resultSet = ps.executeQuery();
            var vendorTypes = new ArrayList<String>();
            while (resultSet.next()) {
                String typePartner = resultSet.getString("type_partner");
                vendorTypes.add(typePartner);
            }
            return vendorTypes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Vendor vendorDataUpdate) {
        String sql = """
                UPDATE partners
                SET (type_partner, name_partner, director, email, phone, address, inn, rating)
                = (?, ?, ?, ?, ?, ?, ?, ?)
                WHERE id=?""";
        try (var ps = connection.prepareStatement(sql)) {
            System.out.println(vendorDataUpdate);
            ps.setString(1, vendorDataUpdate.getType());
            ps.setString(2, vendorDataUpdate.getName());
            ps.setString(3, vendorDataUpdate.getDirector());
            ps.setString(4, vendorDataUpdate.getEmail());
            ps.setString(5, vendorDataUpdate.getPhone());
            ps.setString(6, vendorDataUpdate.getAddress());
            ps.setString(7, vendorDataUpdate.getInn());
            ps.setInt(8, vendorDataUpdate.getRating());
            ps.setLong(9, vendorDataUpdate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create(Vendor vendor) {
        String sql = """
                INSERT INTO partners
                (type_partner, name_partner, director, email, phone, address, inn, rating)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, vendor.getType());
            ps.setString(2, vendor.getName());
            ps.setString(3, vendor.getDirector());
            ps.setString(4, vendor.getEmail());
            ps.setString(5, vendor.getPhone());
            ps.setString(6, vendor.getAddress());
            ps.setString(7, vendor.getInn());
            ps.setInt(8, vendor.getRating());
            ps.executeUpdate();
            var ids = ps.getGeneratedKeys();
            if (ids.next()) {
                vendor.setId(ids.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vendor findById(Long id) {
        var sql = "SELECT * FROM readers WHERE id = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String type_partner = resultSet.getString("type_partner");
                String name_partner = resultSet.getString("name_partner");
                String director = resultSet.getString("director");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String inn = resultSet.getString("inn");
                Integer rating = resultSet.getInt("rating");
                return new Vendor(
                        id,
                        type_partner,
                        name_partner,
                        director,
                        email,
                        phone,
                        address,
                        inn,
                        rating
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
