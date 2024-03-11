package HouseholdRecord;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.main;

public class MainController implements Initializable {

    @FXML
    private TableView<DataModel> recordTableView;
    @FXML
    private TableColumn<DataModel, Integer> household_id;
    @FXML
    private TableColumn<DataModel, String> full_name;
    @FXML
    private TableColumn<DataModel, LocalDate> date_registered;
    @FXML
    private TableColumn<DataModel, String> pos_in_the_fam;
    @FXML
    private TableColumn<DataModel, Integer> h_member;
    @FXML
    private TableColumn<DataModel, Integer> zone;
    @FXML
    private TableColumn<DataModel, Integer> monthly_income;
    @FXML
    private TextField tfSearch;
    private ObservableList<DataModel> originalData;
    private TableColumn<DataModel, String> first_name;
    private TableColumn<DataModel, String> middle_name;
    private TableColumn<DataModel, String> last_name;
    private TableColumn<DataModel, String> suffix;
    @FXML
    private TableColumn<DataModel, String> civil_status;

    // Map to store whether the context menu has been shown for each item
    private Map<DataModel, Boolean> contextMenuShownMap = new HashMap<>();

    private static Stage uniqueStage; // Static reference to the unique stage
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private TableColumn<ResidentInfo, Boolean> delete_status;
    @FXML
    private Button btnViewRecord;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox<Integer> searchBarFilter;

    private ComboBoxZone comboBoxZone;

    private HBox refreshTable;

    private void openUniqueStage() {
        try {
            if (uniqueStage == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add.fxml"));
                Parent root = loader.load();
                AddController addController = loader.getController();

                uniqueStage = new Stage();
                uniqueStage.setScene(new Scene(root));
                uniqueStage.setOnCloseRequest(event -> uniqueStage = null); // Reset uniqueStage when closed
                uniqueStage.show();
            } else {
                uniqueStage.toFront(); // Bring the existing stage to the front
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error opening add form: " + e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load data from the database
        originalData = loadDataFromDatabase();

        // Set the data to the TableView
        recordTableView.setItems(originalData);

        // Show record (if needed, implement this method)
        showRecord();

        // Initialize columns and bind them to DataModel fields
        household_id.setCellValueFactory(new PropertyValueFactory<>("household_id"));
        full_name.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFirst_name() + " "
                + cellData.getValue().getMiddle_name() + " "
                + cellData.getValue().getLast_name() + " "
                + cellData.getValue().getSuffix()));
        pos_in_the_fam.setCellValueFactory(new PropertyValueFactory<>("pos_in_the_fam"));
        h_member.setCellValueFactory(new PropertyValueFactory<>("h_member"));
        zone.setCellValueFactory(new PropertyValueFactory<>("zone"));

        // Format monthly_income column
        monthly_income.setCellValueFactory(new PropertyValueFactory<>("monthly_income"));
        monthly_income.setCellFactory(tc -> new TableCell<DataModel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("₱%,d", item)); // Format with comma and peso sign
                }
            }
        });

        date_registered.setCellValueFactory(new PropertyValueFactory<>("date_registered"));
        civil_status.setCellValueFactory(new PropertyValueFactory<>("civil_status"));

        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem viewMenuItem = new MenuItem("View Record");
        MenuItem addMenuItem = new MenuItem("Add");
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem deleteStatusMenuItem = new MenuItem("Delete Status");

        // Set actions for view, add, and delete menu items
        viewMenuItem.setOnAction(e -> {
            DataModel selectedData = recordTableView.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                viewRecord(selectedData);
            }
        });

        // Instantiate ComboBoxZone
        comboBoxZone = new ComboBoxZone();

        // Set ComboBox items
        searchBarFilter.setItems(comboBoxZone.getZones());
        addMenuItem.setOnAction(e -> openUniqueStage());
        deleteMenuItem.setOnAction(e -> handleDeleteAction());
        deleteStatusMenuItem.setOnAction(e -> handleDeleteStatusAction());

        // Add menu items to the context menu
        contextMenu.getItems().addAll(viewMenuItem, addMenuItem, deleteMenuItem, deleteStatusMenuItem);

        // Set the context menu to the TableView
        recordTableView.setContextMenu(contextMenu);

        // Initialize the search listener
        initializeSearchListener();

        applyCssAlignment();

    }

    private void handleDeleteStatusAction() {
        DataModel selectedData = recordTableView.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            // Implement logic to handle delete status action
            // For example, you can update the delete status in the database
            updateDeleteStatus(selectedData);
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

    private ObservableList<DataModel> loadDataFromDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // Assuming you have TableColumn objects for each column
        household_id.setCellValueFactory(new PropertyValueFactory<>("household_id"));
        full_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        civil_status.setCellValueFactory(new PropertyValueFactory<>("civil_status"));
        pos_in_the_fam.setCellValueFactory(new PropertyValueFactory<>("pos_in_the_fam"));
        h_member.setCellValueFactory(new PropertyValueFactory<>("h_member"));
        zone.setCellValueFactory(new PropertyValueFactory<>("zone"));
        monthly_income.setCellValueFactory(new PropertyValueFactory<>("monthly_income"));
        date_registered.setCellValueFactory(new PropertyValueFactory<>("date_registered"));

        ObservableList<DataModel> data = FXCollections.observableArrayList();

        try {
            // Establish database connection
            connection = getConnection();

            // Prepare SQL query
            String sql = "SELECT household_id, first_name, middle_name, last_name, suffix, civil_status, pos_in_the_fam, h_member, zone, monthly_income, date_registered FROM household";
            preparedStatement = connection.prepareStatement(sql);

            // Execute query and get result set
            resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                // Retrieve data from result set
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

                // Create a new DataModel object and add it to the list
                DataModel dataModel = new DataModel(householdId, firstName, middleName, lastName, suffix, civilStatus, posInTheFam, hMember, zone, monthlyIncome, dateRegistered);
                data.add(dataModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions (e.g., display error message)
            showErrorAlert("Error occurred while loading data from database: " + e.getMessage());
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exceptions (e.g., display error message)
                showErrorAlert("Error occurred while closing database resources: " + e.getMessage());
            }
        }

        return data;
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        openUniqueStage();
    }

    @FXML
    private void handleButtonView(ActionEvent event) {
        DataModel selectedData = recordTableView.getSelectionModel().getSelectedItem();
        if (selectedData == null) {
            // If no row is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row in the table.");
            alert.showAndWait();
            return; // Exit the method without proceeding further
        }

        viewRecord(selectedData);
    }

    void populateForm(DataModel selectedData) {
        // Assuming household_id, first_name, middle_name, last_name, suffix, age, gender,
        // civil_status, date_registered, pos_in_the_fam, h_member, zone, and monthly_income
        // are the names of your form fields/components.

        household_id.setText(String.valueOf(selectedData.getHousehold_id()));
        first_name.setText(selectedData.getFirst_name());
        middle_name.setText(selectedData.getMiddle_name());
        last_name.setText(selectedData.getLast_name());
        suffix.setText(selectedData.getSuffix());
        civil_status.setText(selectedData.getCivil_status());
        pos_in_the_fam.setText(selectedData.getPos_in_the_fam());
        h_member.setText(String.valueOf(selectedData.getH_member()));
        zone.setText(String.valueOf((char) selectedData.getZone()));
        monthly_income.setText(String.valueOf(selectedData.getMonthly_income()));
        selectedData.setDate_registered(selectedData.getDate_registered());

        recordTableView.refresh();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void showRecord() {
        // This method can be implemented if needed
    }

    private void initializeSearchListener() {
        FilteredList<DataModel> filteredData = new FilteredList<>(originalData, p -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim();
            if (searchText.isEmpty()) {
                recordTableView.setItems(originalData);
            } else {
                filteredData.setPredicate(data -> {
                    if (String.valueOf(data.getHousehold_id()).contains(searchText)
                            || data.getFirst_name().toLowerCase().contains(searchText.toLowerCase())
                            || data.getLast_name().toLowerCase().contains(searchText.toLowerCase())
                            || data.getMiddle_name().toLowerCase().contains(searchText.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
                recordTableView.setItems(filteredData);
            }
        });
    }

    private void handleDeleteAction() {
        DataModel selectedData = recordTableView.getSelectionModel().getSelectedItem();
        if (selectedData == null) {
            // If no row is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row in the table to delete.");
            alert.showAndWait();
            return; // Exit the method without proceeding further
        }

        // Prompt the user for confirmation before deleting
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this record?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the selected record from the database
            deleteRecord(selectedData);

            // Remove the selected record from the TableView
            recordTableView.getItems().remove(selectedData);
        }
    }

    @FXML
    private void handleTableViewRightClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // Check if right mouse button is clicked
            DataModel selectedData = recordTableView.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                if (!contextMenuShownMap.containsKey(selectedData) || !contextMenuShownMap.get(selectedData)) {
                    contextMenuShownMap.put(selectedData, true);

                    ContextMenu contextMenu = new ContextMenu();

                    MenuItem addMenuItem = new MenuItem("Add");
                    MenuItem deleteMenuItem = new MenuItem("Delete");
                    MenuItem viewMenuItem = new MenuItem("View Record");

                    addMenuItem.setOnAction(e -> openUniqueStage());
                    deleteMenuItem.setOnAction(e -> deleteRecord(selectedData));
                    viewMenuItem.setOnAction(e -> viewRecord(selectedData));

                    contextMenu.getItems().addAll(viewMenuItem, addMenuItem, deleteMenuItem);

                    contextMenu.show(recordTableView, event.getScreenX(), event.getScreenY());

                    contextMenu.setOnHidden(hiddenEvent -> {
                        contextMenuShownMap.put(selectedData, false);
                    });

                    recordTableView.setOnMouseExited(mouseEvent -> {
                        contextMenu.hide();
                    });

                    recordTableView.setOnMouseEntered(mouseEvent -> {
                        contextMenu.hide();
                    });
                }
            }
        }
    }

    private void viewRecord(DataModel selectedData) {
        try {
            int householdId = selectedData.getHousehold_id();

            // Fetch the household data for the given household ID
            DataModel householdData = getHouseholdData(householdId);

            // Check if household data is retrieved successfully and the IDs match
            if (householdData != null && householdData.getHousehold_id() == householdId) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
                Parent root = loader.load();

                ViewController viewController = loader.getController();
                viewController.populateForm(selectedData);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                // Set the stage to full screen
                stage.setFullScreen(true);

                stage.show();
            } else {
                showAlert("Validation Error", "The household ID in the form does not match any household record.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord(DataModel selectedData) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            String sql = "DELETE FROM household WHERE household_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, selectedData.getHousehold_id());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Record deleted successfully.");
                successAlert.showAndWait();
            } else {
                showErrorAlert("Failed to delete record. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while deleting record from database: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Error occurred while closing database resources: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private DataModel getHouseholdData(int householdId) {
        DataModel householdData = null;
        // Implement database retrieval logic to fetch household data based on the household ID
        // You need to establish a database connection and execute a SQL query to retrieve the data
        // Replace the following code with your actual database logic

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM household WHERE household_id = ?");) {
            statement.setInt(1, householdId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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

                householdData = new DataModel(householdId, firstName, middleName, lastName, suffix, civilStatus, posInTheFam, hMember, zone, monthlyIncome, dateRegistered);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
        }

        return householdData;
    }
// Method to update deletion status in the database

    private void updateDeletionStatus(DataModel data) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            String sql = "UPDATE household SET deleted = ? WHERE household_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, data.isDeleted());
            preparedStatement.setInt(2, data.getHousehold_id());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Record restored successfully.");
                successAlert.showAndWait();
            } else {
                showErrorAlert("Failed to restore record. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while updating deletion status in the database: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Error occurred while closing database resources: " + e.getMessage());
            }
        }
    }

// Method to delete record permanently from the database
    private void deleteRecordForever(DataModel data) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            String sql = "DELETE FROM household WHERE household_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, data.getHousehold_id());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Record deleted forever successfully.");
                successAlert.showAndWait();
            } else {
                showErrorAlert("Failed to delete record forever. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while deleting record permanently from the database: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Error occurred while closing database resources: " + e.getMessage());
            }
        }
    }

    private void updateDeleteStatus(DataModel selectedData) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            String sql = "UPDATE household SET deleted = ? WHERE household_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true); // Set deleted to true
            preparedStatement.setInt(2, selectedData.getHousehold_id());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Record status updated successfully.");
                successAlert.showAndWait();
            } else {
                showErrorAlert("Failed to update record status. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error occurred while updating record status in the database: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Error occurred while closing database resources: " + e.getMessage());
            }
        }
    }

    private void applyCssAlignment() {
        // Right align columns
        household_id.getStyleClass().add("left-aligned");
        h_member.getStyleClass().add("left-aligned");
        zone.getStyleClass().add("left-aligned");
        monthly_income.getStyleClass().add("left-aligned");

        // Left align columns
        full_name.getStyleClass().add("right-aligned");
        pos_in_the_fam.getStyleClass().add("right-aligned");
        date_registered.getStyleClass().add("right-aligned");
        civil_status.getStyleClass().add("right-aligned");
    }

    private void handleFieldSearch(KeyEvent event) {
        String searchText = tfSearch.getText().trim();
        FilteredList<DataModel> filteredData = new FilteredList<>(originalData);
        if (searchText.isEmpty()) {
            recordTableView.setItems(originalData);
        } else {
            filteredData.setPredicate(data
                    -> String.valueOf(data.getHousehold_id()).contains(searchText)
                    || data.getFirst_name().toLowerCase().contains(searchText.toLowerCase())
                    || data.getLast_name().toLowerCase().contains(searchText.toLowerCase())
                    || data.getMiddle_name().toLowerCase().contains(searchText.toLowerCase())
            );
            recordTableView.setItems(filteredData);
        }
    }

    @FXML
    private void handleFilterBy(ActionEvent event) {
        Integer selectedZone = searchBarFilter.getSelectionModel().getSelectedItem();
        FilteredList<DataModel> filteredData = new FilteredList<>(originalData);
        if (selectedZone == null) {
            recordTableView.setItems(originalData);
        } else {
            filteredData.setPredicate(data -> data.getZone() == selectedZone);
            recordTableView.setItems(filteredData);
        }
    }

    @FXML
    private void handleFieldSearch(MouseEvent event) {
    }

    @FXML
    private void handleFieldSearch(ActionEvent event) {
    }

    public class ComboBoxZone {

        private ObservableList<Integer> zones;

        public ComboBoxZone() {
            zones = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7);
        }

        public ObservableList<Integer> getZones() {
            return zones;
        }
    }

    public ObservableList<Integer> getZoneList() {
        ObservableList<Integer> zoneList = FXCollections.observableArrayList();
        // Add zone numbers 1 to 7 to the list
        for (int i = 1; i <= 8; i++) {
            zoneList.add(i);
        }
        return zoneList;
    }

    @FXML
    private void refreshMainTable(MouseEvent event) {
        // Reload the data from the database
        ObservableList<DataModel> newData = loadDataFromDatabase();

        // Get the current search text
        String searchText = tfSearch.getText().trim();

        // Filter the new data based on the search text
        FilteredList<DataModel> filteredData = new FilteredList<>(newData);
        if (!searchText.isEmpty()) {
            filteredData.setPredicate(data
                    -> String.valueOf(data.getHousehold_id()).contains(searchText)
                    || data.getFirst_name().toLowerCase().contains(searchText.toLowerCase())
                    || data.getLast_name().toLowerCase().contains(searchText.toLowerCase())
                    || data.getMiddle_name().toLowerCase().contains(searchText.toLowerCase())
            );
        }

        // Apply the search listener to the text field
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String newSearchText = newValue.trim();
            if (newSearchText.isEmpty()) {
                recordTableView.setItems(newData);
            } else {
                FilteredList<DataModel> newFilteredData = new FilteredList<>(newData);
                newFilteredData.setPredicate(data
                        -> String.valueOf(data.getHousehold_id()).contains(newSearchText)
                        || data.getFirst_name().toLowerCase().contains(newSearchText.toLowerCase())
                        || data.getLast_name().toLowerCase().contains(newSearchText.toLowerCase())
                        || data.getMiddle_name().toLowerCase().contains(newSearchText.toLowerCase())
                );
                recordTableView.setItems(newFilteredData);
            }
        });

        // Update the TableView with the newly loaded and filtered data
        recordTableView.setItems(filteredData);
    }

    //Nav Bar
    @FXML
    private void dashboardClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
    }

    @FXML
    private void barangayInfoClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayInformation/barangayInformation.fxml", "Barangay Information");
    }

    @FXML
    private void barangayOfficialClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayOfficial/barangayOfficial.fxml", "Barangay Official");
    }

    @FXML
    private void residentRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/residentRecord/residentRecord.fxml", "Resident Record");
    }

    @FXML
    private void businessRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/businessRecord/businessRecord.fxml", "Business Record");
    }

    @FXML
    private void householdRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/householdRecord/householdRecord.fxml", "Household Record");
    }

    @FXML
    private void requestedDocsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
    }

    @FXML
    private void treasuryClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/treasury/treasury.fxml", "Treasury");
    }

    @FXML
    private void reportsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/reports/reports.fxml", "Reports");
    }

    @FXML
    private void logOutClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");
    }

}
