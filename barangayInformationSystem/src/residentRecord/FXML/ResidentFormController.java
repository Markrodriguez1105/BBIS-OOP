package residentRecord.FXML;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

public class ResidentFormController implements Initializable {

    @FXML
    private TextField fNameForm;
    @FXML
    private TextField mNameForm;
    @FXML
    private TextField LnameForm;
    @FXML
    private ComboBox<?> genderForm;
    @FXML
    private TextField AgeForm;
    @FXML
    private DatePicker birthdateForm;
    @FXML
    private Button addBtn;

    //initialize the resident record controller
    private ResidentRecordViewController residentRecord;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderList();
    }

    public void setResidentForm(ResidentRecordViewController residentRecordController) {
        this.residentRecord = residentRecordController;
    }

    @FXML
    private void addResident(ActionEvent event) {
        
        
        connect = Connect.connectDB(); // error sya since dae ko nilaag si connect class ko
        
        
        try {
             if (fNameForm.getText().isEmpty()
                    || mNameForm.getText().isEmpty()
                    || LnameForm.getText().isEmpty()
                    || AgeForm.getText().isEmpty()
                    || birthdateForm.getValue() == null
                    || genderForm.getSelectionModel() == null) {

                errorMessage("Please fill all the black fields");
             }else {
                
                String checkData = "SELECT fname, mname, lname FROM resident_informations WHERE fname = "
                        + fNameForm.getText() + " AND mname = "+ mNameForm.getText() + " AND lname = "+ LnameForm.getText();

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {

                    successMessage(LnameForm.getText() +", "+ fNameForm.getText()+" "+mNameForm.getText() +" is already taken.");

                } else {
                    String insertData = "INSERT INTO resident_informations (fname, mname, lname, gender, age, birhtdate)"
                            + "VALUES (?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);

                    prepare.setString(1, fNameForm.getText());
                    prepare.setString(2, mNameForm.getText());
                    prepare.setString(3, LnameForm.getText());
                    prepare.setString(4, (String) genderForm.getSelectionModel().getSelectedItem());
                    prepare.setInt(5, Integer.parseInt(AgeForm.getText()));
                    prepare.setDate(6, java.sql.Date.valueOf(birthdateForm.getValue()));

                    prepare.executeUpdate();
                    residentRecord.updateTableView();

                }
             }
             
             
        } catch (Exception e) {
        }
    }
    
    //setting the items of combo boxes 
    //  items for gender
    private final String[] gender = {"Male", "Female"};

    public void genderList() {
        List<String> gList = new ArrayList<>();

        gList.addAll(Arrays.asList(gender));

        ObservableList listData = FXCollections.observableList(gList);
        genderForm.setItems(listData);
    }
    
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

}
