package src.main.java.services;

import src.main.java.repository.SearchRepository;
import src.main.java.utilities.DisplayTable;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchService {
    public void searchByTitleOrAuthor() {
        SearchRepository searchRepository = new SearchRepository();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n================= Search for Book =================\n");

        System.out.print("Enter the book title/author: ");
        String searchQuery = scanner.nextLine();

        List<Map<String, Object>> result = searchRepository.searchBooks(searchQuery);

        DisplayTable.displayBooks(result);
        DisplayTable.callToAction();
    }
}
