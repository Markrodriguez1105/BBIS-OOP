/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businessRecord;

import java.util.regex.Pattern;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.main;

public class businessRecordController implements Initializable {

    static businessRecordController getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private Button Dashboard;

    @FXML
    private TextField Search;

    @FXML
    private TableColumn<Record, String> bNameCol;

    @FXML
    private TableColumn<Record, String> bTypeCol;

    @FXML
    private TableColumn<Record, Integer> busidCol;

    @FXML
    private AnchorPane recordView;

    @FXML
    private TableView<Record> recordsTable;

    @FXML
    private TableColumn<Record, String> monthCol;

    @FXML
    private TableColumn<Record, String> vatCol;

    @FXML
    private TableColumn<Record, String> statCol;

    @FXML
    private TableColumn<Record, String> baridCol;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Record record = null;

    ObservableList<Record> RecordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        vatFilterCombo.setItems(FXCollections.observableArrayList("Registered", "Not Registered"));
        statFilterCombo.setItems(FXCollections.observableArrayList("Active", "Inactive"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private ComboBox<String> vatFilterCombo; // Declare ComboBox field

    @FXML
    private ComboBox<String> statFilterCombo; // Declare ComboBox field

    @FXML
    private void filterByStatus(ActionEvent event) {
        String selectedStatus = statFilterCombo.getValue(); // Get the selected status from the ComboBox
        if (selectedStatus != null) {
            filterRecordsByStatus(selectedStatus);
        }
    }

    @FXML
    private void filterByVATStatus(ActionEvent event) {
        String selectedFilter = vatFilterCombo.getValue(); // Get the selected filter from the ComboBox
        if (selectedFilter != null) {
            if (selectedFilter.equals("Registered")) {
                // Filter the records for registered VAT status
                // Call a method in your controller to update the table view with filtered records
                filterRecordsByVATStatus("Registered");
            } else if (selectedFilter.equals("Not Registered")) {
                // Filter the records for not registered VAT status
                // Call a method in your controller to update the table view with filtered records
                filterRecordsByVATStatus("Not Registered");
            }
        }
    }

    private void filterRecordsByStatus(String status) {
        ObservableList<Record> filteredList = FXCollections.observableArrayList();
        for (Record record : RecordList) {
            if (record.getActive_status().equals(status)) {
                filteredList.add(record);
            }
        }
        recordsTable.setItems(filteredList);
    }

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public class DataValidator {

        // Validate phone number format (10 digits)
        public static boolean isValidPhoneNumber(String phoneNumber) {
            return Pattern.matches("\\d{10}", phoneNumber);
        }

        // Validate email format
        public static boolean isValidEmail(String email) {
            return Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email);
        }

        // You can add more validation methods for other fields as needed
    }

    private void filterRecordsByVATStatus(String vatStatus) {
        ObservableList<Record> filteredList = FXCollections.observableArrayList();
        for (Record record : RecordList) {
            if (record.getVat_status().equals(vatStatus)) {
                filteredList.add(record);
            }
        }
        recordsTable.setItems(filteredList);
    }

    private void closeForm() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/businessrecord/businessRecord.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.close();
            autoUpdate();
        } catch (IOException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Search(ActionEvent event) {
        String searchTerm = Search.getText();

        try {
            RecordList.clear();

            query = "SELECT * FROM `business` WHERE business_name LIKE ?";
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    RecordList.add(new Record(
                            resultSet.getInt("business_id"),
                            resultSet.getInt("barangay_id"),
                            resultSet.getString("business_name"),
                            resultSet.getString("business_type"),
                            resultSet.getString("first_name"),
                            resultSet.getString("middle_name"),
                            resultSet.getString("last_name"),
                            resultSet.getInt("monthly_income"),
                            resultSet.getDate("date_establishment"),
                            resultSet.getInt("tin"),
                            resultSet.getInt("num_employees"),
                            resultSet.getDate("date_registered"),
                            resultSet.getString("owner_phone_num"),
                            resultSet.getString("address"),
                            resultSet.getString("active_status"),
                            resultSet.getString("vat_status"),
                            resultSet.getString("owner_email")));
                }

                recordsTable.setItems(RecordList);
                // Close the ResultSet, PreparedStatement, and Connection
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        try {
            Record record = recordsTable.getSelectionModel().getSelectedItem();
            if (record != null) {
                query = "DELETE FROM `business` WHERE business_id = ?";
                connection = DbConnect.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, record.getBusiness_id());
                preparedStatement.execute();

                RecordList.remove(record);
                recordsTable.refresh();
            }
        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/businessrecord/addForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Show the "Add Form" window and wait for it to be closed
            autoUpdate(); // Call autoUpdate here to update the table after adding a record
        } catch (IOException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        try {
            RecordList.clear();

            query = "SELECT * FROM `business`";
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RecordList.add(new Record(
                        resultSet.getInt("business_id"),
                        resultSet.getInt("barangay_id"),
                        resultSet.getString("business_name"),
                        resultSet.getString("business_type"),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("monthly_income"),
                        resultSet.getDate("date_establishment"),
                        resultSet.getInt("tin"),
                        resultSet.getInt("num_employees"),
                        resultSet.getDate("date_registered"),
                        resultSet.getString("owner_phone_num"),
                        resultSet.getString("address"),
                        resultSet.getString("active_status"),
                        resultSet.getString("vat_status"),
                        resultSet.getString("owner_email")));

            }
            recordsTable.setItems(RecordList);
            recordsTable.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void update(ActionEvent event) {
        record = recordsTable.getSelectionModel().getSelectedItem();
        if (record != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateForm.fxml"));
            try {
                Parent parent = loader.load();
                UpdateFormController updateFormController = loader.getController();
                updateFormController.setUpdate(true);
                updateFormController.setBusinessId(record.getBusiness_id()); // Pass the business_id
                updateFormController.setTextFieldRecordExceptIds(
                        record.getBusiness_name(),
                        record.getBusiness_type(),
                        record.getFirst_name(),
                        record.getMiddle_name(),
                        record.getLast_name(),
                        record.getOwner_phone_num(),
                        record.getAddress(),
                        record.getMonthly_income(),
                        record.getTin(),
                        record.getVat_status(),
                        record.getNum_employees(),
                        (Date) record.getDate_registered(),
                        record.getOwner_Email(),
                        record.getActive_status()
                );

                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.showAndWait(); // Show the form and wait for it to close

                // Auto-update the records table
                autoUpdate();

                // Close the stage after submission
                stage.close();
                closeForm();
            } catch (IOException ex) {
                Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showAlert("Please select a record to update.");
        }
    }

    @FXML
    public void view(ActionEvent event) {
        try {
            // Get the selected record from the table
            Record selectedRecord = recordsTable.getSelectionModel().getSelectedItem();

            if (selectedRecord != null) {
                // Load the view.fxml scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/businessrecord/view.fxml"));
                Parent parent = loader.load();

                // Get the controller of the target scene
                ViewController viewController = loader.getController();

                // Pass the selected record to the controller
                viewController.initData(selectedRecord);

                // Show the view scene
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.showAndWait();
                autoUpdate();

            } else {
                // Show an alert or take appropriate action if no record is selected
                showAlert("Please select a record to view.");
            }

        } catch (IOException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadData() {
        connection = DbConnect.getConnection();
        busidCol.setCellValueFactory(new PropertyValueFactory<>("business_id"));
        baridCol.setCellValueFactory(new PropertyValueFactory<>("barangay_id"));
        bNameCol.setCellValueFactory(new PropertyValueFactory<>("business_name"));
        bTypeCol.setCellValueFactory(new PropertyValueFactory<>("business_type"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("monthly_income"));
        vatCol.setCellValueFactory(new PropertyValueFactory<>("vat_status"));
        statCol.setCellValueFactory(new PropertyValueFactory<>("active_status"));

        autoUpdate();

    }

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

    void autoUpdate() {
        try {
            RecordList.clear();

            query = "SELECT * FROM `business`";
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RecordList.add(new Record(
                        resultSet.getInt("business_id"),
                        resultSet.getInt("barangay_id"),
                        resultSet.getString("business_name"),
                        resultSet.getString("business_type"),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("monthly_income"),
                        resultSet.getDate("date_establishment"),
                        resultSet.getInt("tin"),
                        resultSet.getInt("num_employees"),
                        resultSet.getDate("date_registered"),
                        resultSet.getString("owner_phone_num"),
                        resultSet.getString("address"),
                        resultSet.getString("active_status"),
                        resultSet.getString("vat_status"),
                        resultSet.getString("owner_email")));
            }

            recordsTable.setItems(RecordList);
            recordsTable.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class vatFilterCombo {

        private static String getValue() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void setItems(ObservableList<String> observableArrayList) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public vatFilterCombo() {
        }
    }

    private static class vatCol {

        private static void setCellValueFactory(PropertyValueFactory<Object, Object> propertyValueFactory) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public vatCol() {
        }
    }
}
