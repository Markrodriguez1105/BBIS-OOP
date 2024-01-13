/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import main.main;
import reports.DatabaseConnector;

/**
 * FXML Report Controller class
 *
 * @author Hello Jovel
 */
public class RequestedDocumentController implements Initializable {

    @FXML
    private Label ReqDocCategoryLabel;
    
    @FXML
    private ComboBox<String> ReqDocCategoryComboBox;
    
    @FXML
    private TextField ReqDocSearchTextField;
    
    @FXML
    private TableView<DocReqData> ReqDocReportTableView = new TableView();
    
    @FXML
    private TableColumn<DocReqData, String> DocTypeTableColumn;
    
    @FXML
    private TableColumn<DocReqData, String> DocNameTableColumn;
    
    @FXML
    private TableColumn<DocReqData, String> DocContactNumberTableColumn;
    
    @FXML
    private TableColumn<DocReqData, String> DocDateTableColumn;
    
    @FXML
    private TableColumn<DocReqData, String> DocReasonTableColumn;

    @FXML
    private TableColumn<DocReqData, String> DocPrintTableColumn;

    private String[] Category = {"All Documents", "Barangay Indigency", "Barangay Clearance", "Agricultural Permit", "Business Permit"};

    private final DatabaseConnector databaseConnector = new DatabaseConnector();
    private boolean usingSampleData = true; // Set this to true to use sample data, false to fetch from the database
    static ObservableList<DocReqData> DocDataList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize table columns
        DocTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("docType"));
        DocNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        DocContactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        DocDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        DocReasonTableColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        DocPrintTableColumn.setCellValueFactory(new PropertyValueFactory<>("print"));

        // Initialize combo box
        ReqDocCategoryComboBox.getItems().addAll(Category);
        // Set the initial value to "All Reports"
        ReqDocCategoryComboBox.setValue("All Documents");
        // Set the label to "All Reports" initially
        ReqDocCategoryLabel.setText("All Documents");

        // Initialize the observable list
        DocDataList = FXCollections.observableArrayList();

        try {
            // Load data into the table immediately
            loadDataDocIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(RequestedDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReqDocSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Set content alignment to center for all columns
            ReqDocReportTableView.setStyle("-fx-alignment: CENTER;");
            DocNameTableColumn.setStyle("-fx-alignment: CENTER;");
            DocContactNumberTableColumn.setStyle("-fx-alignment: CENTER;");
            DocDateTableColumn.setStyle("-fx-alignment: CENTER;");
            DocReasonTableColumn.setStyle("-fx-alignment: CENTER;");
            DocPrintTableColumn.setStyle("-fx-alignment: CENTER;");
            try {
                // Reload data when search text changes
                loadDataDocIntoTable();
            } catch (SQLException ex) {
                Logger.getLogger(RequestedDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ReqDocCategoryComboBox.setOnAction(event -> {
            getCategory(event);
            try {
                loadDataDocIntoTable(); // Reload data when category changes
            } catch (SQLException ex) {
                Logger.getLogger(RequestedDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
        
    private void loadDataDocIntoTable() throws SQLException {
        // Clear the DocDataList
        DocDataList.clear();

        // Get the selected category
        String DocSelectedCategory = ReqDocCategoryComboBox.getValue();
        String searchText = ReqDocSearchTextField.getText().toLowerCase(); // Convert to lowercase for case-insensitive comparison

        // Check if selectedCategory is null or "All Documents"
        if (DocSelectedCategory == null || "All Documents".equals(DocSelectedCategory)) {
            DocSelectedCategory = "";
        }

        // Fetch data from the MySQL database
        String query;
        ResultSet resultSet = null;
        if (DocSelectedCategory.isEmpty()) {
            query = "SELECT * FROM `documents`;";
            resultSet = databaseConnector.executeQuery(query);
        } else {
            query = "SELECT * FROM `documents` WHERE `document_type = ?`;";
            resultSet = databaseConnector.getFromDatabase(query, DocSelectedCategory);
        }

        // Check if resultSet is null
        if (resultSet == null) {
            System.out.println("Error fetching data from the database. ResultSet is null.");
            return;
        }

        try {
            while (resultSet.next()) {
                DocReqData document = new DocReqData(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                DocDataList.add(document);
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

        // Set the data in the TableView
        ReqDocReportTableView.getItems().setAll(DocDataList);
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

    

    public void getCategory(ActionEvent event) {
        String selectedCategory = ReqDocCategoryComboBox.getValue();
        ReqDocCategoryLabel.setText(selectedCategory);

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
