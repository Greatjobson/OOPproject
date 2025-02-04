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

}
