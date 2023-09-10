package src.main.java.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRepository {
    public List<Map<String, Object>> searchBooks(String searchQuery) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                String sql = "SELECT b.isbn, b.title, a.name AS author, b.quantity, b.status " +
                        "FROM book b " +
                        "INNER JOIN author a ON b.author_id = a.id " +
                        "WHERE b.title LIKE ? OR a.name LIKE ?";

                String searchParam = "%" + searchQuery + "%";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, searchParam);
                    stmt.setString(2, searchParam);

                    try (ResultSet rs = stmt.executeQuery()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (rs.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnName(i);
                                Object columnValue = rs.getObject(i);
                                row.put(columnName, columnValue);
                            }
                            resultList.add(row);
                        }
                    }
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
