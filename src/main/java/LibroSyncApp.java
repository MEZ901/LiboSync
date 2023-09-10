package src.main.java;

import src.main.java.repository.Model;
import src.main.java.services.LibraryService;
import src.main.java.services.SearchService;

import java.util.*;

public class LibroSyncApp {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        SearchService searchService = new SearchService();

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
            }
        } while (choice != 0);
    }
}
