package org.example.model;

import org.example.database.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankDB {
    public static void saveBank(String name) {
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO banks (name) VALUES (?)")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("✅ Bank added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllBanks() {
        List<String> banks = new ArrayList<>();
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM banks")) {
            while (rs.next()) {
                banks.add("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banks;
    }
}
