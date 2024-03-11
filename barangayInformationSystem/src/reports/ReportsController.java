/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reports;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import reports.AddReportController;
import main.main;

public class ReportsController implements Initializable {

    @FXML
    private Label CategoryLabel;
    @FXML
    private ComboBox<String> CategoryComboBox;
    @FXML
    private ComboBox<String> RecordsComboBox;
    @FXML
    private TextField SearchTextField;
    @FXML
    private TableView<ReportsData> ReportTableView;
    @FXML
    private TableColumn<ReportsData, String> NumberTableColumn;
    @FXML
    private TableColumn<ReportsData, String> ReportTypeTableColumn;
    @FXML
    private TableColumn<ReportsData, String> NameTableColumn;
    @FXML
    private TableColumn<ReportsData, String> ContactNumberTableColumn;
    @FXML
    private TableColumn<ReportsData, String> DateTableColumn;
    @FXML
    private TableColumn<ReportsData, String> ReasonTableColumn;
    @FXML
    private TableColumn<ReportsData, String> StatusTableColumn;
    @FXML
    private TableColumn<ReportsData, String> RecordStatusTableColumn;
    @FXML
    private TableColumn<ReportsData, String> ActionTableColumn;

    private final DatabaseConnector dbConnector = new DatabaseConnector();
    @FXML
    private Button AddReportButton;
    @FXML
    private Text user_lname;
    @FXML
    private Text user_fname;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the TableView columns
        user_fname.setText(LogIn.LogInController.user_fname);
        user_lname.setText(LogIn.LogInController.user_lname);

        loadTableView();

        // Load data from the database and populate the TableView
        loadData();

        // Set up the ComboBox items and add a listener for item changes
        initComboBox();

        // Set the initial value for ComboBox
        CategoryComboBox.setValue("All Reports");
        RecordsComboBox.setValue("All Records");

        // Add listener to SearchTextField for text changes
        SearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // If the search field is empty, reload the original data
                loadData();
            } else {
                // Filter data based on the entered name
                filterDataByName(newValue);
            }
        });

    }

    private void loadTableView() {
        // Set up the cell value factories for each column
        NumberTableColumn.setCellValueFactory(data -> data.getValue().numberProperty());
        ReportTypeTableColumn.setCellValueFactory(data -> data.getValue().reportTypeProperty());
        NameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        ContactNumberTableColumn.setCellValueFactory(data -> data.getValue().contactNumberProperty());
        DateTableColumn.setCellValueFactory(data -> data.getValue().dateProperty());
        ReasonTableColumn.setCellValueFactory(data -> data.getValue().reasonProperty());
        StatusTableColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        RecordStatusTableColumn.setCellValueFactory(data -> {
            String status = data.getValue().recordStatusProperty().get();
            if ("1".equals(status)) {
                return new SimpleStringProperty("Active");
            } else if ("0".equals(status)) {
                return new SimpleStringProperty("Archived");
            } else {
                // You can handle other cases if needed
                return new SimpleStringProperty(status);
            }
        });
        //ActionTableColumn.setCellValueFactory(data -> data.getValue().actionProperty());

        // Set up the cell factory for NumberTableColumn
        NumberTableColumn.setCellFactory(column -> new TableCell<ReportsData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        // Set up the cell factory for ActionTableColumn
        //ActionTableColumn.setCellValueFactory(data -> new SimpleStringProperty(""));
        // Set up the cell factory for ActionTableColumn
        ActionTableColumn.setCellFactory(param -> new TableCell<ReportsData, String>() {
            HBox buttons = new HBox();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ReportsData rowData = getTableView().getItems().get(getIndex());

                    // Clear the buttons HBox
                    buttons.getChildren().clear();

                    if (rowData != null) {
                        // Check if Edit button should be shown
                        if (shouldShowEditButton(rowData)) {
                            buttons.getChildren().add(createEditButton(rowData));
                        }

                        // Check if Recover button should be shown
                        if (shouldShowRecoverButton(rowData)) {
                            buttons.getChildren().add(createRecoverButton(rowData));
                        }

                        // Check if Delete button should be shown
                        if (shouldShowDeleteButton(rowData)) {
                            buttons.getChildren().add(createDeleteButton(rowData));
                        }

                        buttons.setSpacing(5);
                        setGraphic(buttons);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private boolean shouldShowEditButton(ReportsData rowData) {
                // Implement your logic to determine if Edit button should be shown
                // For example, you can check if certain conditions are met in the rowData
                boolean shouldShow = true; // Modify this based on your logic
                return shouldShow;
            }

            private boolean shouldShowDeleteButton(ReportsData rowData) {
                int reportStatus = Integer.parseInt(rowData.getRecordStatus());
                // Implement your logic to determine if Delete button should be shown
                // For example, you can check if certain conditions are met in the rowData
                boolean shouldShow = reportStatus == 1; // Modify this based on your logic
                return shouldShow;
            }

            private boolean shouldShowRecoverButton(ReportsData rowData) {
                int reportStatus = Integer.parseInt(rowData.getRecordStatus());
                // Implement your logic to determine if Recover button should be shown
                // For example, you can check if certain conditions are met in the rowData
                boolean shouldShow = reportStatus == 0;
                return shouldShow;
            }

            private Button createEditButton(ReportsData rowData) {
                Button editButton = new Button("Edit");
                editButton.setOnAction(event -> openEditDialog(rowData));
                // Set width
                editButton.setMinWidth(50); // Set the desired width
                // Set color
                editButton.setStyle("-fx-background-color: #3A53A5; -fx-text-fill: white;");
                return editButton;
            }

            private Button createDeleteButton(ReportsData rowData) {
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> showDeleteConfirmation(rowData));
                // Set width
                deleteButton.setMinWidth(50); // Set the desired width
                // Set color
                deleteButton.setStyle("-fx-background-color: #DB4040; -fx-text-fill: white;");
                return deleteButton;
            }

            private Button createRecoverButton(ReportsData rowData) {
                Button recoverButton = new Button("Recover");
                recoverButton.setOnAction(event -> showRecoverConfirmation(rowData));
                // Set width
                recoverButton.setMinWidth(50); // Set the desired width
                // Set color
                recoverButton.setStyle("-fx-background-color: #51A351; -fx-text-fill: white;");
                return recoverButton;
            }
        });

    }

    private void openEditDialog(ReportsData rowData) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportsDetails.fxml"));
        try {
            Parent root = loader.load();
            ReportDetailsController reportDetailsController = loader.getController();
            reportDetailsController.setReportDetails(rowData);

            Stage stage = new Stage();
            stage.setTitle("Edit Report");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Handle the result if needed after the edit dialog is closed
            // For example, you can refresh the TableView after editing
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmation(ReportsData report) {
        if (showDeleteConfirmationDialog()) {
            deleteReport(report);
        }
    }

    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Report");
        alert.setHeaderText("Are you sure you want to archived this report?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void showRecoverConfirmation(ReportsData report) {
        if (showRecoverConfirmationDialog()) {
            recoverReport(report);
        }
    }

    private boolean showRecoverConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recover Report");
        alert.setHeaderText("Are you sure you want to recover this report?");
        //alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void recoverReport(ReportsData report) {
        try (Connection connection = dbConnector.connect()) {
            String query = "UPDATE `report` SET `report_status` = '1' WHERE `report_id` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, report.getReportId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reload data after recovering a report
        loadData();
    }

    private void deleteReport(ReportsData report) {
        try (Connection connection = dbConnector.connect()) {
            String query = "UPDATE `report` SET `report_status` = '0' WHERE `report_id` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, report.getReportId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reload data after deactivating a report
        loadData();
    }

    private void loadData() {
        // Connect to the database
        try (Connection connection = dbConnector.connect()) {
            // Execute your specific query to fetch data
            String query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                    + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                    + "FROM `report` WHERE 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

                // Create an ObservableList to store the data
                ObservableList<ReportsData> data = FXCollections.observableArrayList();

                // Iterate through the result set and add data to the ObservableList
                while (resultSet.next()) {
                    ReportsData reportData = new ReportsData(
                            resultSet.getString("report_id"),
                            resultSet.getString("report_type"),
                            resultSet.getString("first_name"),
                            resultSet.getString("middle_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("suffix"),
                            resultSet.getString("phone_num"),
                            resultSet.getString("email"),
                            resultSet.getString("date_recorded"),
                            resultSet.getString("reason"),
                            resultSet.getString("status"),
                            resultSet.getString("report_status")
                    );
                    data.add(reportData);
                }

                // Set the data in the TableView
                ReportTableView.setItems(data);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addReportClick(ActionEvent event) {
        openAddReportDialog();
    }

    private void openAddReportDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddReportDialog.fxml"));
        try {
            Parent root = loader.load();
            AddReportController addReportController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Add Report");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Handle the result if needed after the add report dialog is closed
            // For example, you can refresh the TableView after adding a new report
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterDataByName(String name) {
        try (Connection connection = dbConnector.connect()) {
            String query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                    + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                    + "FROM `report` "
                    + "WHERE `first_name` LIKE ? OR `middle_name` LIKE ? OR `last_name` LIKE ? OR `suffix` LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 1; i <= 4; i++) {
                    preparedStatement.setString(i, name + "%");
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<ReportsData> data = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        ReportsData reportData = new ReportsData(
                                resultSet.getString("report_id"),
                                resultSet.getString("report_type"),
                                resultSet.getString("first_name"),
                                resultSet.getString("middle_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("suffix"),
                                resultSet.getString("phone_num"),
                                resultSet.getString("email"),
                                resultSet.getString("date_recorded"),
                                resultSet.getString("reason"),
                                resultSet.getString("status"),
                                resultSet.getString("report_status")
                        );
                        data.add(reportData);
                    }
                    ReportTableView.setItems(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComboBox() {
        ObservableList<String> reportTypes = FXCollections.observableArrayList("All Reports");
        reportTypes.addAll(getDistinctReportTypes());
        CategoryComboBox.setItems(reportTypes);

        ObservableList<String> recordStatusOptions = FXCollections.observableArrayList("All Records", "Active", "Archived");
        RecordsComboBox.setItems(recordStatusOptions);

        CategoryComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    CategoryLabel.setText(newValue);
                    if ("All Reports".equals(newValue)) {
                        loadData();
                    } else {
                        loadDataByReportType(newValue, RecordsComboBox.getValue());
                    }
                });

        RecordsComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if ("All Records".equals(newValue)) {
                        loadData();
                    } else {
                        loadDataByRecordStatus(CategoryComboBox.getValue(), newValue);
                    }
                });
    }

    private ObservableList<String> getDistinctReportTypes() {
        ObservableList<String> reportTypes = FXCollections.observableArrayList();
        try (Connection connection = dbConnector.connect()) {
            String query = "SELECT DISTINCT `report_type` FROM `report`";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reportTypes.add(resultSet.getString("report_type"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportTypes;
    }

    private void loadDataByReportType(String reportType, String recordStatus) {
        try (Connection connection = dbConnector.connect()) {
            String query;

            if ("All Reports".equals(reportType)) {
                query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                        + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                        + "FROM `report`";
            } else {
                query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                        + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                        + "FROM `report` WHERE `report_type` = ?";
            }

            if (!"All Records".equals(recordStatus)) {
                query += " AND `report_status` = ?";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, reportType);

                if (!"All Records".equals(recordStatus)) {
                    preparedStatement.setString(2, recordStatus.equals("Active") ? "1" : "0");
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<ReportsData> data = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        ReportsData reportData = new ReportsData(
                                resultSet.getString("report_id"),
                                resultSet.getString("report_type"),
                                resultSet.getString("first_name"),
                                resultSet.getString("middle_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("suffix"),
                                resultSet.getString("phone_num"),
                                resultSet.getString("email"),
                                resultSet.getString("date_recorded"),
                                resultSet.getString("reason"),
                                resultSet.getString("status"),
                                resultSet.getString("report_status")
                        );
                        data.add(reportData);
                    }
                    ReportTableView.setItems(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataByRecordStatus(String reportType, String recordStatus) {
        try (Connection connection = dbConnector.connect()) {
            String query;

            if ("All Records".equals(recordStatus)) {
                query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                        + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                        + "FROM `report`";
            } else {
                query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                        + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                        + "FROM `report` WHERE `report_status` = ?";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (!"All Records".equals(recordStatus)) {
                    preparedStatement.setString(1, recordStatus.equals("Active") ? "1" : "0");
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<ReportsData> data = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        ReportsData reportData = new ReportsData(
                                resultSet.getString("report_id"),
                                resultSet.getString("report_type"),
                                resultSet.getString("first_name"),
                                resultSet.getString("middle_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("suffix"),
                                resultSet.getString("phone_num"),
                                resultSet.getString("email"),
                                resultSet.getString("date_recorded"),
                                resultSet.getString("reason"),
                                resultSet.getString("status"),
                                resultSet.getString("report_status")
                        );
                        data.add(reportData);
                    }
                    ReportTableView.setItems(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Secretary")) {
            main main = new main();
            main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void treasuryClick(ActionEvent event) throws IOException {
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Treasurer")) {
            main main = new main();
            main.changeScene("/treasury/treasury.fxml", "Treasury");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void reportsClick(ActionEvent event) throws IOException {
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Secretary")) {
            main main = new main();
            main.changeScene("/reports/reports.fxml", "Reports");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void logOutClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");
    }

    @FXML
    private void clear(MouseEvent event) {
        SearchTextField.setText("");
        loadData();
    }

}
