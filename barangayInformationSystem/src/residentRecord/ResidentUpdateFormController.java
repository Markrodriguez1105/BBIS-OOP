package residentRecord;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import static residentRecord.DataValidator.validateEmail;

public class ResidentUpdateFormController implements Initializable {

    //initialize the resident record controller
    private ResidentRecordController residentRecord;
    static Resident selectedResident;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    @FXML
    private ToggleGroup genderUpdateForm;
    @FXML
    private ComboBox<String> civilStatusUpdateForm;
    @FXML
    private TextField religionUpdateForm;
    @FXML
    private TextField suffixUpdateForm;
    @FXML
    private Button addBtn;
    @FXML
    private TextField resident_idUpdateForm;
    @FXML
    private TextField fnameUpdateForm;
    @FXML
    private TextField mnameUpdateForm;
    @FXML
    private TextField lnameUpdateForm;
    @FXML
    private DatePicker bdateUpdateForm;
    @FXML
    private TextField ageUpdateForm;
    @FXML
    private TextField nationalityUpdateForm;
    @FXML
    private TextField birthPlaceUpdateForm;
    @FXML
    private TextField emailUpdateForm;
    @FXML
    private TextField phoneNumUpdateForm;
    @FXML
    private ComboBox<String> voterStatusUpdateForm;
    @FXML
    private ComboBox<String> householdUpdateForm;
    @FXML
    private ComboBox<String> bloodTypeUpdateForm;
    @FXML
    private TextField zoneUpdateForm;
    @FXML
    private TextField addressUpdateForm;
    @FXML
    private TextField livingDurationUpdateForm;
    @FXML
    private TextField relationshipToHeadUpdateForm;
    @FXML
    private TextField perIncomeUpdateForm;
    @FXML
    private TextField weightUpdateForm;
    @FXML
    private TextField heightUpdateForm;
    @FXML
    private ComboBox<String> withDisabilityUpdateForm;
    @FXML
    private ComboBox<String> educationAttUpdateForm;
    @FXML
    private TextField motherNameUpdateForm;
    @FXML
    private TextField motherNumUpdateForm;
    @FXML
    private TextField fatherNameUpdateForm;
    @FXML
    private TextField fatherNumUpdateForm;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField occupationUpdateForm;
    @FXML
    private TextField sourceIncomeUpdateForm;
    @FXML
    private CheckBox residencyStatusUpdateForm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxList(civilStatusList, civilStatusUpdateForm);
        comboboxList(bloodTypeList, bloodTypeUpdateForm);
        comboboxList(educAttainmentList, educationAttUpdateForm);

        //choices for with voterStatus combobox
        voterStatusUpdateForm.getItems().add("Registered");
        voterStatusUpdateForm.getItems().add("Not Registered");
        //choices for with disability combobox
        withDisabilityUpdateForm.getItems().add("Yes");
        withDisabilityUpdateForm.getItems().add("No");

        DataValidator.numbersOnlyTextfields(resident_idUpdateForm, phoneNumUpdateForm, zoneUpdateForm, livingDurationUpdateForm, weightUpdateForm,
                motherNumUpdateForm, phoneNumUpdateForm, perIncomeUpdateForm);

        DataValidator.textOnlyTextfields(fnameUpdateForm, mnameUpdateForm, lnameUpdateForm, nationalityUpdateForm,
                religionUpdateForm, occupationUpdateForm, relationshipToHeadUpdateForm, motherNameUpdateForm,
                fatherNameUpdateForm);

        //restrict user for putting more than 11 digits and validate if the phone no. is actually a phone number
        DataValidator.validatePhoneNumber(phoneNumUpdateForm);
        DataValidator.validatePhoneNumber(motherNumUpdateForm);
        DataValidator.validatePhoneNumber(fatherNumUpdateForm);

        DataValidator.restrictNumberInputlenght(zoneUpdateForm, 1, 8, 1);
        DataValidator.restrictNumberInputlenght(livingDurationUpdateForm, 1, 100, 3);
        DataValidator.restrictNumberInputLength(heightUpdateForm, 1.0, 304.8, 6);
        DataValidator.restrictNumberInputlenght(weightUpdateForm, 1, 300, 3);
        DataValidator.restrictNumberInputLength(perIncomeUpdateForm, 1, 1000000, 7);
        //disable days that is before the current day
        DataValidator.restrictInvalidBirthDate(bdateUpdateForm);

        //change calculate the value automatically after the birhtdate is set
        bdateUpdateForm.valueProperty().addListener((obs, oldVal, newVal) -> {
            DataValidator.calculateAge(bdateUpdateForm, ageUpdateForm);
        });

        emailUpdateForm.textProperty().addListener((observable, oldValue, newValue) -> {
            validateEmail(newValue, emailUpdateForm);
        });

        showHouseholdHeadList();

        if (selectedResident != null) {
            fillTextfield();
            if ("Resident".equals(selectedResident.getInOutBarangay())) {
                residencyStatusUpdateForm.setSelected(true);
            }
        }
    }

    public void setRecordController(ResidentRecordController controller) {
        this.residentRecord = controller;
    }

    public void setSelectedResident(Resident selectedResident) {
        ResidentUpdateFormController.selectedResident = selectedResident;
        fillTextfield(); // Populate the form fields with the resident's information
    }

    @FXML
    public void updateResident(ActionEvent event) {
        connect = Connect.connectDB();

        try {

            if (areAnyFieldsEmpty(
                    resident_idUpdateForm, fnameUpdateForm, lnameUpdateForm, ageUpdateForm, bdateUpdateForm, civilStatusUpdateForm,
                    religionUpdateForm, educationAttUpdateForm, occupationUpdateForm, withDisabilityUpdateForm, zoneUpdateForm,
                    householdUpdateForm, relationshipToHeadUpdateForm, voterStatusUpdateForm, bloodTypeUpdateForm, zoneUpdateForm, occupationUpdateForm,
                    perIncomeUpdateForm, livingDurationUpdateForm, birthPlaceUpdateForm, weightUpdateForm, heightUpdateForm, motherNameUpdateForm,
                    fatherNameUpdateForm)) {

                errorMessage("Please fill all the blank fields");
            } else {

                String updateResident = "UPDATE resident SET first_name = ?, middle_name = ?, last_name = ?, suffix = ?,"
                        + " age = ?, phone_num = ?, email = ?, birth_date = ?, gender = ?, zone = ?, barangay_id = ?, voter_status = ?, "
                        + "nationality = ?, religion = ?, blood_type = ?, occupation = ?, living_duration = ?, birth_place = ?, "
                        + "civil_status = ?, household_id = ?, relation_to_family_head = ?, weight = ?, height = ?, education_attainment = ?,"
                        + " source_of_income = ?, with_disability = ?, personal_income = ?, `mother's_name` = ?, `mother's_phone_num` = ?, "
                        + "`father's_name` = ?, `father's_phone_num` = ?, status = ? WHERE resident_id = " + selectedResident.getResidentID();

                try (PreparedStatement insertPrepare = connect.prepareStatement(updateResident)) {

                    insertPrepare.setString(1, fnameUpdateForm.getText());
                    insertPrepare.setString(2, mnameUpdateForm.getText());
                    insertPrepare.setString(3, lnameUpdateForm.getText());
                    insertPrepare.setString(4, suffixUpdateForm.getText());
                    insertPrepare.setInt(5, Integer.parseInt(ageUpdateForm.getText()));
                    insertPrepare.setLong(6, Long.parseLong(phoneNumUpdateForm.getText()));
                    insertPrepare.setString(7, emailUpdateForm.getText());
                    insertPrepare.setDate(8, java.sql.Date.valueOf(bdateUpdateForm.getValue()));
                    insertPrepare.setString(9, ((RadioButton) genderUpdateForm.getSelectedToggle()).getText());
                    insertPrepare.setInt(10, Integer.parseInt(zoneUpdateForm.getText()));
                    insertPrepare.setInt(11, 101);
                    insertPrepare.setString(12, voterStatusUpdateForm.getSelectionModel().getSelectedItem());
                    insertPrepare.setString(13, nationalityUpdateForm.getText());
                    insertPrepare.setString(14, religionUpdateForm.getText());
                    insertPrepare.setString(15, bloodTypeUpdateForm.getSelectionModel().getSelectedItem());
                    insertPrepare.setString(16, occupationUpdateForm.getText());
                    insertPrepare.setInt(17, Integer.parseInt(livingDurationUpdateForm.getText()));
                    insertPrepare.setString(18, birthPlaceUpdateForm.getText());
                    insertPrepare.setString(19, civilStatusUpdateForm.getSelectionModel().getSelectedItem());
                    insertPrepare.setInt(20, getHouseholdID(HeadInfoList()));
                    insertPrepare.setString(21, relationshipToHeadUpdateForm.getText());
                    insertPrepare.setDouble(22, Double.parseDouble(weightUpdateForm.getText()));
                    insertPrepare.setDouble(23, Double.parseDouble(heightUpdateForm.getText()));
                    insertPrepare.setString(24, educationAttUpdateForm.getSelectionModel().getSelectedItem());
                    insertPrepare.setString(25, sourceIncomeUpdateForm.getText());
                    insertPrepare.setString(26, withDisabilityUpdateForm.getSelectionModel().getSelectedItem());
                    insertPrepare.setInt(27, Integer.parseInt(perIncomeUpdateForm.getText()));
                    insertPrepare.setString(28, motherNameUpdateForm.getText());
                    insertPrepare.setLong(29, Long.parseLong(motherNumUpdateForm.getText()));
                    insertPrepare.setString(30, fatherNameUpdateForm.getText());
                    insertPrepare.setLong(31, Long.parseLong(fatherNumUpdateForm.getText()));
                    if (residencyStatusUpdateForm.isSelected()) {
                        insertPrepare.setString(32, residencyStatusUpdateForm.getText());
                    } else {
                        insertPrepare.setString(32, "Non-resident");
                    }

                    insertPrepare.executeUpdate();
                    residentRecord.updateTableView();
                    Stage stage = (Stage) addBtn.getScene().getWindow();
                    stage.close();
                    successMessage("Successfully updated" + selectedResident.getFullName().toUpperCase() + " informations.");

                }
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
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

    public void fillTextfield() {

        if (selectedResident != null) {
            resident_idUpdateForm.setText(String.valueOf(selectedResident.getResidentID()));
            fnameUpdateForm.setText(selectedResident.getfName());
            mnameUpdateForm.setText(selectedResident.getmName());
            lnameUpdateForm.setText(selectedResident.getlName());
            if (selectedResident.getSuffix() != null) {
                suffixUpdateForm.setText(selectedResident.getSuffix());
            } else {
                suffixUpdateForm.setText("");
            }
            ageUpdateForm.setText(String.valueOf(selectedResident.getAge()));
            phoneNumUpdateForm.setText("0" + String.valueOf(selectedResident.getPhoneNo()));
            emailUpdateForm.setText(selectedResident.getEmail());
            bdateUpdateForm.setValue(Instant.ofEpochMilli(selectedResident.getBirthdate().getTime())
                    .atZone(ZoneId.systemDefault()).toLocalDate());
            //set the value for radio buttons
            for (Toggle toggle : genderUpdateForm.getToggles()) {
                RadioButton radioButton = (RadioButton) toggle;
                if (radioButton.getText().equals(selectedResident.getGender())) {
                    radioButton.setSelected(true);
                    break;
                }
            }
            zoneUpdateForm.setText(String.valueOf(selectedResident.getPurok()));
            voterStatusUpdateForm.setValue(selectedResident.getVoterStatus());
            nationalityUpdateForm.setText(selectedResident.getNationality());
            religionUpdateForm.setText(selectedResident.getReligion());
            bloodTypeUpdateForm.setValue(selectedResident.getBloodType());
            occupationUpdateForm.setText(selectedResident.getOccupation());
            livingDurationUpdateForm.setText(String.valueOf(selectedResident.getLivingDuration()));
            birthPlaceUpdateForm.setText(selectedResident.getBirthplace());
            civilStatusUpdateForm.setValue(selectedResident.getCivilStatus());
            householdUpdateForm.setValue(String.valueOf(getHeadInfoList().get(ResidentFormController.householdIndex).getHouseholdFullName() + " " + getHeadInfoList().get(ResidentFormController.householdIndex).getHouseholdNo()));
            relationshipToHeadUpdateForm.setText(selectedResident.getRelationWithFamHead());
            weightUpdateForm.setText(String.valueOf(selectedResident.getWeight()));
            heightUpdateForm.setText(String.valueOf(selectedResident.getHeight()));
            educationAttUpdateForm.setValue(selectedResident.getEducationAttainment());
            withDisabilityUpdateForm.setValue(selectedResident.getDisability());
            perIncomeUpdateForm.setText(String.valueOf(selectedResident.getPersonalIncome()));
            motherNameUpdateForm.setText(selectedResident.getMotherName());
            motherNumUpdateForm.setText("0" + String.valueOf(selectedResident.getMotherPhoneNo()));
            fatherNameUpdateForm.setText(selectedResident.getFatherName());
            fatherNumUpdateForm.setText("0" + String.valueOf(selectedResident.getFatherPhoneNo()));
            sourceIncomeUpdateForm.setText(selectedResident.getSourceOfIncome());

        }
    }
//
// private void searchComboBoxList() {
//    List<String> houseHoldList = new ArrayList<>();
//
//    for (Resident resident : getHeadInfoList()) {
//        String fullNameAndHouseholdNo = resident.getHouseholdFullName() + " " + resident.getHouseholdNo();
//        houseHoldList.add(fullNameAndHouseholdNo);
//    }
//
//    ObservableList<String> initialItems = FXCollections.observableArrayList(houseHoldList);
//
//    AtomicBoolean settingItems = new AtomicBoolean(false);
//
//    ChangeListener<String> textChangeListener = (observable, oldValue, newValue) -> {
//        if (!settingItems.get()) {
//            settingItems.set(true);
//            if (newValue.isEmpty()) {
//                householdUpdateForm.setItems(initialItems);
//            } else {
//                FilteredList<String> filteredList = new FilteredList<>(
//                        initialItems,
//                        s -> s.toUpperCase().startsWith(newValue.toUpperCase())
//                );
//                householdUpdateForm.setItems(filteredList);
//            }
//            settingItems.set(false);
//        }
//    };
//    // Remove the listener before setting items
//    householdUpdateForm.getEditor().textProperty().removeListener(textChangeListener);
//
//    // Set the items in the ComboBox
//    Platform.runLater(() -> {
//        householdUpdateForm.setItems(initialItems);
//    });
//
//    // Reattach the listener after setting items
//    householdUpdateForm.getEditor().textProperty().addListener(textChangeListener);
//}


//    dialogue for a unsucessful action
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
                } else {
                    ((TextField) control).setStyle("-fx-border-color: blue;");
                }
            } else if (control instanceof DatePicker) {
                if (((DatePicker) control).getValue() == null) {
                    ((DatePicker) control).setStyle("-fx-border-color: red;");
                    return true;
                } else {
                    ((DatePicker) control).setStyle("-fx-border-color: blue;");
                }
            } else if (control instanceof ComboBox) {
                if (((ComboBox<?>) control).getSelectionModel().isEmpty() || ((ComboBox<?>) control).getValue() == null) {
                    ((ComboBox<?>) control).setStyle("-fx-border-color: red;");
                    return true;
                } else {
                    ((ComboBox<?>) control).setStyle("-fx-border-color: blue;");
                }
            } else if (control instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) control;
                if (radioButton.isSelected() && radioButton.getUserData().toString().trim().isEmpty()) {
                    (radioButton).setStyle("-fx-border-color: red;");
                    return true;
                } else {
                    (radioButton).setStyle("-fx-border-color: blue;");
                }
            }
        }
        return false;
    }

    private final String[] civilStatusList = {"Single", "Legally Married", "Widowed", "Divorced/Separated", "Common Love/Live In"};
    private final String[] educAttainmentList = {"Elementary Undergraduate", "Elementary Graduate", "HighSchool Undergraduate", "HighSchool Graduate",
        "College Undergraduate", "College Graduate", "Post Graduate", "Vocational"};
    private final String[] bloodTypeList = {"A", "B", "AB", "O", "A Rh+", "A Rh-", "B Rh+", "B Rh-", "AB Rh+", "AB Rh-", "O Rh+", "O Rh-"};

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
            householdUpdateForm.setItems(listData);
        }
    }

    public int getHouseholdID(ObservableList<Resident> householdList) {

        for (Resident resident : householdList) {

            if (householdUpdateForm.getSelectionModel().getSelectedItem().equals(resident.getHouseholdFullName() + " " + resident.getHouseholdNo())) {
                return resident.getHouseholdNo();
            }
        }

        return -1;
    }
}
