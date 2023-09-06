package src.main.java.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dbConnection.closeConnection();
        } else {
            System.out.println("Failed to establish a database connection.");
        }
    }
}