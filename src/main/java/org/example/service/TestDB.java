
package org.example.service;

import org.example.database.DBManager;

public class TestDB {
    public static void main(String[] args) {
        if (DBManager.getConnection() != null) {
            System.out.println("✅ Подключение к базе данных успешно!");
        } else {
            System.out.println("❌ Ошибка подключения к базе данных.");
        }
    }
}
