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


public class ResidentUpdateFormController implements Initializable {


    //initialize the resident record controller
//    private ResidentRecordViewController residentRecord ;
    static Resident selectedResident;
    private Connection connect;
    private Alert alert;
    
    @FXML
    private TextField fNameUpdateForm;
    @FXML
    private TextField mNameUpdateForm;
    @FXML
    private TextField LnameUpdateForm;
    @FXML
    private ComboBox<String> genderUpdateForm;
    @FXML
    private TextField AgeUpdateForm;
    @FXML
    private ComboBox<String> civilStatusUpdateForm;
    @FXML
    private ComboBox<String> religionUpdateForm;
    @FXML
    private ComboBox<String> inOutSchoolUpdateForm;
    @FXML
    private ComboBox<String> educAttainmentUpdateForm;
    @FXML
    private ComboBox<String> occupationUpdateForm;
    @FXML
    private ComboBox<String> laborForceUpdateForm;
    @FXML
    private ComboBox<String> pregnantUpdateForm;
    @FXML
    private ComboBox<String> nursingMotherUpdateForm;
    @FXML
    private ComboBox<String> familyPlanningUpdateForm;
    @FXML
    private ComboBox<String> DisabilityUpdateForm;
    @FXML
    private TextField suffixUpdateForm;
    @FXML
    private TextField purokUpdateForm;
    @FXML
    private TextField houseHoldNoUpdateForm;
    @FXML
    private DatePicker birthdateUpdateForm;
    @FXML
    private Button addBtn;
    private ResidentRecordController residentRecord;
    
    public void setCRUDForm(ResidentRecordController controller) {
        this.residentRecord = controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxList(gender, genderUpdateForm);
        comboboxList(civilStatus, civilStatusUpdateForm);
        comboboxList(religion, religionUpdateForm);
        comboboxList(inOutSchool, inOutSchoolUpdateForm);
        comboboxList(educAttainment, educAttainmentUpdateForm);
        comboboxList(occupation, occupationUpdateForm);
        comboboxList(laborForce, laborForceUpdateForm);
        comboboxList(pregnant, pregnantUpdateForm);
        comboboxList(nursingMother, nursingMotherUpdateForm);
        comboboxList(practiceFamilyPlanning, familyPlanningUpdateForm);
        comboboxList(patientWithDisability, DisabilityUpdateForm);
        
        if (selectedResident != null) {
            fillTextfield();
        }

    }
    @FXML
    public void updateResident(ActionEvent event){
        connect = Connect.connectDB();
       
        
        try {
            if (fNameUpdateForm.getText().isEmpty()
                || mNameUpdateForm.getText().isEmpty()
                || LnameUpdateForm.getText().isEmpty()
                || AgeUpdateForm.getText().isEmpty()
                || birthdateUpdateForm.getValue() == null
                || genderUpdateForm.getSelectionModel().isEmpty()
                || civilStatusUpdateForm.getSelectionModel().isEmpty()
                || religionUpdateForm.getSelectionModel().isEmpty()
                || inOutSchoolUpdateForm.getSelectionModel().isEmpty()
                || educAttainmentUpdateForm.getSelectionModel().isEmpty()
                || occupationUpdateForm.getSelectionModel().isEmpty()
                || laborForceUpdateForm.getSelectionModel().isEmpty()
                || pregnantUpdateForm.getSelectionModel().isEmpty()
                || nursingMotherUpdateForm.getSelectionModel().isEmpty()
                || familyPlanningUpdateForm.getSelectionModel().isEmpty()
                || DisabilityUpdateForm.getSelectionModel().isEmpty()
                || purokUpdateForm.getText().isEmpty()
                || houseHoldNoUpdateForm.getText().isEmpty()) {
            errorMessage("Please fill all the blank fields");
        }else{
                
                
               String updateResident = "UPDATE resident_informations SET fname = ?, mname = ?, lname = ?, suffix = ?, gender = ?, age = ?, birthdate = ?," +
                                        " civil_status = ?, religion = ?, in_out_school = ?, educ_attainment = ?, occupation = ?, " +
                                        " labor_force = ?, pregnant = ?, nursing_mother = ?, family_planning = ?," +
                                        " patient_with_disability = ?, purok = ?, household_no = ?" +
                                        " WHERE fname = " + selectedResident.getfName()+ ", mname = "+ selectedResident.getmName() +", lname = "+selectedResident.getlName();
                
               try (PreparedStatement insertPrepare = connect.prepareStatement(updateResident)) {
                            insertPrepare.setString(1, fNameUpdateForm.getText());
                            insertPrepare.setString(2, mNameUpdateForm.getText());
                            insertPrepare.setString(3, LnameUpdateForm.getText());
                            insertPrepare.setString(4, suffixUpdateForm.getText());
                            insertPrepare.setString(5, (String) genderUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(6,  Integer.parseInt(AgeUpdateForm.getText()));
                            insertPrepare.setDate(7, java.sql.Date.valueOf(birthdateUpdateForm.getValue()));
                            insertPrepare.setString(8, (String) civilStatusUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(9, (String) religionUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(10, (String) inOutSchoolUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(11, (String) educAttainmentUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(12, (String) occupationUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(13, (String) laborForceUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(14, (String) pregnantUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(15, (String) nursingMotherUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(16, (String) familyPlanningUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setString(17, (String) DisabilityUpdateForm.getSelectionModel().getSelectedItem());
                            insertPrepare.setInt(18,Integer.parseInt(purokUpdateForm.getText()));
                            insertPrepare.setInt(19,Integer.parseInt(houseHoldNoUpdateForm.getText()));

                            insertPrepare.executeUpdate();
//                            residentRecord.updateTableView();
                            Stage stage = (Stage) addBtn.getScene().getWindow();
                            stage.close();
                            successMessage("Successfully added the resident to the record.");
                        }
               
            }
        } catch (NumberFormatException e) {
            errorMessage("Please put numbers on Age, household, and purok");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

    public void fillTextfield() {
        fNameUpdateForm.setText(selectedResident.getfName());
        mNameUpdateForm.setText(selectedResident.getmName());
        LnameUpdateForm.setText(selectedResident.getlName());
        suffixUpdateForm.setText(selectedResident.getSuffix());
        genderUpdateForm.setValue(selectedResident.getGender());
        AgeUpdateForm.setText(String.valueOf(selectedResident.getAge()));
        //Date
        civilStatusUpdateForm.setValue(selectedResident.getCivilStatus());
        religionUpdateForm.setValue(selectedResident.getReligion());
        inOutSchoolUpdateForm.setValue(selectedResident.getInOutSchool());
        educAttainmentUpdateForm.setValue(selectedResident.getEducAttainment());
        occupationUpdateForm.setValue(selectedResident.getOccupation());
        laborForceUpdateForm.setValue(selectedResident.getLaborForce());
        pregnantUpdateForm.setValue(selectedResident.getIspregnant());
        nursingMotherUpdateForm.setValue(selectedResident.getNursingMother());
        familyPlanningUpdateForm.setValue(selectedResident.getFamilyPlanning());
        DisabilityUpdateForm.setValue(selectedResident.getDisability());
        purokUpdateForm.setText(String.valueOf(selectedResident.getPurok()));
        houseHoldNoUpdateForm.setText(String.valueOf(selectedResident.getHousehold()));

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
