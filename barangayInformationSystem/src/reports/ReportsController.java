/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reports;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.main;

/**
 * FXML Report Controller class
 *
 * @author Hello Jovel
 */
public class ReportsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Label CategoryLabel;
    @FXML private ComboBox<String> CategoryComboBox;
    @FXML private TextField SearchTextField;
    @FXML private TableView<ReportsData> ReportTableView;
    @FXML private TableColumn<ReportsData, String> ReportTypeTableColumn;
    @FXML private TableColumn<ReportsData, String> NameTableColumn;
    @FXML private TableColumn<ReportsData, String> PhoneNumberTableColumn;
    @FXML private TableColumn<ReportsData, String> EmailTableColumn;
    @FXML private TableColumn<ReportsData, String> ReasonTableColumn;

    private String[] Category = {"All Reports", "Barangay Indigency", "Barangay Clearance",
        "Agricultural Permit", "Business Permit", "Blotter", "Infrastructural Report", "Utility Report"};

    // Observable List to store data
    private final ObservableList<ReportsData> datalist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReportsData RP1 = new ReportsData("Blotter", "Jovel", "123", "Jovel@gmail.com", "Trip Ko Lang");
        ReportsData RP2 = new ReportsData("Business Permit", "Mark", "123", "Mark@gmail.com", "Trip Ko Lang");
        ReportsData RP3 = new ReportsData("Barangay Indigency", "James", "123", "James@gmail.com", "Trip Ko Lang");
        ReportsData RP4 = new ReportsData("Barangay Clearance", "Doms", "123", "Doms@gmail.com", "Trip Ko Lang");

        datalist.addAll(RP1, RP2, RP3, RP4);

        ReportTableView.setItems(datalist);

        CategoryComboBox.getItems().addAll(Category);
        CategoryComboBox.setOnAction(this::getCategory);

        ReportTypeTableColumn.setCellValueFactory(cellData -> cellData.getValue().reportTypeProperty());
        NameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        PhoneNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        EmailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        ReasonTableColumn.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
    }

    public void getCategory(ActionEvent event) {
        String selectedCategory = CategoryComboBox.getValue();
        CategoryLabel.setText(selectedCategory);

        FilteredList<ReportsData> filteredData = new FilteredList<>(datalist, report -> {
            if (selectedCategory.equals("All Reports")) {
                return true; // Show all reports
            } else {
                return report.getReportType().equals(selectedCategory);
            }
        });

        ReportTableView.setItems(filteredData);

        // Filter based on search text
        SearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(report -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all reports when search text is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return report.getName().toLowerCase().contains(lowerCaseFilter)
                        || report.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                        || report.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || report.getReason().toLowerCase().contains(lowerCaseFilter);
            });
        });
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
