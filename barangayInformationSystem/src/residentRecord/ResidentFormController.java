package residentRecord;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static residentRecord.DataValidator.validateEmail;

public class ResidentFormController implements Initializable {

    @FXML
    private TextField resident_idForm;
    @FXML
    private TextField fnameForm;
    @FXML
    private TextField mnameForm;
    @FXML
    private TextField lnameForm;
    @FXML
    private TextField suffixForm;
    @FXML
    private DatePicker bdateForm;
    @FXML
    private TextField ageForm;
    @FXML
    private TextField nationalityForm;
    @FXML
    private ComboBox<String> civilStatusForm;
    @FXML
    private TextField birthPlaceForm;
    @FXML
    private TextField emailForm;
    @FXML
    private TextField phoneNumForm;
    @FXML
    private ComboBox<String> voterStatusForm;
    @FXML
    private TextField religionForm;
    @FXML
    private ComboBox<String> householdForm;
    @FXML
    private ComboBox<String> bloodTypeForm;
    @FXML
    private TextField zoneForm;
    @FXML
    private TextField occupationForm;
    @FXML
    private TextField livingDurationForm;
    @FXML
    private TextField relationshipToHeadForm;
    @FXML
    private TextField perIncomeForm;
    @FXML
    private TextField weightForm;
    @FXML
    private TextField heightForm;
    @FXML
    private ComboBox<String> withDisabilityForm;
    @FXML
    private ComboBox<String> educationAttForm;
    @FXML
    private TextField motherNameForm;
    @FXML
    private TextField motherNumForm;
    @FXML
    private TextField fatherNameForm;
    @FXML
    private TextField fatherNumForm;
    @FXML
    private Button addBtn;
    @FXML
    private ToggleGroup genderForm;
    @FXML
    private TextField addressForm;
    @FXML
    private Button cancelBtn;

    private ResidentRecordController residentRecord;

    public static int householdIndex;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    @FXML
    private TextField sourceIncomeForm;
    @FXML
    private CheckBox residencyStatusForm;

    public void setCRUDForm(ResidentRecordController controller) {
        this.residentRecord = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Initializing ComboBox
        comboboxList(civilStatusList, civilStatusForm);
        comboboxList(bloodTypeList, bloodTypeForm);
        comboboxList(educAttainmentList, educationAttForm);

        //choices for with voterStatus combobox
        voterStatusForm.getItems().add("Registered");
        voterStatusForm.getItems().add("Not Registered");
        //choices for with disability combobox
        withDisabilityForm.getItems().add("Yes");
        withDisabilityForm.getItems().add("No");

        residencyStatusForm.setSelected(true);

        DataValidator.numbersOnlyTextfields(resident_idForm, phoneNumForm, zoneForm, livingDurationForm, weightForm,
                motherNumForm, phoneNumForm, perIncomeForm);

        DataValidator.textOnlyTextfields(fnameForm, mnameForm, lnameForm, nationalityForm,
                religionForm, occupationForm, relationshipToHeadForm, motherNameForm,
                fatherNameForm);

        //restrict user for putting more than 11 digits and validate if the phone no. is actually a phone number
        DataValidator.validatePhoneNumber(phoneNumForm);
        DataValidator.validatePhoneNumber(motherNumForm);
        DataValidator.validatePhoneNumber(fatherNumForm);

        DataValidator.restrictNumberInputlenght(zoneForm, 1, 8, 1);
        DataValidator.restrictNumberInputlenght(livingDurationForm, 1, 100, 3);
        DataValidator.restrictNumberInputLength(heightForm, 1.0, 304.8, 6);
        DataValidator.restrictNumberInputlenght(weightForm, 1, 300, 3);
        DataValidator.restrictNumberInputlenght(perIncomeForm, 1, 1000000, 7);

        //disable days that is before the current day
        DataValidator.restrictInvalidBirthDate(bdateForm);

        DataValidator.restrictInvalidBirthDate(bdateForm);
        //change calculate the value automatically after the birhtdate is set
        bdateForm.valueProperty().addListener((obs, oldVal, newVal) -> {
            DataValidator.calculateAge(bdateForm, ageForm);
        });

        emailForm.textProperty().addListener((observable, oldValue, newValue) -> {
            validateEmail(newValue, emailForm);
        });
        showHouseholdHeadList();
    }

    @FXML
    private void addResident(ActionEvent event) {
        connect = Connect.connectDB();
        if (areAnyFieldsEmpty(
                resident_idForm, fnameForm, lnameForm, ageForm, bdateForm, civilStatusForm,
                religionForm, educationAttForm, occupationForm, withDisabilityForm, zoneForm,
                householdForm, relationshipToHeadForm, voterStatusForm, bloodTypeForm, zoneForm, occupationForm,
                perIncomeForm, livingDurationForm, birthPlaceForm, weightForm, heightForm, motherNameForm,
                fatherNameForm)) {

            errorMessage("Please fill all the blank fields");
        } else {
            System.out.println("Connected to the database");

            String checkData = "SELECT resident_id FROM resident WHERE resident_id = ?";
            try (PreparedStatement prepared = connect.prepareStatement(checkData)) {
                prepared.setInt(1, Integer.parseInt(resident_idForm.getText()));
                try (ResultSet results = prepared.executeQuery()) {
                    if (results.next()) {
                        errorMessage(resident_idForm.getText() + " is already taken.");
                    } else {
                        String insertData = "INSERT INTO resident(resident_id, first_name, middle_name, last_name, suffix,"
                                + " age, phone_num, email, birth_date, gender, zone, barangay_id, voter_status, "
                                + "nationality, religion, blood_type, occupation, living_duration, birth_place, "
                                + "civil_status, household_id, relation_to_family_head, weight, height, education_attainment,"
                                + " source_of_income, with_disability, personal_income, `mother's_name`, `mother's_phone_num`, "
                                + "`father's_name`, `father's_phone_num`, status) "
                                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        try (PreparedStatement insertPrepare = connect.prepareStatement(insertData)) {
                            insertPrepare.setInt(1, Integer.parseInt(resident_idForm.getText()));
                            insertPrepare.setString(2, fnameForm.getText());
                            insertPrepare.setString(3, mnameForm.getText());
                            insertPrepare.setString(4, lnameForm.getText());
                            insertPrepare.setString(5, suffixForm.getText());
                            insertPrepare.setInt(6, Integer.parseInt(ageForm.getText()));
                            insertPrepare.setLong(7, Long.parseLong(phoneNumForm.getText()));
                            insertPrepare.setString(8, emailForm.getText());
                            insertPrepare.setDate(9, java.sql.Date.valueOf(bdateForm.getValue()));
                            insertPrepare.setString(10, ((RadioButton) genderForm.getSelectedToggle()).getText());
                            insertPrepare.setInt(11, Integer.parseInt(zoneForm.getText()));
                            insertPrepare.setInt(12, 101);
                            insertPrepare.setString(13, voterStatusForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(14, nationalityForm.getText());
                            insertPrepare.setString(15, religionForm.getText());
                            insertPrepare.setString(16, bloodTypeForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(17, occupationForm.getText());
                            insertPrepare.setInt(18, Integer.parseInt(livingDurationForm.getText()));
                            insertPrepare.setString(19, birthPlaceForm.getText());
                            insertPrepare.setString(20, civilStatusForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(21, getHouseholdID(HeadInfoList()));
                            insertPrepare.setString(22, relationshipToHeadForm.getText());
                            insertPrepare.setDouble(23, Double.parseDouble(weightForm.getText()));
                            insertPrepare.setDouble(24, Double.parseDouble(heightForm.getText()));
                            insertPrepare.setString(25, educationAttForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(26, sourceIncomeForm.getText());
                            insertPrepare.setString(27, withDisabilityForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(28, Integer.parseInt(perIncomeForm.getText()));
                            insertPrepare.setString(29, motherNameForm.getText());
                            insertPrepare.setLong(30, Long.parseLong(motherNumForm.getText()));
                            insertPrepare.setString(31, fatherNameForm.getText());
                            insertPrepare.setLong(32, Long.parseLong(fatherNumForm.getText()));
                            if (residencyStatusForm.isSelected()) {
                                insertPrepare.setString(33, residencyStatusForm.getText());
                            } else {
                                insertPrepare.setString(33, "Non-resident");
                            }

                            insertPrepare.executeUpdate();

                            residentRecord.updateTableView();
                            Stage stage = (Stage) addBtn.getScene().getWindow();
                            stage.close();
                            successMessage("Successfully updated the resident to the record.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to leave?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {

            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }

    }

    //setting the items of combo boxes 
    private final String[] civilStatusList = {"Single", "Legally Married", "Widowed", "Divorced/Separated", "Common Love/Live In"};
    private final String[] educAttainmentList = {"Elementary Undergraduate", "Elementary Graduate", "HighSchool Undergraduate", "HighSchool Graduate",
        "College Undergraduate", "College Graduate", "Post Graduate", "Vocational"};
    private final String[] bloodTypeList = {"A", "B", "AB", "O", "A Rh+", "A Rh-", "B Rh+", "B Rh-", "AB Rh+", "AB Rh-", "O Rh+", "O Rh-"};

    //dialogue for a unsucessful action
    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
//    dialogue for a successful action

    private void successMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void comboboxList(String[] stringArray, ComboBox<String> combobox) {
        List<String> list = new ArrayList<>();

        list.addAll(Arrays.asList(stringArray));

        ObservableList listData = FXCollections.observableList(list);
        combobox.setItems(listData);
    }

    private boolean areAnyFieldsEmpty(Control... controls) {
        for (Control control : controls) {
            if (control instanceof TextField) {
                if (((TextField) control).getText() == null || ((TextField) control).getText().trim().isEmpty()) {

                    ((TextField) control).setStyle("-fx-border-color: red;");
                    return true;
                }
            } else if (control instanceof DatePicker) {
                if (((DatePicker) control).getValue() == null) {
                    ((DatePicker) control).setStyle("-fx-border-color: red;");
                    return true;
                }
            } else if (control instanceof ComboBox) {
                if (((ComboBox<?>) control).getSelectionModel().isEmpty() || ((ComboBox<?>) control).getValue() == null) {
                    ((ComboBox<?>) control).setStyle("-fx-border-color: red;");
                    return true;
                }
            } else if (control instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) control;
                if (radioButton.isSelected() && radioButton.getUserData().toString().trim().isEmpty()) {
                    (radioButton).setStyle("-fx-border-color: red;");
                    return true;
                }
            }
        }
        return false;
    }



//    private void searchComboBoxList() {
//    ObservableList<String> initialItems = FXCollections.observableArrayList(householdForm.getItems());
//
//    // Listener for the text property of the ComboBox editor
//    householdForm.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
//        if (newValue.isEmpty()) {
//            // If the text is empty, show all items
//            householdForm.setItems(initialItems);
//        } else {
//            // Filter the items based on the typed text
//            FilteredList<String> filteredList = new FilteredList<>(
//                initialItems,
//                item -> item.toUpperCase().contains(newValue.toUpperCase())
//            );
//            householdForm.setItems(filteredList);
//        }
//    });
//}



    public ObservableList<Resident> getHeadInfoList() {
        return this.HeadInfoList();
    }

    public ObservableList<Resident> HeadInfoList() {

        ObservableList<Resident> listData = FXCollections.observableArrayList();

        String selectData = "SELECT `household_id`, `first_name`, `middle_name`, `last_name` FROM `household` ORDER BY last_name ASC;";

        connect = Connect.connectDB();

        try {

            if (connect == null) {
                return listData; // Return an empty list
            }

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            Resident residentDatas;

            while (result.next()) {

                residentDatas = new Resident(result.getString("first_name"), result.getString("middle_name"), result.getString("last_name"), result.getInt("household_id"));

                listData.add(residentDatas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    ObservableList<Resident> householdHead;

    public void showHouseholdHeadList() {
        householdHead = HeadInfoList();
        ArrayList<String> residentFullname = new ArrayList<>();

        for (Resident resident : householdHead) {

            String houseNameAndNumber = resident.getHouseholdFullName() + " " + resident.getHouseholdNo();

            residentFullname.add(houseNameAndNumber);
            ObservableList listData = FXCollections.observableList(residentFullname);
            householdForm.setItems(listData);
        }

    }

    public int getHouseholdID(ObservableList<Resident> householdList) {

        for (Resident resident : householdList) {

            if (householdForm.getSelectionModel().getSelectedItem().equals(resident.getHouseholdFullName() + " " + resident.getHouseholdNo())) {
                householdIndex = householdForm.getSelectionModel().getSelectedIndex();
                return resident.getHouseholdNo();
            }
        }

        return -1;
    }

}
