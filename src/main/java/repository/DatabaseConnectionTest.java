package src.main.java.repository;

import src.main.java.enums.BookStatus;
import src.main.java.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DatabaseConnection dbConnection = new DatabaseConnection();

        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            System.out.println("Database connection established successfully.");

            List<Book> books = new ArrayList<>();
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long isbn = rs.getLong("isbn");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    int quantity = rs.getInt("quantity");
                    String statusStr = rs.getString("status");
                    BookStatus status = BookStatus.valueOf(statusStr);
                    books.add(new Book(isbn, title, author, quantity, status));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Book book : books) {
                System.out.println(book.getTitle());
            }

            dbConnection.closeConnection();
        } else {
            System.out.println("Failed to establish a database connection.");
        }
    }
}