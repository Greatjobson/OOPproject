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

}
