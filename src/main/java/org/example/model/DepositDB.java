package org.example.model;

import org.example.database.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepositDB {

    public static void saveDeposit(String depositorName, double amount, int branchId) {
        if (!doesBranchExist(branchId)) {
            System.out.println("❌ Error: Branch with ID " + branchId + " does not exist!");
            return;
        }
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO deposits (depositor_name, amount, branch_id) VALUES (?, ?, ?)")) {
            stmt.setString(1, depositorName);
            stmt.setDouble(2, amount);
            stmt.setInt(3, branchId);
            stmt.executeUpdate();
            System.out.println("✅ Deposit added: " + depositorName + " - " + amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getDepositsByBranch(int branchId) {
        List<String[]> deposits = new ArrayList<>();
        String query = "SELECT id, depositor_name, amount FROM deposits WHERE branch_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                deposits.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("depositor_name"),
                        String.valueOf(rs.getDouble("amount"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }


    public static void replenishDeposit(int depositId, double amount) {
        String query = "UPDATE deposits SET amount = amount + ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, depositId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Депозит ID " + depositId + " пополнен на " + amount);
            } else {
                System.out.println("❌ Ошибка: Депозит с ID " + depositId + " не найден!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<String[]> getAllDeposits() {
        List<String[]> deposits = new ArrayList<>();
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM deposits")) {
            while (rs.next()) {
                deposits.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("depositor_name"),
                        String.valueOf(rs.getDouble("amount")),
                        String.valueOf(rs.getInt("branch_id"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }

    public static String findDeposit(String depositorName) {
        String query = "SELECT * FROM deposits WHERE depositor_name = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, depositorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "ID: " + rs.getInt("id") + ", Amount: " + rs.getDouble("amount") + ", Branch ID: " + rs.getInt("branch_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static void updateDeposit(int depositId, double amount) {
        String query = "UPDATE deposits SET amount = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, depositId);
            stmt.executeUpdate();
            System.out.println("✅ Deposit updated.");
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
            System.out.println("🗑 Deposit deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
