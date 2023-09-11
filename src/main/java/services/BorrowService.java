package src.main.java.services;

import src.main.java.LibroSyncApp;
import src.main.java.repository.Model;

import java.util.Map;
import java.util.Scanner;

public class BorrowService {
    Model model = new Model("reservation");
    LibraryService libraryService = new LibraryService();

    public void borrowBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ISBN of the book: ");
        String isbn = scanner.nextLine();


    }

    public void returnBook() {}
}
