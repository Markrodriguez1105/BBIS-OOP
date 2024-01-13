/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseConnector {

    private final String DB_URL = "jdbc:mysql://localhost:3306/bbis";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
    
    Connection connection;
    
    
    public ObservableList<ReportsData> getReportsDataList() {
        ObservableList<ReportsData> dataList = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT * FROM report"; // Replace with your actual table name
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReportsData report = new ReportsData(
                            resultSet.getString("report_type"),
                            resultSet.getString("name"),
                            resultSet.getString("phone_num"),
                            resultSet.getString("email"),
                            resultSet.getString("reason")
                    );
                    dataList.add(report);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return dataList;
    }
    public ResultSet getFromDatabase(String query, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement(query);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for better debugging
            return null; // Handle the error accordingly
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing result set: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing prepared statement: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public ResultSet executeQuery(String query) throws SQLException{
        Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try{
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch(Exception e){
            System.out.println(e);
        }
        
        return resultSet;
    }
}

