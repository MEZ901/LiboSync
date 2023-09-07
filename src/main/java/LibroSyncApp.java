package src.main.java;

import java.util.Scanner;

public class LibroSyncApp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int choice;

        System.out.println("\n\n\t\t Welcome to LibroSync \t\t\n\n");
        do {
            System.out.println("1. Display all books");
            System.out.println("2. Add new book");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Search for books by title or author.");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = s.nextInt();
        } while (choice != 0);
    }
}
