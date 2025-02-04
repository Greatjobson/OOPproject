package org.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BankGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BankGUI() {
        frame = new JFrame("Bank Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Search Bar
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Find Deposit");
        searchButton.addActionListener(e -> searchDeposit());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Bank ID", "Depositor Name", "Amount", "Branch"}, 0);
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBankBtn = new JButton("Add Bank");
        JButton addBranchBtn = new JButton("Add Branch");
        JButton addDepositBtn = new JButton("Add Deposit");
        JButton editDepositBtn = new JButton("Replenish Account");
        JButton deleteDepositBtn = new JButton("Delete Deposit");

        addBankBtn.addActionListener(e -> addBank());
        addBranchBtn.addActionListener(e -> addBranch());
        addDepositBtn.addActionListener(e -> addDeposit());
        editDepositBtn.addActionListener(e -> editDeposit());
        deleteDepositBtn.addActionListener(e -> deleteDeposit());

        buttonPanel.add(addBankBtn);
        buttonPanel.add(addBranchBtn);
        buttonPanel.add(addDepositBtn);
        buttonPanel.add(editDepositBtn);
        buttonPanel.add(deleteDepositBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void show() {
        frame.setVisible(true);
        loadDeposits();
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

    private void addBank() {
        String name = JOptionPane.showInputDialog(frame, "Enter bank name:");
        if (name != null && !name.isEmpty()) {
            BankDB.saveBank(name);
            JOptionPane.showMessageDialog(frame, "Bank added: " + name);
        }
    }

    private void addBranch() {
        String name = JOptionPane.showInputDialog(frame, "Enter branch name:");
        int bankId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter bank ID:"));
        if (name != null && !name.isEmpty()) {
            BranchDB.saveBranch(name, bankId);
            JOptionPane.showMessageDialog(frame, "Branch added: " + name);
        }
    }

    private void addDeposit() {
        String depositor = JOptionPane.showInputDialog(frame, "Enter depositor name:");
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter deposit amount:"));
        int branchId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter branch ID:"));
        if (depositor != null && !depositor.isEmpty()) {
            DepositDB.saveDeposit(depositor, amount, branchId);
            JOptionPane.showMessageDialog(frame, "Deposit added: " + depositor);
            loadDeposits();
        }
    }

    private void editDeposit() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter deposit ID:"));
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter amount:"));
        DepositDB.replenishAccount(id, amount);
        JOptionPane.showMessageDialog(frame, "Deposit updated.");
        loadDeposits();
    }

    private void deleteDeposit() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter deposit ID:"));
        DepositDB.deleteDeposit(id);
        JOptionPane.showMessageDialog(frame, "Deposit deleted.");
        loadDeposits();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankGUI().show());
    }
}
