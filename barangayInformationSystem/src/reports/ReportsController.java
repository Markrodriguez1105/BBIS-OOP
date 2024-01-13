/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reports;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.main;

/**
 * FXML Report Controller class
 *
 * @author Hello Jovel
 */
public class ReportsController implements Initializable {

    @FXML
    private Label CategoryLabel;
    
    @FXML
    private ComboBox<String> CategoryComboBox;
    
    @FXML
    private TextField SearchTextField;
    
    @FXML
    private TableView<ReportsData> ReportTableView;
    
    @FXML
    private TableColumn<ReportsData, String> ReportTypeTableColumn;
    
    @FXML
    private TableColumn<ReportsData, String> NameTableColumn;
    
    @FXML
    private TableColumn<ReportsData, String> ContactNumberTableColumn;
    
    @FXML
    private TableColumn<ReportsData, String> EmailTableColumn;
    
    @FXML
    private TableColumn<ReportsData, String> ReasonTableColumn;


    private String[] Category = {"All Reports", "Blotter", "Infrastructural Report", "Utility Report"};

    private final DatabaseConnector databaseConnector = new DatabaseConnector();
    private boolean usingSampleData = true; // Set this to true to use sample data, false to fetch from the database

    @FXML
    private void initializeTable() {
        // Initialize table columns
        ReportTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        NameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ContactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ReasonTableColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        SearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
        
        // Set content alignment to center for all columns
        ReportTypeTableColumn.setStyle("-fx-alignment: CENTER;");
        NameTableColumn.setStyle("-fx-alignment: CENTER;");
        ContactNumberTableColumn.setStyle("-fx-alignment: CENTER;");
        EmailTableColumn.setStyle("-fx-alignment: CENTER;");
        ReasonTableColumn.setStyle("-fx-alignment: CENTER;");
    
        // Load data into the table
        loadDataIntoTable();
        });
        // Set a row click event handler
        ReportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Get the selected item from the table
                ReportsData selectedReport = ReportTableView.getSelectionModel().getSelectedItem();

                // Open the detailed information window
                openReportDetailsWindow(selectedReport);
            }
        });
    }
    private void openReportDetailsWindow(ReportsData report) {
        try {
            // Use a relative path to load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportDetails.fxml"));
            Parent root = loader.load();

            // Access the controller and set the report details
            ReportDetailsController detailsController = loader.getController();
            detailsController.setReportDetails(report);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Report Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadDataIntoTable() {
        // Get the selected category
        String selectedCategory = CategoryComboBox.getValue();
        String searchText = SearchTextField.getText().toLowerCase(); // Convert to lowercase for case-insensitive comparison

         // Check if selectedCategory is null
        if (selectedCategory == null) {
            System.out.println("Selected category is null.");
            return;
        }

        ObservableList<ReportsData> reportsDataList = FXCollections.observableArrayList();

        // Check if using sample data or fetching from a database
        if (usingSampleData) {
            // Sample data for testing
            String[][] sampleData = {
                    {"Blotter", "John Doe", "123-456-7890", "john.doe@example.com", "Disturbance"},
                    {"Infrastructural Report", "Jane Smith", "987-654-3210", "jane.smith@example.com", "Road Maintenance"},
                    {"Utility Report", "Bob Johnson", "555-123-4567", "bob.johnson@example.com", "Water Supply"},
                    {"Infrastructural Report", "Alice Brown", "222-333-4444", "alice.brown@example.com", "Air Quality"},
                    {"Utility Report", "Michael Davis", "111-222-3333", "michael.davis@example.com", "Fire Prevention"},
                    {"Utility Report", "Emily Wilson", "777-888-9999", "emily.wilson@example.com", "Community Development"},
                    {"Blotter", "David Miller", "444-555-6666", "david.miller@example.com", "Traffic Management"},
                    {"Infrastructural Report", "Sarah Jones", "666-777-8888", "sarah.jones@example.com", "Public Health"},
                    {"Utility Report", "Chris Thompson", "999-000-1111", "chris.thompson@example.com", "Special Events"},
                    {"Infrastructural Report", "Jennifer Lee", "123-321-4567", "jennifer.lee@example.com", "Urban Development"}
                    // Add more sample data as needed
            };

            // Filter sample data based on selected category
            for (String[] data : sampleData) {
                if (("All Reports".equals(selectedCategory) || selectedCategory.equals(data[0]))
                        && (searchText.isEmpty() || containsIgnoreCase(data, searchText))) {
                    ReportsData report = new ReportsData(data[0], data[1], data[2], data[3], data[4]);
                    reportsDataList.add(report);
                }
            }
        } else {
            // Fetch data from the database
            String query;
            if ("All Reports".equals(selectedCategory)) {
                query = "SELECT * FROM report";
            } else {
                query = "SELECT * FROM report WHERE report_type = '" + selectedCategory + "'";
            }

            ResultSet resultSet = databaseConnector.getFromDatabase(query);

            // Check if resultSet is null
            if (resultSet == null) {
                System.out.println("Error fetching data from the database. ResultSet is null.");
                return;
            }

            try {
                while (resultSet.next()) {
                    ReportsData report = new ReportsData(
                            resultSet.getString("report_type"),
                            resultSet.getString("name"),
                            resultSet.getString("phone_num"),
                            resultSet.getString("email"),
                            resultSet.getString("reason")
                    );
                    reportsDataList.add(report);
                }
            } catch (SQLException e) {
                System.out.println("Error loading data into table: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error closing result set: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Set the data in the TableView
        ReportTableView.setItems(reportsDataList);
    }
    // Utility method to check if a string array contains the specified search text (case-insensitive)
    private boolean containsIgnoreCase(String[] data, String searchText) {
        for (String value : data) {
            if (value.toLowerCase().contains(searchText)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize combo box
        CategoryComboBox.getItems().addAll(Category);
        // Set the initial value to "All Reports"
        CategoryComboBox.setValue("All Reports");
        // Set the label to "All Reports" initially
        CategoryLabel.setText("All Reports");
        // Load data into the table immediately
        loadDataIntoTable();
        CategoryComboBox.setOnAction(event -> {
            getCategory(event);
            loadDataIntoTable(); // Reload data when category changes
        });

        // Initialize the table
        initializeTable();
    }

    public void getCategory(ActionEvent event) {
        String selectedCategory = CategoryComboBox.getValue();
        CategoryLabel.setText(selectedCategory);

    }
    
    
    //Left-Nav Controller for buttons
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
