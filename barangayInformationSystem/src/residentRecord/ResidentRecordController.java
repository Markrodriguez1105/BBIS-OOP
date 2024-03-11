/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package residentRecord;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.main;

public class ResidentRecordController implements Initializable {

    @FXML
    private Button addResidentBtn;
    @FXML
    private Button updateInfoBtn;
    @FXML
    private Button deleteResidentBtn;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Resident> ResidentInfoTableview;
    @FXML
    private TableColumn<Resident, String> tableColumnName;
    @FXML
    private TableColumn<Resident, String> tableColumnAge;
    @FXML
    private TableColumn<Resident, String> tableColumnGender;
    @FXML
    private TableColumn<Resident, String> tableColumnBirthdate;
    @FXML
    private TableColumn<Resident, String> tableColumnPurok;
    @FXML
    private TableColumn<Resident, String> tableColumnHouseholdNo;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    private ResidentFormController residentForm;
    private ResidentUpdateFormController updateForm;
    @FXML
    private Text user_lname;
    @FXML
    private Text user_fname;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userShowData();
        user_fname.setText(LogIn.LogInController.user_fname);
        user_lname.setText(LogIn.LogInController.user_lname);
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
    private void addResident(ActionEvent event) throws IOException {
        main main = new main();
        main.overlayWindow("/residentRecord/addForm.fxml", "Add Resident");
    }

//         ResidentFormController residentFormController = new ResidentFormController(this);
    // Rest of the codex
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResidentForm.fxml"));
//            Parent root = loader.load();
//
//            residentForm = loader.getController();
//            residentForm.setCRUDForm(this);
//
//            Stage stage = new Stage();
//            stage.setTitle("Add Resident");
//            stage.setScene(new Scene(root));
//
//            stage.show();
//            userShowData();
//            updateTableView();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    private void updateResidentInfo(ActionEvent event) {

        Resident selectedResident = ResidentInfoTableview.getSelectionModel().getSelectedItem();

        if (selectedResident != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ResidentUpdateInformationForm.fxml"));
                Parent root = loader.load();

                updateForm = loader.getController();
                updateForm.setCRUDForm(this);

                Stage stage = new Stage();
                stage.setTitle("Update Resident Information");
                stage.setScene(new Scene(root));

                stage.show();
                userShowData();
                updateTableView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage("Select a resident first before updating resident information");
        }
    }

    @FXML
    private void DeleteResident(ActionEvent event) {
        connect = Connect.connectDB();

        try {

            Resident rD = ResidentInfoTableview.getSelectionModel().getSelectedItem();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE  "
                    + rD.getFullName() + " to your database?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM resident_informations WHERE fname = ? AND mname = ? AND lname = ?";

                prepare = connect.prepareStatement(deleteData);
                prepare.setString(1, rD.getfName());
                prepare.setString(2, rD.getmName());
                prepare.setString(3, rD.getlName());

                prepare.executeUpdate();

                userShowData();
                updateTableView();

                successMessage("Successfully deleted the resident");
            } else {
                errorMessage("Cancelled");
            }

        } catch (SQLException e) {
        }
    }

    private void searchTable(String searchText) {
        FilteredList<Resident> filteredList = new FilteredList<>(residentData, Resident
                -> Resident.getfName().toUpperCase().contains(searchText.toUpperCase())
                || Resident.getlName().toUpperCase().contains(searchText.toUpperCase())
                || Resident.getmName().toUpperCase().contains(searchText.toUpperCase())
                || String.valueOf(Resident.getPurok()).contains(searchText)
                || String.valueOf(Resident.getHousehold()).contains(searchText)
        );

        ResidentInfoTableview.setItems(filteredList);
    }

    @FXML
    private void searchResidentRecord(ActionEvent event) {
    }

    public ObservableList<Resident> residentList() {

        ObservableList listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM resident_informations";

        connect = Connect.connectDB();

        try {

            if (connect == null) {
                System.err.println("Database connection is null.");
                return listData; // Return an empty list
            }

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            Resident residentDatas;

            while (result.next()) {

                residentDatas = new Resident(result.getString("fname"), result.getString("mname"),
                        result.getString("lname"), result.getString("suffix"), result.getString("gender"), result.getInt("age"),
                        result.getDate("birthdate"), result.getString("civil_status"), result.getString("religion"),
                        result.getString("in_out_school"), result.getString("educ_attainment"), result.getString("occupation"),
                        result.getString("labor_force"), result.getString("pregnant"), result.getString("nursing_mother"),
                        result.getString("family_planning"), result.getString("patient_with_disability"), result.getInt("purok"), result.getInt("household_no"));

                listData.add(residentDatas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<Resident> residentData;

    public void userShowData() {
        residentData = residentList();

        System.out.println("Resident Data Size: " + residentData.size());

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableColumnBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        tableColumnPurok.setCellValueFactory(new PropertyValueFactory<>("purok"));
        tableColumnHouseholdNo.setCellValueFactory(new PropertyValueFactory<>("household"));

        ResidentInfoTableview.setItems(residentData);

        searchBar.textProperty().addListener((Observable, oldValue, newValue) -> {
            searchTable(newValue);
        });
    }

    @FXML
    public void selectResident() {

        ResidentInfoTableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Resident selectedResident1click = ResidentInfoTableview.getSelectionModel().getSelectedItem();
                ResidentUpdateFormController.selectedResident = newValue;
            }
        });

        ResidentInfoTableview.setOnMouseClicked((MouseEvent event) -> {
//            count the click for mouse event 
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Resident selectedResident2clicks = ResidentInfoTableview.getSelectionModel().getSelectedItem();
                // Double click action
                if (selectedResident2clicks != null) {
                    try {
                        ResidentInformationWindowController.selectedResident = selectedResident2clicks;
                        ResidentUpdateFormController.selectedResident = selectedResident2clicks;

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResidentInformationWindow.fxml"));
                        Parent root = loader.load();
                        //load the stage for the resident information Preview
                        Stage stage = new Stage();
                        stage.setTitle("Update Resident Information");
                        stage.setScene(new Scene(root));

                        stage.show(); //show the stage
                        userShowData(); // show the information in the table
                        updateTableView(); //update the table
                        event.consume();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void updateTableView() {
        residentData.clear();
        residentData.addAll(residentList()); // Add updated data
        ResidentInfoTableview.setItems(residentData);
        ResidentInfoTableview.refresh();
    }

    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void successMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void removeData(Resident selected) throws SQLException {
        Database database = new Database();
        String key = selected.getfName() + selected.getmName() + selected.getlName();
        ResultSet result = database.executeQuery("SELECT `resident_id`\n"
                + "FROM `resident`\n"
                + "WHERE CONCAT(`first_name`, `middle_name`, `last_name`) = '" + key + "';");
        while (result.next()) {
            database.executeQuery("UPDATE `resident`\n"
                    + "SET `inOutBarangay` = false\n"
                    + "WHERE `resident_id` = " + result.getString(1) + ";");
            System.out.println(result.getString(1));
        }
    }
}
