package src.main.java;

import src.main.java.repository.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibroSyncApp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int choice;

        do {
            System.out.println("+--------------------------------------------+");
            System.out.println("|          Library Management System         |");
            System.out.println("+--------------------------------------------+");
            System.out.println("|  Choice  |           Description           |");
            System.out.println("+--------------------------------------------+");
            System.out.println("|     1    | Display all books               |");
            System.out.println("|     2    | Add new book                    |");
            System.out.println("|     3    | Update book                     |");
            System.out.println("|     4    | Delete book                     |");
            System.out.println("|     5    | Search for books by title/author|");
            System.out.println("|     0    | Exit                            |");
            System.out.println("+--------------------------------------------+");
            System.out.print("Enter your choice: ");

            choice = s.nextInt();

            switch (choice){
                case 1:
                    break;
                case 2:
                    Model model = new Model();
                    Map<String, Object> criteria = new HashMap<>();
                    criteria.put("isbn", "9783104012544");

                    String tableName = "book";

                    List<Map<String, Object>> results = model.find(tableName, criteria);

                    System.out.println(results);
                    break;
            }
        } while (choice != 0);
    }
}
