package src.main.java;

import src.main.java.enums.BookStatus;
import src.main.java.models.Book;

public class LibroSyncApp {
    public static void main(String[] args) {
        Book book = new Book(123421, "hhhhh", "ana", 5, BookStatus.AVAILABLE);
        book.setTitle("chihaja");
        System.out.println(book.getTitle());
    }
}
