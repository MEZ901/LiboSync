package src.main.java.services;

import src.main.java.repository.Model;

import java.util.List;
import java.util.Map;

public class LibraryService {
    Model model = new Model("book");

    public void displayAllBooks() {
        List<Map<String, Object>> books = model.getAll();

        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|  Quantity |       ISBN      |         Title        | Author ID |    Status   |");
        System.out.println("+------------------------------------------------------------------------------+");

        for (Map<String, Object> row : books) {
            System.out.printf("|  %8s | %15s | %-20s | %9s | %-11s |\n",
                    row.get("quantity"), row.get("isbn"), row.get("title"),
                    row.get("author_id"), row.get("status"));
        }

        System.out.println("+------------------------------------------------------------------------------+");
    }
}
