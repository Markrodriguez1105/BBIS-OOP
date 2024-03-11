package HouseholdRecord;

import static HouseholdRecord.DatabaseConnector.getConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddController implements Initializable {

    @FXML
    private TitledPane form_headInfo;
    @FXML
    private TextField household_id;
    @FXML
    private TextField first_name;
    @FXML
    private TextField middle_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField pos_in_the_fam;
    @FXML
    private TextField h_member;
    @FXML
    private TextField zone;
    @FXML
    private TextField monthly_income;
    @FXML
    private TextField suffix;
    @FXML
    private TextField civil_status;
    @FXML
    private DatePicker date_registered;
    @FXML
    private Button btnSave;
    @FXML
    private AnchorPane rootAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void handleButtonBack(ActionEvent event) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Would you really like to go back home?");

        // Add "Yes" and "No" buttons to the dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Show the dialog and wait for user input
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                // User clicked "Yes", go back to the main screen
                try {
                    // Load the new FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/main.fxml"));
                    Parent newRoot = loader.load();

                    // Get the stage from the event source
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Get the screen bounds
                    javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

                    // Set the scene size to fill the entire screen
                    Scene scene = new Scene(newRoot, bounds.getWidth(), bounds.getHeight());

                    // Create a fade transition
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), newRoot);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.play();

                    // Set the scene
                    stage.setScene(scene);

                    // Close the current window
                    stage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
private void handleButtonSave(ActionEvent event) {
    // Get data from input fields
    DataModel newData = getDataFromFields();
    // Save data to the database
    boolean saved = saveDataToDatabase(newData);
    // Show alert based on the result
    if (saved) {
        showAlert(AlertType.INFORMATION, "Success", "Data saved successfully.");
    } else {
        showAlert(AlertType.ERROR, "Error", "Failed to save data to the database.");
    }
}
 private Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Establish database connection
            String url = "jdbc:mysql://localhost:3306/bbis";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // Handle SQL exceptions (e.g., display error message)
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e; // Re-throw the exception to propagate it
        }
        return connection;
    }

   private boolean saveDataToDatabase(DataModel newData) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        // Establish connection
        connection = getConnection();
        // Prepare SQL statement
        String sql = "INSERT INTO household (household_id, first_name, middle_name, last_name, suffix, civil_status, pos_in_the_fam, h_member, zone, monthly_income, date_registered) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        // Set parameters
        preparedStatement.setInt(1, newData.getHousehold_id());
        preparedStatement.setString(2, newData.getFirst_name());
        preparedStatement.setString(3, newData.getMiddle_name());
        preparedStatement.setString(4, newData.getLast_name());
        preparedStatement.setString(5, newData.getSuffix());
        preparedStatement.setString(6, newData.getCivil_status());
        preparedStatement.setString(7, newData.getPos_in_the_fam());
        preparedStatement.setInt(8, newData.getH_member());
        preparedStatement.setInt(9, newData.getZone());
        preparedStatement.setInt(10, newData.getMonthly_income());
        preparedStatement.setDate(11, java.sql.Date.valueOf(newData.getDate_registered()));
        // Execute SQL statement
        int rowsAffected = preparedStatement.executeUpdate();
        // Check if data was successfully saved
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        // Close resources
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    private void showAlert(AlertType alertType, String title, String message) {
        // Show an alert dialog
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private DataModel getDataFromFields() {
        // Retrieve data from form fields and construct a DataModel object
        int householdId = Integer.parseInt(household_id.getText());
        String firstName = first_name.getText();
        String middleName = middle_name.getText();
        String lastName = last_name.getText();
        String suffix = this.suffix.getText();
        String civilStatus = civil_status.getText();
        String posInTheFam = pos_in_the_fam.getText();
        int hMember = Integer.parseInt(h_member.getText());
        int zoneValue = Integer.parseInt(zone.getText());
        int monthlyIncome = Integer.parseInt(monthly_income.getText());
        LocalDate dateRegistered = date_registered.getValue();

        // Construct and return a DataModel object
        return new DataModel(householdId, firstName, middleName, lastName, suffix, civilStatus, posInTheFam, hMember, zoneValue, monthlyIncome, dateRegistered);
    }

    @FXML
    private void valDateRegistered(ActionEvent event) {
    }

    @FXML
    private void valHousehold(ActionEvent event) {
    }

    @FXML
    private void valFirstName(ActionEvent event) {
    }

    @FXML
    private void valMiddleName(ActionEvent event) {
    }

    @FXML
    private void valLastName(ActionEvent event) {
    }

    @FXML
    private void valPositionInTheFamily(ActionEvent event) {
    }

    @FXML
    private void valHouseholdMember(ActionEvent event) {
    }

    @FXML
    private void valZone(ActionEvent event) {
    }

    @FXML
    private void valMonthlyIncome(ActionEvent event) {
    }

    @FXML
    private void valSuffix(ActionEvent event) {
    }

    @FXML
    private void valCivilStatus(ActionEvent event) {
    }

}
