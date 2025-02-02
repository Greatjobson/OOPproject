package org.example.service;

import org.example.model.Bank;
import org.example.model.Branch;
import org.example.model.Deposit;

public class Main {
    public static void main(String[] args) {
        // Создаём банк
        Bank bank = new Bank("HalykBank");

        // Создаём два филиала
        Branch branch1 = new Branch("Astana", bank);
        Branch branch2 = new Branch("Almaty", bank);

        // Создаём депозиты
        Deposit deposit1 = new Deposit("Beibarys Zhumagali", 5000, branch1);
        Deposit deposit2 = new Deposit("Jhon Smith", 7000, branch1);
        Deposit deposit3 = new Deposit("Charlie Chaplin", 8000, branch2);

        // Вывод информации о банке и филиалах
        System.out.println("Bank: " + bank.getName());
        for (Branch branch : bank.getBranches()) {
            System.out.println("Branch: " + branch.getName() + ", Total Deposits: " + branch.getTotalDepositAmount());
        }

        // Пополняем вклад
        System.out.println("\nReplenishing Alice's account by 3000...");
        deposit1.replenishAccount(3000);

        // Проверяем изменения
        System.out.println("Branch: " + branch1.getName() + ", Total Deposits: " + branch1.getTotalDepositAmount());

        // Поиск вклада по имени вкладчика
        System.out.println("\nSearching for Bob Smith's deposit...");
        Deposit foundDeposit = bank.findDepositByDepositor("Bob Smith");
        if (foundDeposit != null) {
            System.out.println("Found deposit: " + foundDeposit.getAmount() + " in branch " + foundDeposit.getBranch().getName());
        } else {
            System.out.println("Deposit not found.");
        }
    }
}
