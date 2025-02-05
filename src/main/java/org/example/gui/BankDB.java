package org.example.gui;

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
            System.out.println("Bank added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getAllBanks() {
        List<String> banks = new ArrayList<>();
        String query = "SELECT name FROM banks";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                banks.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banks.toArray(new String[0]); // Конвертация списка в массив
    }

    public static int getBankIdByName(String bankName) {
        String query = "SELECT id FROM banks WHERE name = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bankName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если банк не найден
    }
}
