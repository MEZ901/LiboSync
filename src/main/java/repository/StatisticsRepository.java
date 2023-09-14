package src.main.java.repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticsRepository {
    public Map<String, Object> statistics() {
        Map<String, Object> resultList = new HashMap<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                String sqlQuery = "SELECT " +
                        "(SELECT SUM(quantity) FROM book) + (SELECT SUM(CASE WHEN has_been_returned = false AND has_been_stolen = false THEN 1 ELSE 0 END) FROM reservation) + (SELECT SUM(quantity) FROM lost_book) AS total_books, " +
                        "(SELECT SUM(quantity) FROM book) AS available_books, " +
                        "(SELECT SUM(CASE WHEN has_been_returned = false AND has_been_stolen = false THEN 1 ELSE 0 END) FROM reservation) AS borrowed_books, " +
                        "(SELECT SUM(quantity) FROM lost_book) AS lost_books, " +
                        "(SELECT COUNT(*) FROM member) AS total_members, " +
                        "(SELECT COUNT(*) FROM author) AS total_authors";

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int totalBooks = resultSet.getInt("total_books");
                    int availableBooks = resultSet.getInt("available_books");
                    int borrowedBooks = resultSet.getInt("borrowed_books");
                    int lostBooks = resultSet.getInt("lost_books");
                    int totalMembers = resultSet.getInt("total_members");
                    int totalAuthors = resultSet.getInt("total_authors");

                    resultList.put("totalBooks", totalBooks);
                    resultList.put("availableBooks", availableBooks);
                    resultList.put("borrowedBooks", borrowedBooks);
                    resultList.put("lostBooks", lostBooks);
                    resultList.put("totalMembers", totalMembers);
                    resultList.put("totalAuthors", totalAuthors);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            System.out.println("Failed to establish a database connection.");
        }

        return resultList;
    }
}
