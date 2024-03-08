package reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    private final String DB_URL = "jdbc:mysql://localhost:3308/bbis";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    public Connection connect() throws SQLException {
        Connection connection = null;
        try {
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to let the calling code handle it
        }
        return connection;
    }

    public List<String> getDistinctReportTypes() {
        List<String> reportTypes = new ArrayList<>();

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT DISTINCT `report_type` FROM `report`")) {

            while (resultSet.next()) {
                reportTypes.add(resultSet.getString("report_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportTypes;
    }
    public int getMaxReportId() {
        int maxReportId = 0;
        try (Connection connection = connect()) {
            String query = "SELECT MAX(report_id) FROM report";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    maxReportId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxReportId;
    }
    public List<Resident> getResidents() {
        List<Resident> residents = new ArrayList<>();

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM `resident`")) {

            while (resultSet.next()) {
                Resident resident = new Resident();
                resident.setFirstName(resultSet.getString("first_name"));
                resident.setMiddleName(resultSet.getString("middle_name"));
                resident.setLastName(resultSet.getString("last_name"));
                resident.setSuffix(resultSet.getString("suffix"));
                
                resident.setEmail(resultSet.getString("email"));
                resident.setContactNumber(resultSet.getString("phone_num"));
                residents.add(resident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residents;
    }

    
}
