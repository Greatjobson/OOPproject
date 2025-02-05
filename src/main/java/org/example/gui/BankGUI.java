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
        frame.setSize(700, 400);
        frame.setLayout(new BorderLayout());

        // Search Bar
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Find Deposit");
        searchButton.addActionListener(e -> searchDeposit());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Table with corrected columns
        tableModel = new DefaultTableModel(new String[]{"Bank ID","Bank Name", "Branch ID", "Branch Name","Depositor ID","Depositor Name", "Amount",}, 0);

        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);


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
        List<String[]> deposits = DepositDB.getAllDepositsWithDetails();
        if (deposits.isEmpty()) {
            tableModel.addRow(new String[]{"NULL", "NULL", "NULL", "NULL", "NULL", "NULL"});
        } else {
            for (String[] deposit : deposits) {
                tableModel.addRow(deposit);
            }
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
            loadDeposits();
        }
    }

    private void addBranch() {
        String[] banks = BankDB.getAllBanks();
        if (banks.length == 0) {
            JOptionPane.showMessageDialog(frame, "No banks available!");
            return;
        }
        String bankName = (String) JOptionPane.showInputDialog(frame, "Select bank:", "Choose Bank",
                JOptionPane.QUESTION_MESSAGE, null, banks, banks[0]);

        int bankId = BankDB.getBankIdByName(bankName);

        String name = JOptionPane.showInputDialog(frame, "Enter branch name:");
        if (name != null && !name.isEmpty()) {
            BranchDB.saveBranch(name, bankId);
            JOptionPane.showMessageDialog(frame, "Branch added: " + name);
            loadDeposits();
        }
    }

    private void addDeposit() {
        String[] branches = BranchDB.getAllBranches();
        if (branches.length == 0) {
            JOptionPane.showMessageDialog(frame, "No branches available!");
            return;
        }
        String branchName = (String) JOptionPane.showInputDialog(frame, "Select branch:", "Choose Branch",
                JOptionPane.QUESTION_MESSAGE, null, branches, branches[0]);

        int branchId = BranchDB.getBranchIdByName(branchName);

        if (branchId == -1) {
            JOptionPane.showMessageDialog(frame, "Branch not found!");
            return;
        }

        String depositor = JOptionPane.showInputDialog(frame, "Enter depositor name:");
        double amount;
        try {
            amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter deposit amount:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount!");
            return;
        }

        if (depositor != null && !depositor.isEmpty()) {
            DepositDB.saveDeposit(depositor, amount, branchId);
            JOptionPane.showMessageDialog(frame, "Deposit added: " + depositor);
            loadDeposits();
        }
    }

    private void editDeposit() {
        int id;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter deposit ID:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid deposit ID!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter amount:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount!");
            return;
        }

        DepositDB.replenishAccount(id, amount);
        JOptionPane.showMessageDialog(frame, "Deposit updated.");
        loadDeposits();
    }

    private void deleteDeposit() {
        int id;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter deposit ID:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid deposit ID!");
            return;
        }

        DepositDB.deleteDeposit(id);
        JOptionPane.showMessageDialog(frame, "Deposit deleted.");
        loadDeposits();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankGUI().show());
    }
}
