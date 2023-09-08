package src.main.java.repository;

import java.sql.*;
import java.util.*;

public class Model {
    private String table;

    public Model(String table) {
        this.table = table;
    }

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> resultList = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection;
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.table);
                 ResultSet rs = stmt.executeQuery()) {

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

    public List<Map<String, Object>> find(Map<String, Object> data) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
                queryBuilder.append(this.table).append(" WHERE ");

                for (String key : data.keySet()) {
                    queryBuilder.append(key).append(" = ? AND ");
                }
                queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());

                try (PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
                    int parameterIndex = 1;
                    for (String key : data.keySet()) {
                        stmt.setObject(parameterIndex++, data.get(key));
                    }

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

    public String insert(Map<String, Object> data) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
                queryBuilder.append(this.table).append(" (");

                for (String key : data.keySet()) {
                    queryBuilder.append(key).append(", ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                queryBuilder.append(") VALUES (");

                for (int i = 0; i < data.size(); i++) {
                    queryBuilder.append("?, ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                queryBuilder.append(")");

                try (PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
                    int parameterIndex = 1;
                    for (String key : data.keySet()) {
                        stmt.setObject(parameterIndex++, data.get(key));
                    }
                    stmt.executeUpdate();
                    return "Record inserted successfully.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Failed to insert the record.";
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            return "Failed to establish a database connection.";
        }
    }

}
