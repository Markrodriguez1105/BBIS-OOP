package residentRecord;

import connect.Connect;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    @FXML
    private TableColumn<Resident, String> rowNo;
    @FXML
    private TableColumn<Resident, Integer> personalIncome;
    @FXML
    private TableColumn<Resident, String> tableColumnPhoneNo;
    @FXML
    private TableColumn<Resident, String> tableColumnStatus;
    @FXML
    private ComboBox<String> Category;
    @FXML
    private ComboBox<String> Group;
    @FXML
    private Button exportBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    private ResidentFormController residentForm;
    private String ExcelTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Category.setItems(FXCollections.observableArrayList("All", "Age", "Gender", "Civil Status", "Zone", "Residency Status"));
        Category.getSelectionModel().selectFirst();

        Category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectCategory(newValue);
            }
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            searchAndUpdateTable(newValue, Group.getValue());
        });

        Group.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            searchAndUpdateTable(searchBar.getText(), newValue);
        });

        userShowData();

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

    @FXML
    private void addResident(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addForm.fxml"));
            Parent root = loader.load();

            residentForm = loader.getController();
            residentForm.setCRUDForm(this);

            Stage stage = new Stage();
            stage.setTitle("Add Resident");
            stage.setScene(new Scene(root));

            stage.show();
            userShowData();
            updateTableView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateResidentInfo(ActionEvent event) {

        Resident selectedResident = ResidentInfoTableview.getSelectionModel().getSelectedItem();
        ResidentUpdateFormController.selectedResident = selectedResident;

        if (selectedResident != null) {
            try {

                FXMLLoader updateFormLoader = new FXMLLoader(getClass().getResource("updateForm.fxml"));
                Parent updateFormRoot = updateFormLoader.load();
                ResidentUpdateFormController updateForm = updateFormLoader.getController();
                updateForm.setRecordController(this);

                updateForm.setSelectedResident(selectedResident);

                Stage stage = new Stage();
                stage.setTitle("Update Resident Information");
                stage.setScene(new Scene(updateFormRoot));

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
    private void updateResidentStatus(ActionEvent event) {

        Resident rD = ResidentInfoTableview.getSelectionModel().getSelectedItem();

        if (rD != null) {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            if ("In the Barangay".equals(rD.getInOutBarangay())) {
                alert.setContentText("Are you sure you want to change  "
                        + rD.getFullName() + " status to RESIDENT?");
            } else {
                alert.setContentText("Are you sure you want to change  "
                        + rD.getFullName() + " status to NON-RESIDENT?");
            }

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                updateResidentStatus(rD);

                userShowData();
                updateTableView();

                successMessage("Successfully change Status");
            } else {
                errorMessage("Cancelled");
            }
        } else {
            errorMessage("Select a resident first before updating resident information");
        }
    }

    private void searchTable(String searchText) {
        if (Group.getValue() == null) {
            FilteredList<Resident> filteredList = new FilteredList<>(residentData, Resident
                    -> Resident.getlName().toUpperCase().startsWith(searchText.toUpperCase()));
            ResidentInfoTableview.setItems(filteredList);
        } else if (Group.getValue() != null) {
            FilteredList<Resident> filteredList = new FilteredList<>(ResidentInfoTableview.getItems(), Resident
                    -> Resident.getlName().toUpperCase().startsWith(searchText.toUpperCase()));

            ResidentInfoTableview.setItems(filteredList);
        }

    }

    private void selectCategory(String category) {

        if (category != null) {
            switch (category) {
                case "All":
                    userShowData();
                    Group.getItems().clear();
                    break;
                case "Age":
                    Group.setItems(FXCollections.observableArrayList("Infant", "Children",
                            "Teenager", "Adult", "Senior"));
                    Group.getSelectionModel().selectFirst();
                    break;
                case "Gender":
                    Group.setItems(FXCollections.observableArrayList("Male", "Female"));
                    Group.getSelectionModel().selectFirst();

                    break;
                case "Civil Status":
                    Group.setItems(FXCollections.observableArrayList("Single", "Legally Married", "Widowed",
                            "Divorced/Separated", "Common Love/Live In"));

                    Group.getSelectionModel().selectFirst();

                    break;
                case "Zone":
                    Group.setItems(FXCollections.observableArrayList(" zone 1", "zone 2",
                            "zone 3", "zone 4", "zone 5", "zone 6",
                            "zone 7"));

                    Group.getSelectionModel().selectFirst();
                    break;
                case "Residency Staus":
                    Group.setItems(FXCollections.observableArrayList("Resident", "Non-resident"));
                    Group.getSelectionModel().selectFirst();
                    break;
                default:
                    Group.setDisable(true);
            }
        }

    }

// Update table data based on selected Group
    private void searchAndUpdateTable(String searchText, String group) {
        FilteredList<Resident> filteredList;
        filteredList = new FilteredList<>(residentData, (var resident) -> {
            // Filtering based on search text
            boolean matchesSearchText = resident.getlName().toUpperCase().startsWith(searchText.toUpperCase());

            // Filtering based on selected group
            boolean matchesGroup = true; // Default to true if group is null
            if (group != null) {
                switch (Category.getValue()) {
                    case "Age":
                        // Add logic to filter based on age group
                        int age = resident.getAge();
                        switch (group) {
                            case "Infant":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = age >= 0 && age <= 1;
                                break;
                            case "Children":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = age >= 2 && age <= 12;
                                break;
                            case "Teenager":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = age >= 13 && age <= 18;
                                break;
                            case "Adult":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = age >= 19 && age <= 59;
                                break;
                            case "Senior":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = age >= 60;
                                break;
                            // Add more cases for other age groups if needed
                            default:
                                matchesGroup = false; // Invalid group
                                break;
                        }
                        break;
                    case "Gender":
                        ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                        matchesGroup = resident.getGender().equalsIgnoreCase(group);
                        break;
                    case "Civil Status":
                        ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                        matchesGroup = resident.getCivilStatus().equalsIgnoreCase(group);
                        break;
                    case "Zone":
                        switch (group) {
                            case "zone 1":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 1;
                                break;
                            case "zone 2":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 2;
                                break;
                            case "zone 3":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 3;
                                break;
                            case "zone 4":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 4;
                                break;
                            case "zone 5":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 5;
                                break;
                            case "zone 6":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 6;
                                break;
                            case "zone 7":
                                ExcelTitle = "Resident_List(" + group + ")" + LocalDate.now();
                                matchesGroup = resident.getPurok() == 7;
                                break;
                            default:
                                return false;
                        }
                        break;
                    case "Residency Status":

                        matchesGroup = resident.getCivilStatus().equalsIgnoreCase(group);
                        break;
                    // Handle other categories as needed
                    default:
                        ExcelTitle = "Resident_List" + LocalDate.now();
                        return true; // Return true for "All" category
                }
            }
            return matchesSearchText && matchesGroup;
        });
        // Set the sorted data to the TableView
        ResidentInfoTableview.setItems(filteredList);

    }

    public ObservableList<Resident> residentList() {

        String baseQuery = "SELECT * FROM `resident` ORDER BY last_name ASC";

        ObservableList listData = FXCollections.observableArrayList();

        String selectData = baseQuery;

        connect = Connect.connectDB();

        try {

            if (connect == null) {
                return listData; // Return an empty list
            }

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            Resident residentDatas;

            while (result.next()) {

                residentDatas = new Resident(result.getInt("resident_id"), result.getString("first_name"), result.getString("middle_name"),
                        result.getString("last_name"), result.getString("suffix"), result.getString("gender"), result.getInt("age"),
                        result.getDate("birth_date"), result.getString("religion"), result.getString("civil_status"),
                        result.getString("education_attainment"), result.getString("occupation"),
                        result.getInt("personal_income"), result.getString("with_disability"), result.getInt("zone"),
                        result.getLong("phone_num"), result.getString("email"), result.getInt("barangay_id"), result.getString("voter_status"),
                        result.getString("nationality"), result.getString("blood_type"), result.getInt("living_duration"),
                        result.getString("birth_place"), result.getInt("household_id"), result.getString("relation_to_family_head"),
                        result.getInt("height"), result.getInt("weight"), result.getString("mother's_name"), result.getLong("mother's_phone_num"),
                        result.getString("father's_name"), result.getLong("father's_phone_num"), result.getString("status"), result.getString("source_of_income"));

                listData.add(residentDatas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return listData;
    }

    public ObservableList<Resident> residentData;

    public void userShowData() {

        residentData = residentList();

        System.out.println("Resident Data Size: " + residentData.size());

        rowNo.setCellValueFactory(new PropertyValueFactory<>("residentID"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableColumnPhoneNo.setCellValueFactory(new PropertyValueFactory<>("FormattedPhoneNo"));
        tableColumnBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        personalIncome.setCellValueFactory(new PropertyValueFactory<>("formattedPersonalIncome"));
        tableColumnPurok.setCellValueFactory(new PropertyValueFactory<>("purok"));
        tableColumnHouseholdNo.setCellValueFactory(new PropertyValueFactory<>("householdNo"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("inOutBarangay"));

        // Set the sorted data to the table view
        ResidentInfoTableview.setItems(residentData);

        searchBar.textProperty().addListener((Observable, oldValue, newValue) -> {
            searchTable(newValue);
        });
    }

    @FXML
    public int selectResident() {

        int selectedResidentInt = ResidentInfoTableview.getSelectionModel().getSelectedIndex();

        ResidentInfoTableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

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
                        ResidentUpdateFormController.selectedResident = selectedResident2clicks;
                        ResidentInformationWindowController.selectedResident = selectedResident2clicks;

                        FXMLLoader infoWindowLoader = new FXMLLoader(getClass().getResource("ResidentInformationWindow.fxml"));
                        Parent Root = infoWindowLoader.load();
                        ResidentInformationWindowController informationWindow = infoWindowLoader.getController();
                        informationWindow.setResidentRecord(this);

                        FXMLLoader updateFormLoader = new FXMLLoader(getClass().getResource("updateForm.fxml"));
                        Parent updateFormRoot = updateFormLoader.load();
                        ResidentUpdateFormController updateForm = updateFormLoader.getController();
                        updateForm.setRecordController(this);

                        //load the stage for the resident information Preview
                        Stage stage = new Stage();
                        stage.setTitle("Update Resident Information");
                        stage.setScene(new Scene(Root));

                        stage.show(); //show the stage

                        event.consume();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if ((selectedResidentInt - 1) > -1) {
            return selectedResidentInt;
        }

        return -1;

    }

    public void updateResidentStatus(Resident selected) {
        connect = Connect.connectDB();
        String key = selected.getfName() + selected.getmName() + selected.getlName();
        try {
            prepare = connect.prepareStatement("SELECT `resident_id`, `status` FROM `resident` WHERE CONCAT(first_name, middle_name, last_name) = ?");
            prepare.setString(1, key);
            result = prepare.executeQuery();

            while (result.next()) {
                int residentId = result.getInt(1);
                String status = result.getString(2);
                String newStatus;

                if ("Resident".equals(status)) {
                    newStatus = "Non-resident";
                } else {
                    newStatus = "Resident";
                }

                // Update the status of the resident
                prepare = connect.prepareStatement("UPDATE `resident` SET status = ? WHERE resident_id = ?");
                prepare.setString(1, newStatus);
                prepare.setInt(2, residentId);
                prepare.executeUpdate();

            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentRecordController.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close resources (result, prepare, connect) in a finally block
        }
    }

    public void updateTableView() {
        residentData.clear();
        residentData.addAll(residentList());
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

    @FXML
    private void exportResidentList(ActionEvent event) {

        try {

            exportTable(ResidentInfoTableview);
        } catch (IOException ex) {

            // Handle any exceptions that occur during the export process
            System.out.println("Couldn't export table data.");
            throw new RuntimeException(ex);

        }
    }

    private <T> void exportToExcel(TableView<T> tableView, String filePath) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet();

            ObservableList<TableColumn<T, ?>> columns = tableView.getColumns();

            // Create header row
            Row headerRow = sheet.createRow(0);

            for (int i = 1; i < columns.size(); i++) {
                headerRow.createCell(i - 1).setCellValue(columns.get(i).getText());
            }

            // Fill data
            ObservableList<T> items = tableView.getItems();

            for (int rowIdx = 0; rowIdx < items.size(); rowIdx++) {

                Row row = sheet.createRow(rowIdx + 1);
                T item = items.get(rowIdx);

                for (int colIdx = 1; colIdx < columns.size(); colIdx++) {

                    Object cellValue = columns.get(colIdx).getCellData(item);

                    if (cellValue != null) {
                        if (colIdx < columns.size()) {
                            row.createCell(colIdx - 1).setCellValue(cellValue.toString());
                        }

                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columns.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }

    private void exportTable(TableView tableView) throws IOException {
        if (tableView.getSelectionModel() != null && !tableView.getItems().isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            fileChooser.setInitialFileName(ExcelTitle);

            File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());

            if (file != null) {
                exportToExcel(tableView, file.getAbsolutePath());
            }
        } else {
            if (Group.getSelectionModel() != null) {
                errorMessage("Group " + Group.getSelectionModel().getSelectedItem().toUpperCase() + " in Category "
                        + Category.getSelectionModel().getSelectedItem().toUpperCase() + " is Empty.");
            } else {
                errorMessage("Table is Empty.");
            }
        }
    }
}
