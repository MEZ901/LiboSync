package src.main.java.services;

import src.main.java.repository.Model;
import src.main.java.utilities.ConsoleClear;

import java.util.*;

public class LibraryService {
    Model model = new Model("book");

    public void displayAllBooks() {
        List<Map<String, Object>> books = model.getAll();
        Scanner s = new Scanner(System.in);
        int choice;

//        ConsoleClear.clearConsole();

        System.out.println("\n+------------------------------------------------------------------------------+");
        System.out.println("|  Quantity |       ISBN      |         Title        | Author ID |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        for (Map<String, Object> row : books) {
            System.out.printf("|  %8s | %15s | %-20s | %9s | %-11s |\n",
                    row.get("quantity"), row.get("isbn"), row.get("title"),
                    row.get("author_id"), row.get("status"));
        }

        System.out.println("+------------------------------------------------------------------------------+\n");

        System.out.println("1. Back to menu");
        System.out.println("0. Exit");
        do {
            System.out.print("Enter your choice: ");
            choice = s.nextInt();

            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    break;
                default:
                    System.out.println("You can only choose between 1 or 0");
                    break;
            }
        } while(choice != 1);
    }

    public void addBook() {
        AuthorService author = new AuthorService();
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> bookData = new LinkedHashMap<>();
        int choice;

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Author Name: ");
        scanner.nextLine();
        String authorName = scanner.nextLine();

        int authorId = author.getAuthorIdOrCreateIfNotFound(authorName);

        bookData.put("isbn", "\"" + isbn + "\"");
        bookData.put("title", "\"" + title + "\"");
        bookData.put("quantity", quantity);
        bookData.put("author_id", authorId);
        bookData.put("status", "\"AVAILABLE\"");

        List<Map<String, Object>> result = model.insert(bookData);

        System.out.println("\n\u001B[32mThe book has been added successfully\u001B[0m");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|  Quantity |       ISBN      |         Title        |   Author  |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        System.out.printf("|  %8s | %15s | %-20s | %-9s | %-11s |\n",
                result.get(0).get("quantity"), result.get(0).get("isbn"), result.get(0).get("title"),
                authorName, result.get(0).get("status"));

        System.out.println("+------------------------------------------------------------------------------+\n");

        System.out.println("1. Back to menu");
        System.out.println("0. Exit");
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    break;
                default:
                    System.out.println("You can only choose between 1 or 0");
                    break;
            }
        } while(choice != 1);
    }
}
