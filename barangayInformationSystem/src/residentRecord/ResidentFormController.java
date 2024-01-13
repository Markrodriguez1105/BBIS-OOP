package residentRecord;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResidentFormController implements Initializable {

    @FXML
    private TextField fNameForm;
    @FXML
    private TextField mNameForm;
    @FXML
    private TextField LnameForm;
    @FXML
    private ComboBox<String> genderForm;
    @FXML
    private TextField AgeForm;
    @FXML
    private DatePicker birthdateForm;
    @FXML
    private Button addBtn;
    @FXML
    private ComboBox<String> civilStatusForm;
    @FXML
    private ComboBox<String> religionForm;
    @FXML
    private ComboBox<String> inOutSchoolForm;
    @FXML
    private ComboBox<String> educAttainmentForm;
    @FXML
    private ComboBox<String> occupationForm;
    @FXML
    private ComboBox<String> laborForceForm;
    @FXML
    private ComboBox<String> pregnantForm;
    @FXML
    private ComboBox<String> nursingMotherForm;
    @FXML
    private ComboBox<String> familyPlanningForm;
    @FXML
    private ComboBox<String> DisabilityForm;
    @FXML
    private TextField suffixForm;
    @FXML
    private TextField purokForm;
    @FXML
    private TextField houseHoldNoForm;

    //
    //initialize the resident record controller
    private ResidentRecordController residentRecord;
    
    static Resident selectedResident;
    private Connection connect;
    private Alert alert;

    public void setCRUDForm(ResidentRecordController controller) {
        this.residentRecord = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxList(gender, genderForm);
        comboboxList(civilStatus, civilStatusForm);
        comboboxList(religion, religionForm);
        comboboxList(inOutSchool, inOutSchoolForm);
        comboboxList(educAttainment, educAttainmentForm);
        comboboxList(occupation, occupationForm);
        comboboxList(laborForce, laborForceForm);
        comboboxList(pregnant, pregnantForm);
        comboboxList(nursingMother, nursingMotherForm);
        comboboxList(practiceFamilyPlanning, familyPlanningForm);
        comboboxList(patientWithDisability, DisabilityForm);

    }

    @FXML
    private void addResident(ActionEvent event) {
        connect = Connect.connectDB();
        if (fNameForm.getText().isEmpty()
                || mNameForm.getText().isEmpty()
                || LnameForm.getText().isEmpty()
                || AgeForm.getText().isEmpty()
                || birthdateForm.getValue() == null
                || genderForm.getSelectionModel().isEmpty()
                || civilStatusForm.getSelectionModel().isEmpty()
                || religionForm.getSelectionModel().isEmpty()
                || inOutSchoolForm.getSelectionModel().isEmpty()
                || educAttainmentForm.getSelectionModel().isEmpty()
                || occupationForm.getSelectionModel().isEmpty()
                || laborForceForm.getSelectionModel().isEmpty()
                || pregnantForm.getSelectionModel().isEmpty()
                || nursingMotherForm.getSelectionModel().isEmpty()
                || familyPlanningForm.getSelectionModel().isEmpty()
                || DisabilityForm.getSelectionModel().isEmpty()
                || purokForm.getText().isEmpty()
                || houseHoldNoForm.getText().isEmpty()) {
            errorMessage("Please fill all the blank fields");
        } else {
            System.out.println("Connected to the database");

            String checkData = "SELECT fname, mname, lname FROM resident_informations WHERE fname = ? AND mname = ? AND lname = ?";
            try (PreparedStatement prepare = connect.prepareStatement(checkData)) {
                prepare.setString(1, fNameForm.getText());
                prepare.setString(2, mNameForm.getText());
                prepare.setString(3, LnameForm.getText());
                try (ResultSet result = prepare.executeQuery()) {
                    if (result.next()) {
                        successMessage(LnameForm.getText() + ", " + fNameForm.getText() + " " + mNameForm.getText() + " is already taken.");
                    } else {
                        String insertData = "INSERT INTO resident_informations (fname, mname, lname, suffix, gender, age, birthdate,"
                                + " civil_status, religion, in_out_school, educ_attainment, occupation, labor_force, pregnant, "
                                + "nursing_mother, family_planning, patient_with_disability, purok, household_no) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertPrepare = connect.prepareStatement(insertData)) {
                            insertPrepare.setString(1, fNameForm.getText());
                            insertPrepare.setString(2, mNameForm.getText());
                            insertPrepare.setString(3, LnameForm.getText());
                            insertPrepare.setString(4, suffixForm.getText());
                            insertPrepare.setString(5, (String) genderForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(6, Integer.parseInt(AgeForm.getText()));
                            insertPrepare.setDate(7, java.sql.Date.valueOf(birthdateForm.getValue()));
                            insertPrepare.setString(8, (String) civilStatusForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(9, (String) religionForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(10, (String) inOutSchoolForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(11, (String) educAttainmentForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(12, (String) occupationForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(13, (String) laborForceForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(14, (String) pregnantForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(15, (String) nursingMotherForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(16, (String) familyPlanningForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(17, (String) DisabilityForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(18, Integer.parseInt(purokForm.getText()));
                            insertPrepare.setInt(19, Integer.parseInt(houseHoldNoForm.getText()));

                            insertPrepare.executeUpdate();
                          residentRecord.updateTableView();
                            Stage stage = (Stage) addBtn.getScene().getWindow();
                            stage.close();
                            successMessage("Successfully added the resident to the record.");
                        }
                    }
                }
            } catch (SQLException e) {
                // Handle SQLException

            } catch (NumberFormatException e) {
                errorMessage("Please enter a valid age");
            }
        }
    }

    //setting the items of combo boxes 
    //  items for gender
    private final String[] gender = {"Male", "Female"};
    private final String[] civilStatus = {"Single", "Legally Married", "Widowed", "Divorced/Separated", "Common Love/Live In"};
    private final String[] religion = {"Catholic", "Protestant", "Inglesia ni kristo", "Aglipay", "Islam"};
    private final String[] inOutSchool = {"In School", "Out of School"};
    private final String[] educAttainment = {"Elementary Level", "Elementary Graduate", "HighSchool Level", "HighSchool Graduate",
        "College Level", "College Graduate", "Post Graduate", "Vocational"};
    private final String[] occupation = {"Vendor", "Laborer"};
    private final String[] laborForce = {"14-17 Male", "14-17 Female", "18-60 Male", "18-60 Female", "None"};
    private final String[] pregnant = {"Yes", "No"};
    private final String[] nursingMother = {"Yes", "No"};
    private final String[] practiceFamilyPlanning = {"No"};
    private final String[] patientWithDisability = {"No"};

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

}
