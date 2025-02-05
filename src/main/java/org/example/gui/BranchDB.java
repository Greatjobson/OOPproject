package org.example.gui;

import org.example.database.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDB {
    public static void saveBranch(String name, int bankId) {
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO branches (name, bank_id) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setInt(2, bankId);
            stmt.executeUpdate();
            System.out.println("Branch added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getAllBranches() {
        List<String> branches = new ArrayList<>();
        String query = "SELECT name FROM branches";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                branches.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches.toArray(new String[0]); // Конвертация списка в массив
    }

    public static int getBranchIdByName(String branchName) {
        String query = "SELECT id FROM branches WHERE name = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branchName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если филиал не найден
    }
}
