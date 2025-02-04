package org.example.service;

import org.example.model.Bank;
import org.example.model.Branch;
import org.example.model.Deposit;

public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank("HalykBank");


        Branch branch1 = new Branch("Astana", bank);
        Branch branch2 = new Branch("Almaty", bank);


        Deposit deposit1 = new Deposit("Beibarys Zhumagali", 5000, branch1);
        Deposit deposit2 = new Deposit("Jhon Smith", 7000, branch1);
        Deposit deposit3 = new Deposit("Charlie Chaplin", 8000, branch2);
        Deposit deposit4 = new Deposit("Chico Letti", 11000, branch2);


        System.out.println("Bank: " + bank.getName());
        for (Branch branch : bank.getBranches()) {
            System.out.println("Branch: " + branch.getName() + ", Total Deposits: " + branch.getTotalDepositAmount());
        }

        // Пополнение
        deposit1.replenishAccount(3000);

        branch1.setName("Karaganda");
        System.out.println(branch1.getName());

        System.out.println("Branch: " + branch1.getName() + ", Total Deposits: " + branch1.getTotalDepositAmount());

        // Поиск вклада по имени
        Deposit foundDeposit = bank.findDepositByDepositor("Chico Letti");
        if (foundDeposit != null) {
            System.out.println("Found deposit: " + foundDeposit.getAmount() + " in branch " + foundDeposit.getBranch().getName());
        } else {
            System.out.println("Deposit not found.");
        }
    }
}
