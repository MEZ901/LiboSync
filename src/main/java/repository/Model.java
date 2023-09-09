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

    public List<Map<String, Object>> insert(Map<String, Object> data) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder columnNamesBuilder = new StringBuilder();
                StringBuilder columnValuesBuilder = new StringBuilder();

                for (String key : data.keySet()) {
                    columnNamesBuilder.append(key).append(", ");
                }
                String columnNames = columnNamesBuilder.substring(0, columnNamesBuilder.length() - 2);

                for (String key : data.keySet()) {
                    columnValuesBuilder.append(data.get(key)).append(", ");
                }
                String columnValues = columnValuesBuilder.substring(0, columnValuesBuilder.length() - 2);

                try (CallableStatement callableStatement = conn.prepareCall("{CALL InsertAndReturn(?, ?, ?)}")) {
                    callableStatement.setString(1, this.table);
                    callableStatement.setString(2, columnNames);
                    callableStatement.setString(3, columnValues);

                    callableStatement.execute();

                    try (ResultSet rs = callableStatement.getResultSet();) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (rs.next()) {
                            Map<String, Object> row = new HashMap<>();
                            row.put("Message", "Record inserted successfully.");
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnName(i);
                                Object columnValue = rs.getObject(i);
                                row.put(columnName, columnValue);
                            }
                            resultList.add(row);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Map<String, Object> row = new HashMap<>();
                    row.put("Message", "Failed to insert the record.");
                    resultList.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Map<String, Object> row = new HashMap<>();
                row.put("Message", "Failed to insert the record.");
                resultList.add(row);
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            Map<String, Object> row = new HashMap<>();
            row.put("Message", "Failed to establish a database connection.");
            resultList.add(row);
        }

        return resultList;
    }


    public String update(Map<String, Object> data, Map<String, Object> whereData) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder queryBuilder = new StringBuilder("UPDATE ");
                queryBuilder.append(this.table).append(" SET ");

                for (String key : data.keySet()) {
                    queryBuilder.append(key).append(" = ?, ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

                queryBuilder.append(" WHERE ");

                for (String key : whereData.keySet()) {
                    queryBuilder.append(key).append(" = ? AND ");
                }
                queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());

                try (PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
                    int parameterIndex = 1;

                    for (String key : data.keySet()) {
                        stmt.setObject(parameterIndex++, data.get(key));
                    }

                    for (String key : whereData.keySet()) {
                        stmt.setObject(parameterIndex++, whereData.get(key));
                    }

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return "Record updated successfully.";
                    } else {
                        return "No matching records found for update.";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Failed to update the record.";
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            return "Failed to establish a database connection.";
        }
    }

    public String delete(Map<String, Object> whereData) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder queryBuilder = new StringBuilder("DELETE FROM ");
                queryBuilder.append(table).append(" WHERE ");

                for (String key : whereData.keySet()) {
                    queryBuilder.append(key).append(" = ? AND ");
                }
                queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());

                try (PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
                    int parameterIndex = 1;

                    for (String key : whereData.keySet()) {
                        stmt.setObject(parameterIndex++, whereData.get(key));
                    }

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return "Record deleted successfully.";
                    } else {
                        return "No matching records found for deletion.";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Failed to delete the record.";
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            return "Failed to establish a database connection.";
        }
    }
}
