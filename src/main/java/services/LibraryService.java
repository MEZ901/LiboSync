package src.main.java.services;

import src.main.java.repository.Model;

import java.util.*;

public class LibraryService {
    Model model = new Model("book");

    public void displayAllBooks() {
        List<Map<String, Object>> books = model.getAll();
        Scanner s = new Scanner(System.in);
        int choice;

        System.out.println("\n+------------------------------------------------------------------------------+");
        System.out.println("|       ISBN      |         Title        | Author ID |  Quantity |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        for (Map<String, Object> row : books) {
            System.out.printf("| %15s | %-20s | %9s |  %8s | %-11s |\n",
                    row.get("isbn"), row.get("title"), row.get("author_id"),
                    row.get("quantity"), row.get("status"));
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

        System.out.println("\n================= Add New Book =================");

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
        System.out.println("|       ISBN      |         Title        |   Author  |  Quantity |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        System.out.printf("| %15s | %-20s | %9s |  %8s | %-11s |\n",
                result.get(0).get("isbn"), result.get(0).get("title"), authorName,
                result.get(0).get("quantity"), result.get(0).get("status"));

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

    public void updateBook() {
        AuthorService author = new AuthorService();
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> whereCriteria = new HashMap<>();
        Map<String, Object> dataToUpdate = new HashMap<>();
        List<Map<String, Object>> book;

        System.out.println("\n================= Update Book =================");

        do {
            System.out.print("\nEnter the ISBN of the book: ");
            String isbn = scanner.nextLine();
            whereCriteria.put("isbn", isbn);

            book = model.find(whereCriteria);

            if (book.isEmpty()) {
                System.out.println("\n\u001B[31mThere's no book with this ISBN.\u001B[0m\n");
                System.out.println("1. Try again");
                System.out.println("2. Back to menu");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        return;
                }
            }
        } while (book.isEmpty());

        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|       ISBN      |         Title        | Author ID |  Quantity |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        System.out.printf("| %15s | %-20s | %9s |  %8s | %-11s |\n",
                book.get(0).get("isbn"), book.get(0).get("title"), book.get(0).get("author_id"),
                book.get(0).get("quantity"), book.get(0).get("status"));

        System.out.println("+------------------------------------------------------------------------------+\n");

        int choice;
        boolean isReadyToUpdate = false;

        System.out.println("1. Update title");
        System.out.println("2. Update author");
        System.out.println("3. Update quantity");
        System.out.println("4. Update all of the above");
        System.out.println("0. back to menu");

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter the new title: ");
                    String title = scanner.nextLine();
                    dataToUpdate.put("title", title);

                    isReadyToUpdate = true;
                    break;
                case 2:
                    System.out.print("\nEnter the new author name: ");
                    String authorName = scanner.nextLine();
                    int authorId = author.getAuthorIdOrCreateIfNotFound(authorName);
                    dataToUpdate.put("author_id", authorId);

                    isReadyToUpdate = true;
                    break;
                case 3:
                    System.out.print("\nEnter the new quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    dataToUpdate.put("quantity", quantity);

                    isReadyToUpdate = true;
                    break;
                case 4:
                    System.out.print("\nEnter the new title: ");
                    String bookTitle = scanner.nextLine();
                    dataToUpdate.put("title", bookTitle);

                    System.out.print("Enter the new author name: ");
                    String bookAuthorName = scanner.nextLine();
                    int bookAuthorId = author.getAuthorIdOrCreateIfNotFound(bookAuthorName);
                    dataToUpdate.put("author_id", bookAuthorId);

                    System.out.print("Enter the new quantity: ");
                    int bookQuantity = scanner.nextInt();
                    scanner.nextLine();
                    dataToUpdate.put("quantity", bookQuantity);

                    isReadyToUpdate = true;
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (isReadyToUpdate == false);

        model.update(dataToUpdate, whereCriteria);
        List<Map<String, Object>> bookAfterUpdate = model.find(whereCriteria);

        System.out.println("\n\u001B[32mThe book has been updated successfully\u001B[0m");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|       ISBN      |         Title        | Author ID |  Quantity |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        System.out.printf("| %15s | %-20s | %9s |  %8s | %-11s |\n",
                bookAfterUpdate.get(0).get("isbn"), bookAfterUpdate.get(0).get("title"), bookAfterUpdate.get(0).get("author_id"),
                bookAfterUpdate.get(0).get("quantity"), bookAfterUpdate.get(0).get("status"));

        System.out.println("+------------------------------------------------------------------------------+\n");

        System.out.println("1. Back to menu");
        System.out.println("0. Exit");

        int ch;

        do {
            System.out.print("Enter your choice: ");
            ch = scanner.nextInt();

            switch (ch) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    break;
                default:
                    System.out.println("You can only choose between 1 or 0");
                    break;
            }
        } while(ch != 1);
    }
}
