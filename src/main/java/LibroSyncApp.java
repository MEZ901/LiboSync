package src.main.java;

import src.main.java.repository.Model;
import src.main.java.services.*;

import java.util.*;

public class LibroSyncApp {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        SearchService searchService = new SearchService();
        BorrowService borrowService = new BorrowService();
        LostService lostService = new LostService();
        ReportService reportService = new ReportService();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n+---------------------------------------------+");
            System.out.println("|          Library Management System          |");
            System.out.println("+---------------------------------------------+");
            System.out.println("|  Choice  |           Description            |");
            System.out.println("+---------------------------------------------+");
            System.out.println("|     1    | Display all books                |");
            System.out.println("|     2    | Add new book                     |");
            System.out.println("|     3    | Update book                      |");
            System.out.println("|     4    | Delete book                      |");
            System.out.println("|     5    | Search for books by title/author |");
            System.out.println("|     6    | Borrow book                      |");
            System.out.println("|     7    | Return book                      |");
            System.out.println("|     8    | Declare book as lost             |");
            System.out.println("|     9    | Statistics                       |");
            System.out.println("|     0    | Exit                             |");
            System.out.println("+---------------------------------------------+\n");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    libraryService.displayAllBooks();
                    break;
                case 2:
                    libraryService.addBook();
                    break;
                case 3:
                    libraryService.updateBook();
                    break;
                case 4:
                    libraryService.deleteBook();
                    break;
                case 5:
                    searchService.searchByTitleOrAuthor();
                    break;
                case 6:
                    borrowService.borrowBook();
                    break;
                case 7:
                    borrowService.returnBook();
                    break;
                case 8:
                    lostService.declareAsLost();
                    break;
                case 9:
                    reportService.statistics();
                    break;
                case 0:
                    System.out.println("Bye.");
                    break;
                default:
                    System.out.println("\n\u001B[31mInvalid choice.\u001B[0m");
                    break;
            }
        } while (choice != 0);
    }
}
