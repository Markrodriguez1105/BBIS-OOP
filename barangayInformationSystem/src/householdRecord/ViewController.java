package HouseholdRecord;

import householdRecord.MainController;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private Label showFamilyFirstName;

    @FXML
    private TitledPane form_headInfo;

    @FXML
    private DatePicker date_registered;

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
    private TextField tfSearch;

    @FXML
    private TableView<ResidentInfo> residentTableView;

    @FXML
    private AnchorPane rootAnchorPane;

    private ObservableList<ResidentInfo> originalData;

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/bbis";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateForm();
        // Make the date_registered DatePicker non-editable
        date_registered.setEditable(false);

        // Add event handler to show alert if the user attempts to edit the date
        date_registered.getEditor().setOnMouseClicked(event -> {
            showAlert("Sorry", "You are in view mode. Date cannot be edited.");
            // Consume the event to prevent further processing
            event.consume();
        });

        // Initialize the originalData list with data from the database
        originalData = residentList();
        residentTableView.setItems(originalData);

        // Check if a household ID is provided in the form, and filter the resident information accordingly
        if (!household_id.getText().isEmpty()) {
            int householdId = Integer.parseInt(household_id.getText());
            filterResidentInfoByHouseholdId(householdId);
        }

        // Initialize your controller
        if (rootAnchorPane != null) {
            // Perform operations involving rootAnchorPane
            // For example, accessing its properties or adding children
        } else {
            System.err.println("rootAnchorPane is null. Ensure it's properly initialized in the FXML file.");
        }
    }

    private void initializeTableView() {
        rootAnchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            residentTableView.setPrefWidth(newVal.doubleValue() - 20); // Adjust as needed
        });

        rootAnchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            residentTableView.setPrefHeight(newVal.doubleValue() - 100); // Adjust as needed
        });
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

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/householdRecord/householdRecord.fxml"));
                    loader.setController(this);
                    Parent root = loader.load();

                    // Get the controller of the main screen
                    loader.getController();

                    // Get the stage from the event source
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Get the screen bounds
                    javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
                    Parent newRoot = null;

                    // Set the scene size to fill the entire screen
                    Scene scene = new Scene(newRoot, bounds.getWidth(), bounds.getHeight());

                    // Pass any necessary data to the main controller before navigating back
                    // For example:
                    // mainController.setData(data);
                    // Create a fade transition
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), newRoot);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.play();

                    // Set the scene
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void handleFieldSearch(ActionEvent event) {
        String searchText = tfSearch.getText().trim();
        filterData(searchText);
    }

    private void filterData(String searchText) {
        ObservableList<ResidentInfo> filteredData = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            residentTableView.setItems(originalData);
            showAlert("Filtering", "Search cleared. Showing all records.");
            return;
        }

        String searchTextLower = searchText.toLowerCase();

        for (ResidentInfo data : originalData) {
            String fullName = data.getFirstName() + " " + data.getLastName(); // Concatenate first name and last name
            if (fullName != null) {
                String fullNameLower = fullName.toLowerCase();
                if (String.valueOf(data.getResidentId()).contains(searchTextLower)) {
                    filteredData.add(data);
                } else if (fullNameLower.contains(searchTextLower)) {
                    filteredData.add(data);
                }
            }
        }

        residentTableView.setItems(filteredData);
        showAlert("Filtering", "Search applied. Showing filtered records.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateForm() {
        DataModel select = MainController.selected;
        household_id.setText(String.valueOf(select.getHousehold_id()));
        first_name.setText(select.getFirst_name());
        middle_name.setText(select.getMiddle_name());
        last_name.setText(select.getLast_name());
        suffix.setText(select.getSuffix());
        civil_status.setText(select.getCivil_status());
        pos_in_the_fam.setText(select.getPos_in_the_fam());
        h_member.setText(String.valueOf(select.getH_member()));
        zone.setText(String.valueOf(select.getZone()));
        monthly_income.setText(String.valueOf(select.getMonthly_income()));

        // Set the last name of the head of the family as the family name in uppercase
        String familyName = select.getLast_name().toUpperCase();
        showFamilyFirstName.setText(familyName);

        // Convert Date to LocalDate
        LocalDate dateRegistered = select.getDate_registered(); // Assuming getDateRegistered() returns a LocalDate object
        date_registered.setValue(dateRegistered);
    }

    // Method to establish a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to retrieve resident information from the database
    public ObservableList<ResidentInfo> residentList() {
        ObservableList<ResidentInfo> residents = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM resident"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int residentId = resultSet.getInt("resident_id");
                int household_id = resultSet.getInt("household_id");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                String suffix = resultSet.getString("suffix");
                int age = resultSet.getInt("age");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String gender = resultSet.getString("gender");
                String civilStatus = resultSet.getString("civil_status");
                String relationToFamilyHead = resultSet.getString("relation_to_family_head");
                LocalDate dateRegistered = resultSet.getDate("date_registered").toLocalDate();

                // Create a new ResidentInfo object and add it to the list
                ResidentInfo resident = new ResidentInfo(residentId, household_id, firstName, middleName, lastName, suffix,
                        age, birthDate, gender, civilStatus, relationToFamilyHead, dateRegistered);
                residents.add(resident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
        }

        return residents;
    }

    public void populateForm(int householdId) {
        // Set household ID in the text field
        household_id.setText(String.valueOf(householdId));

        // Filter resident information based on household ID
        filterResidentInfoByHouseholdId(householdId);
    }

    private void filterResidentInfoByHouseholdId(int householdId) {
        ObservableList<ResidentInfo> filteredData = FXCollections.observableArrayList();

        // Fetch the household data for the given household ID
        DataModel householdData = getHouseholdData(householdId);

        // Check if household data is retrieved successfully
        if (householdData != null && householdData.getHousehold_id() == householdId) {
            // Adjust the title of the TitledPane
            form_headInfo.setText("Household Information - ID: " + householdId);

            // If household ID matches, filter the resident information
            for (ResidentInfo resident : originalData) {
                if (resident.getHouseholdId() == householdId) {
                    filteredData.add(resident);
                }
            }
            residentTableView.setItems(filteredData);
        } else {
            // If household data is not found or household ID doesn't match, clear the table
            residentTableView.setItems(null);
            showAlert("Validation Error", "The household ID in the form does not match any household record.");
        }
    }

    private DataModel getHouseholdData(int householdId) {
        // Fetch household data for the given household ID from the database
        DataModel householdData = null;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM household WHERE household_id = ?;")) {
            statement.setInt(1, MainController.householdId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Populate the householdData object
                    householdData = new DataModel(
                            resultSet.getInt("household_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("middle_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("suffix"),
                            resultSet.getString("civil_status"),
                            resultSet.getString("pos_in_the_fam"),
                            resultSet.getInt("h_member"),
                            resultSet.getInt("zone"),
                            resultSet.getInt("monthly_income"),
                            resultSet.getDate("date_registered").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
        }

        return householdData;
    }

    private ObservableList<DataModel> householdList() {
        ObservableList<DataModel> households = FXCollections.observableArrayList();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM household"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int householdId = resultSet.getInt("household_id");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                String suffix = resultSet.getString("suffix");
                String civilStatus = resultSet.getString("civil_status");
                String posInTheFam = resultSet.getString("pos_in_the_fam");
                int hMember = resultSet.getInt("h_member");
                int zone = resultSet.getInt("zone");
                int monthlyIncome = resultSet.getInt("monthly_income");
                LocalDate dateRegistered = resultSet.getDate("date_registered").toLocalDate();

                DataModel household = new DataModel(householdId, firstName, middleName, lastName, suffix, civilStatus, posInTheFam, hMember, zone, monthlyIncome, dateRegistered);
                households.add(household);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
        }

        return households;
    }

    @FXML
    private void handleFieldSearch(MouseEvent event) {
        String searchText = tfSearch.getText().trim();
        filterData(searchText);
    }

}
