package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private ArrayList<Branch> branches;

    public Bank(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; } //сеттеры и геттеры
    public List<Branch> getBranches() { return branches; }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public Deposit findDepositByDepositor(String depositorName) {
        for (int i = 0; i < branches.size(); i++) {
            Branch branch = branches.get(i);
            for (int j = 0; j < branch.getDeposits().size(); j++) {
                Deposit deposit = branch.getDeposits().get(j);
                if (deposit.getDepositorName().equalsIgnoreCase(depositorName)) {
                    return deposit;
                }
            }
        }
        return null; 
    }
}
