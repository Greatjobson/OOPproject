package org.example.model;

public class Deposit {
    private String depositorName;
    private double amount;
    private Branch branch;


    public Deposit(String depositorName, double amount, Branch branch) {
        this.depositorName = depositorName;
        this.amount = amount;
        this.branch = branch;
        branch.addDeposit(this);
    }

    public String getDepositorName() { return depositorName; }
    public double getAmount() { return amount; }
    public Branch getBranch() { return branch; }

    public void replenishAccount(double amount) {
        if (amount > 0) {
            this.amount += amount;
        }
    }
}
