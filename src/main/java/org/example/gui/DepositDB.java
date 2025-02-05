package org.example.gui;

import org.example.database.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepositDB {
    public static void saveDeposit(String depositorName, double amount, int branchId) {
        if (!doesBranchExist(branchId)) {
            System.out.println("Error: Branch with ID " + branchId + " does not exist!");
            return;
        }
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO deposits (depositor_name, amount, branch_id) VALUES (?, ?, ?)")) {
            stmt.setString(1, depositorName);
            stmt.setDouble(2, amount);
            stmt.setInt(3, branchId);
            stmt.executeUpdate();
            System.out.println("Deposit added: " + depositorName + " - " + amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String findDeposit(String depositorName) {
        String query = "SELECT d.id AS depositor_id, d.amount, " +
                "b.name AS branch_name, bk.name AS bank_name " +
                "FROM deposits d " +
                "JOIN branches b ON d.branch_id = b.id " +
                "JOIN banks bk ON b.bank_id = bk.id " +
                "WHERE d.depositor_name = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, depositorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Depositor ID: " + rs.getInt("depositor_id") +
                        ", Amount: " + rs.getDouble("amount") +
                        ", Branch: " + rs.getString("branch_name") +
                        ", Bank: " + rs.getString("bank_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если депозит не найден
    }



    public static List<String[]> getAllDepositsWithDetails() {
        List<String[]> deposits = new ArrayList<>();
        String query = "SELECT bk.id AS bank_id, bk.name AS bank_name, " +
                "b.id AS branch_id, b.name AS branch_name, " +
                "d.id AS depositor_id, d.depositor_name, d.amount " +
                "FROM deposits d " +
                "JOIN branches b ON d.branch_id = b.id " +
                "JOIN banks bk ON b.bank_id = bk.id";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                deposits.add(new String[]{
                        String.valueOf(rs.getInt("bank_id")),
                        rs.getString("bank_name"),
                        String.valueOf(rs.getInt("branch_id")),
                        rs.getString("branch_name"),
                        String.valueOf(rs.getInt("depositor_id")),
                        rs.getString("depositor_name"),
                        String.valueOf(rs.getDouble("amount"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }



    public static boolean doesBranchExist(int branchId) {
        String query = "SELECT COUNT(*) FROM branches WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void replenishAccount(int depositId, double amount) {
        String query = "UPDATE deposits SET amount = amount + ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, depositId);
            stmt.executeUpdate();
            System.out.println("Account has been replenished by --> " + amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDeposit(int depositId) {
        String query = "DELETE FROM deposits WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, depositId);
            stmt.executeUpdate();
            System.out.println("Deposit deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
