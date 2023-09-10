package src.main.java;

import src.main.java.repository.Model;
import src.main.java.services.LibraryService;

import java.util.*;

public class LibroSyncApp {
    public static void main(String[] args) {
//        Model model = new Model("book");
        LibraryService libraryService = new LibraryService();
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
//                    Map<String, Object> dataToUpdate = new HashMap<>();
//                    dataToUpdate.put("title", "test updated");
//
//                    Map<String, Object> whereCriteria = new HashMap<>();
//                    whereCriteria.put("isbn", "3725301238253");
//
//                    String result = model.update(dataToUpdate, whereCriteria);
//                    System.out.println(result);
                    break;
                case 4:
//                    Map<String, Object> criteriaToDelete = new HashMap<>();
//                    criteriaToDelete.put("isbn", "3725301238253");
//
//                    String result = model.delete(criteriaToDelete);
//                    System.out.println(result);
                    break;
                case 5:
//                    Model model = new Model();
//                    Map<String, Object> criteria = new HashMap<>();
//                    criteria.put("isbn", "9783104012544");
//
//                    String tableName = "book";
//
//                    List<Map<String, Object>> results = model.find(tableName, criteria);
//
//                    System.out.println(results);
                    break;
            }
        } while (choice != 0);
    }
}
