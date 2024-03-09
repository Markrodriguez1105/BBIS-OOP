/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package residentRecord;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import dashboard.*;
import java.util.HashMap;
import java.sql.*;
import java.util.Set;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class AddFormController implements Initializable {

    @FXML
    private TextField fname;
    @FXML
    private TextField mname;
    @FXML
    private TextField lname;
    @FXML
    private TextField suffix;
    @FXML
    private ComboBox<String> civilStatus;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNum;
    @FXML
    private ComboBox<String> voterStatus;
    @FXML
    private ComboBox<String> bloodType;
    @FXML
    private TextField zone;
    @FXML
    private ComboBox<String> withDisability;
    @FXML
    private ComboBox<String> educationAtt;
    @FXML
    private ComboBox<String> educationStats;
    @FXML
    private ComboBox<String> inOutBarangay;

    Database database = new Database();
    HashMap<Integer, String> householdListHash = new HashMap<>();
    ObservableList<String> householdListObs = FXCollections.observableArrayList();
    @FXML
    private TextField resident_id;
    @FXML
    private DatePicker bdate;
    @FXML
    private TextField age;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TextField nationality;
    @FXML
    private TextField birthPlace;
    @FXML
    private TextField religion;
    @FXML
    private TextField household;
    @FXML
    private TextField address;
    @FXML
    private TextField occupation;
    @FXML
    private TextField livingDuration;
    @FXML
    private TextField relationshipToHead;
    @FXML
    private TextField perIncome;
    @FXML
    private TextField weight;
    @FXML
    private TextField height;
    @FXML
    private TextField motherName;
    @FXML
    private TextField motherNum;
    @FXML
    private TextField fatherName;
    @FXML
    private TextField fatherNum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Initializing ComboBox
        civilStatus.getItems().add("Single");
        civilStatus.getItems().add("Married");
        civilStatus.getItems().add("Divorced");
        civilStatus.getItems().add("Widowed");

        voterStatus.getItems().add("Registered");
        voterStatus.getItems().add("Not Registered");

        ObservableList<String> bloodTypeOptions = FXCollections.observableArrayList(
                "A",
                "B",
                "AB",
                "O",
                "A Rh+",
                "A Rh-",
                "B Rh+",
                "B Rh-",
                "AB Rh+",
                "AB Rh-",
                "O Rh+",
                "O Rh-"
        );

        bloodType.setItems(bloodTypeOptions);

        withDisability.getItems().add("Yes");
        withDisability.getItems().add("No");

        educationAtt.getItems().add("Primary School");
        educationAtt.getItems().add("Secondary School");
        educationAtt.getItems().add("Vocational School");
        educationAtt.getItems().add("Tertiary School");

        educationStats.getItems().add("Not studying anymore");
        educationStats.getItems().add("Studying");
        educationStats.getItems().add("Graduated");

        inOutBarangay.getItems().add("In the Barangay");
        inOutBarangay.getItems().add("Out of Barangay");

        ResultSet result = database.executeQuery("SELECT `household_id`, CONCAT(`last_name`, ', ', `first_name`, ' ', \n"
                + "CASE \n"
                + "WHEN LENGTH(`middle_name`) > 0 THEN CONCAT(LEFT(`middle_name`, 1), '.')\n"
                + "ELSE '' \n"
                + "END) AS `full_name`\n"
                + "FROM `household`;");
        try {
            while (result.next()) {
                householdListObs.add(result.getString(2));
                householdListHash.put(result.getInt(1), result.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        civilStatus.setItems(householdListObs);

    }

    @FXML
    private void cancel(ActionEvent event) {
    }


    @FXML
    private void selected(ActionEvent event) {
        Set<Integer> keys = householdListHash.keySet();
        for(int value : keys){
            if(civilStatus.getValue().equals(householdListHash.get(value))){
                System.out.println(value);
            }
        }
    }

    @FXML
    private void submit(ActionEvent event) {
    }

}
