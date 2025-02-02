package org.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import org.example.model.*;

public class BankGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BankGUI() {
        frame = new JFrame("Bank System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // Панель поиска
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Find Deposit");
        searchButton.addActionListener(e -> searchDeposit());
        searchPanel.add(new JLabel("Depositor Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Таблица депозитов
        tableModel = new DefaultTableModel(new String[]{"ID", "Depositor", "Amount", "Branch"}, 0);
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Кнопки управления
        JPanel buttonPanel = new JPanel();
        JButton addDepositBtn = new JButton("+ Add Deposit");
        JButton replenishDepositBtn = new JButton("💰 Replenish Deposit");
        JButton showBranchDepositsBtn = new JButton("🏦 Show Branch Deposits");

        addDepositBtn.addActionListener(e -> addDeposit());
        replenishDepositBtn.addActionListener(e -> replenishDeposit());
        showBranchDepositsBtn.addActionListener(e -> showBranchDeposits());

        buttonPanel.add(addDepositBtn);
        buttonPanel.add(replenishDepositBtn);
        buttonPanel.add(showBranchDepositsBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadDeposits();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void loadDeposits() {
        tableModel.setRowCount(0);
        List<String[]> deposits = DepositDB.getAllDeposits();
        for (String[] deposit : deposits) {
            tableModel.addRow(deposit);
        }
    }

    private void searchDeposit() {
        String depositor = searchField.getText().trim();
        if (!depositor.isEmpty()) {
            String result = DepositDB.findDeposit(depositor);
            if (result != null) {
                JOptionPane.showMessageDialog(frame, "Deposit found: " + result);
            } else {
                JOptionPane.showMessageDialog(frame, "No deposit found.");
            }
        }
    }

    private void addDeposit() {
        String depositor = JOptionPane.showInputDialog(frame, "Enter depositor name:");
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter deposit amount:"));
        int branchId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter branch ID:"));

        if (!depositor.isEmpty()) {
            DepositDB.saveDeposit(depositor, amount, branchId);
            JOptionPane.showMessageDialog(frame, "Deposit added: " + depositor);
            loadDeposits();
        }
    }

    private void replenishDeposit() {
        int depositId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter deposit ID:"));
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter amount to add:"));

        DepositDB.replenishDeposit(depositId, amount);
        JOptionPane.showMessageDialog(frame, "Deposit replenished!");
        loadDeposits();
    }

    private void showBranchDeposits() {
        int branchId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter branch ID:"));
        List<String[]> deposits = DepositDB.getDepositsByBranch(branchId);
        tableModel.setRowCount(0);

        double total = 0;
        for (String[] deposit : deposits) {
            tableModel.addRow(deposit);
            total += Double.parseDouble(deposit[2]); // Колонка с суммой
        }

        JOptionPane.showMessageDialog(frame, "Total Deposits in Branch: " + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankGUI().show());
    }
}
