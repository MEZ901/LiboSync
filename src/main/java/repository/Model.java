package src.main.java.repository;

import java.sql.*;
import java.util.*;

public class Model {
    private final String table;

    public Model(String table) {
        this.table = table;
    }

    public List<Map<String, Object>> getAll(String joinCondition) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection;
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.table + " " + ((joinCondition != null) ? joinCondition : ""));
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

    public List<Map<String, Object>> find(Map<String, Object> data, String joinCondition) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
                queryBuilder.append(this.table).append(" ");

                if (joinCondition != null) {
                    queryBuilder.append(joinCondition).append(" ");
                }

                queryBuilder.append("WHERE ");

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

    public List<Map<String, Object>> insertAndReturn(Map<String, Object> data) {
        List<Map<String, Object>> resultList;
        Map<String, Object> procedureParams = new LinkedHashMap<>();
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

        procedureParams.put("table", this.table);
        procedureParams.put("columnNames", columnNames);
        procedureParams.put("columnValues", columnValues);

        resultList = callProcedure("InsertAndReturn", procedureParams);
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

    public List<Map<String, Object>> callProcedure(String procedureName, Map<String, Object> procedureParams) {
        List<Map<String, Object>> result = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try (Connection conn = connection) {
                StringBuilder callBuilder = new StringBuilder("{call ");
                callBuilder.append(procedureName).append("(");

                int paramCount = procedureParams.size();
                for (int i = 0; i < paramCount; i++) {
                    callBuilder.append("?");
                    if (i < paramCount - 1) {
                        callBuilder.append(", ");
                    }
                }
                callBuilder.append(")}");

                CallableStatement callableStatement = conn.prepareCall(callBuilder.toString());

                int parameterIndex = 1;
                for (String key : procedureParams.keySet()) {
                    callableStatement.setObject(parameterIndex++, procedureParams.get(key));
                }

                boolean hasResultSet = callableStatement.execute();

                if (hasResultSet) {
                    try (var rs = callableStatement.getResultSet()) {
                        var metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (rs.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnName(i);
                                Object columnValue = rs.getObject(i);
                                row.put(columnName, columnValue);
                            }
                            result.add(row);
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

        return result;
    }
}
