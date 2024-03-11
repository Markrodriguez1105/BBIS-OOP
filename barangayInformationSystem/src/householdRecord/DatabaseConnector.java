package HouseholdRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/bbis";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Method to connect to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to retrieve specific data from the "household" table
    public static ResultSet retrieveDataFromHouseholdTable() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String sql = "SELECT `household_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `civil_status`, `pos_in_the_fam`, `h_member`, `zone`, `monthly_income`, `date_registered` FROM `household`";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
}
