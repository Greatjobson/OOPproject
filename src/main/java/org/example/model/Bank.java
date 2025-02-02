package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<Branch> branches;

    public Bank(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Branch> getBranches() { return branches; }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public Deposit findDepositByDepositor(String depositorName) {
        for (Branch branch : branches) {
            for (Deposit deposit : branch.getDeposits()) {
                if (deposit.getDepositorName().equalsIgnoreCase(depositorName)) {
                    return deposit;
                }
            }
        }
        return null;
    }
}
