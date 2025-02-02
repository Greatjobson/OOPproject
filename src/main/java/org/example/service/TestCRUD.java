//package org.example.service;
//
//import org.example.model.BankDB;
//import org.example.model.BranchDB;
//import org.example.model.DepositDB;
//import java.util.List;
//
//public class TestCRUD {
//    public static void main(String[] args) {
//        // 🗑 Удаляем старые записи
//        DepositDB.clearDeposits();
//        BranchDB.clearBranches();
//        BankDB.clearBanks();
//
//        // ✅ 1. Добавляем банк
//        BankDB.saveBank("National Bank");
//
//        // ✅ 2. Получаем ID последнего банка
//        int bankId = BankDB.getLastBankId();
//        if (bankId == -1) {
//            System.out.println("❌ Ошибка: банк не был добавлен!");
//            return;
//        }
//
//        // ✅ 3. Добавляем филиалы
//        BranchDB.saveBranch("Downtown Branch", bankId);
//        BranchDB.saveBranch("Uptown Branch", bankId);
//
//        // ✅ 4. Получаем ID филиалов
//        int branchId1 = BranchDB.getLastBranchId();
//        int branchId2 = branchId1 - 1; // Предыдущий филиал
//
//        // ✅ 5. Добавляем депозиты
//        DepositDB.saveDeposit("Alice Johnson", 5000, branchId1);
//        DepositDB.saveDeposit("Bob Smith", 7000, branchId2);
//        DepositDB.saveDeposit("Charlie Brown", 8000, branchId1);
//
//        // ✅ 6. Пополняем депозит Alice на 3000
//        DepositDB.depositMoney(1, 3000);
//
//        // ✅ 7. Выводим всё
//        System.out.println("\n📋 Список банков:");
//        for (String bank : BankDB.getAllBanks()) {
//            System.out.println(bank);
//        }
//
//        System.out.println("\n🏢 Список филиалов:");
//        for (String branch : BranchDB.getAllBranches()) {
//            System.out.println(branch);
//        }
//
//        System.out.println("\n💰 Список вкладов:");
//        for (String deposit : DepositDB.getAllDeposits()) {
//            System.out.println(deposit);
//        }
//    }
//}
