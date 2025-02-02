package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String name;
    private Bank bank;
    private List<Deposit> deposits;

    public Branch(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
        this.deposits = new ArrayList<>();
        bank.addBranch(this);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Bank getBank() { return bank; }
    public List<Deposit> getDeposits() { return deposits; }

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public void removeDeposit(Deposit deposit) {
        deposits.remove(deposit);
    }

    public double getTotalDepositAmount() {
        double total = 0;
        for (Deposit deposit : deposits) {
            total += deposit.getAmount();
        }
        return total;
    }
}
