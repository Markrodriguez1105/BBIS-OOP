package treasury;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.swing.text.TableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class CedulaForm implements Initializable {

    private TreasuryController treasuryController;

    @FXML
    private TextField getFirstname;
    @FXML
    private TextField getMiddlename;
    @FXML
    private TextField getLastname;
    @FXML
    private TextField getAddress;
    @FXML
    private TextField getProfession;
    @FXML
    private TextField getPlaceOfIssue;
    @FXML
    private TextField getPlaceOfBirth;
    @FXML
    private ComboBox<String> myGenderOptions;
    @FXML
    private DatePicker myDatePicker;
    @FXML
    private ComboBox<String> myCitizenship;
    @FXML
    private ComboBox<String> myTinNum;
    @FXML
    private ComboBox<String> myStatus;
    @FXML
    private TextField getCurrentDate;
    @FXML
    private TextField getAmount1;
    @FXML
    private TextField getTax;
    @FXML
    private TextField getTax2;
    @FXML
    private TextField getTax3;
    @FXML
    private TextField getAmount3;
    @FXML
    private TextField getAmount4;
   
    @FXML
    private TextField getFinalTotal;
    @FXML
    private TextField getAmount2;
    @FXML
    private TextField getTotal;
    @FXML
    private TextField getInterest;
    @FXML
    private TextField getPurpose;
    @FXML
    private Button getReset;
    @FXML
    private Button getSubmit;
    @FXML
    private TextField getHeight;
    @FXML
    private TextField getWeight;
    @FXML
    private TextField getCedulaNum;
    @FXML
    private RadioButton radiobutton_resident;
    @FXML
    private RadioButton radiobutton_nonres;
    private ToggleGroup residenceToggleGroup;

 


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myGenderOptions.getItems().addAll("Male", "Female");
        myCitizenship.getItems().addAll("Filipino", "American", "Australian", "British ", "Chinese", "Korean", "Thai");
        myTinNum.getItems().addAll("None");
        myStatus.getItems().addAll("Single", "Married", "Widow/Widower/Legally Separated", "Divorced");
        
        //5 peso voluntary
        getAmount1.setText("5");
        
        
        getAmount1.textProperty().addListener((observable, oldValue, newValue) -> {
        updateFinalTotal();
        });
        getAmount2.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFinalTotal();
        });
        getAmount3.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFinalTotal();
        });
        getAmount4.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFinalTotal();
        });
        getTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFinalTotal();
        });
        getInterest.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFinalTotal();
        });
        //getTax1
        getTax.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmount2(newValue);
            updateTotal();
        });
        //getTax2
        getTax2.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmount3(newValue);
            updateTotal();
        });
        //getTax3
        getTax3.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmount4(newValue);
            updateTotal();
        });
        
        // Initialize ToggleGroup
        residenceToggleGroup = new ToggleGroup();
        radiobutton_resident.setToggleGroup(residenceToggleGroup);
        radiobutton_nonres.setToggleGroup(residenceToggleGroup);

        // Add event handlers
        radiobutton_resident.setOnAction(event -> handleResidentSelection());
        radiobutton_nonres.setOnAction(event -> handleNonResidentSelection());
        
        setFieldsEditable(true);
        
        //Date Issued
        setCurrentDateTextField();

        // Populate search bar with resident data
        showResidentList();
        
         searchTable();
         

    }
    
   
     // Event handler for resident selection
    private void handleResidentSelection() {
        boolean isResident = radiobutton_resident.isSelected();
        setFieldsEditable(!isResident);
        
         getSearchCedula.setDisable(!isResident);
    }

    // Event handler for non-resident selection
    private void handleNonResidentSelection() {
        boolean isNonResident = radiobutton_nonres.isSelected();
        setFieldsEditable(isNonResident);
        getSearchCedula.setDisable(isNonResident);
       
        
    }

   

    // Method to set fields editable/uneditable
    private void setFieldsEditable(boolean editable) {
        // Set all fields initially uneditable
        getFirstname.setEditable(editable);
        getMiddlename.setEditable(editable);
        getLastname.setEditable(editable);
        getAddress.setEditable(editable);
        getProfession.setEditable(editable);
        getPlaceOfBirth.setEditable(editable);
        myGenderOptions.setDisable(!editable);
        myDatePicker.setDisable(!editable);
        myCitizenship.setDisable(!editable);
        myStatus.setDisable(!editable);
        getTax.setEditable(editable);      
        getHeight.setEditable(editable);
        getWeight.setEditable(editable);
      
        
    }

    public void setTreasuryController(TreasuryController controller) {
        this.treasuryController = controller;
    }
    @FXML
    private ComboBox<String> getSearchCedula;

    // JDBC connection parameters for XAMPP MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bbis";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //Select Gender
    @FXML
    public void Select() {

        String selectedGender = myGenderOptions.getValue();

    }

    @FXML
    public void getDate() {
        // Handle DatePicker event
        LocalDate date = myDatePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);

    }

    private void updateTotal() {
        try {
            double amount1 = parseTextFieldValue(getAmount1);
            double amount2 = parseTextFieldValue(getAmount2);
            double amount3 = parseTextFieldValue(getAmount3);
            double amount4 = parseTextFieldValue(getAmount4);

            double total = amount1 + amount2 + amount3 + amount4;

            DecimalFormat df = new DecimalFormat("#.##");
            getTotal.setText(df.format(total));
        } catch (NumberFormatException e) {
            
        }
    }
    
    private void updateFinalTotal() {
        try {
            double amount1 = parseTextFieldValue(getAmount1);
            double amount2 = parseTextFieldValue(getAmount2);
            double amount3 = parseTextFieldValue(getAmount3);
            double amount4 = parseTextFieldValue(getAmount4);
            double total = parseTextFieldValue(getTotal);
            double interest = parseTextFieldValue(getInterest);

            double finalTotal = amount1 + amount2 + amount3 + amount4 + total + interest;

            DecimalFormat df = new DecimalFormat("#.##");
            getFinalTotal.setText(df.format(finalTotal));
        } catch (NumberFormatException e) {
            // Handle exception
        }
    }

    //GetAmount2
    private void updateAmount2(String taxValue) {
        try {
            double tax = Double.parseDouble(taxValue);
            double amount2 = tax * 0.001;
            DecimalFormat df = new DecimalFormat("#.##");
            getAmount2.setText(df.format(amount2));
        } catch (NumberFormatException e) {
            getAmount2.setText("");
            System.err.println("");
        }
    }

    // Inside updateAmount3 method
    private void updateAmount3(String taxValue) {
        try {
            double tax = Double.parseDouble(taxValue);
            double amount3 = tax * 0.001;
            DecimalFormat df = new DecimalFormat("#.##");
            getAmount3.setText(df.format(amount3));
        } catch (NumberFormatException e) {
            getAmount3.setText("");
            System.err.println("");
        }
    }

    // Inside updateAmount4 method
    private void updateAmount4(String taxValue) {
        try {
            double tax = Double.parseDouble(taxValue);
            double amount4 = tax * 0.001;
            DecimalFormat df = new DecimalFormat("#.##");
            getAmount4.setText(df.format(amount4));
        } catch (NumberFormatException e) {
            getAmount4.setText("");
            System.err.println("");
        }
    }

    private double parseTextFieldValue(TextField textField) {
        String text = textField.getText().trim(); // Trim whitespace
        if (!text.isEmpty()) {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                // Handle parsing errors if necessary
            }
        }
        return 0.0; // Return default value or handle empty case accordingly
    }


    //Set date issued to current date
    private void setCurrentDateTextField() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        getCurrentDate.setText(formattedDate);
    }

    public ObservableList<Resident> residentList() {

        String baseQuery = "SELECT * FROM resident ORDER BY last_name ASC";

        ObservableList listData = FXCollections.observableArrayList();

        String selectData = baseQuery;
        try {

            if (connectDB() == null) {
                return listData; // Return an empty list
            }

            PreparedStatement prepare = connectDB().prepareStatement(selectData);
            ResultSet result = prepare.executeQuery();

            Resident residentDatas;

            while (result.next()) {

                residentDatas = new Resident(result.getInt("resident_id"), result.getString("first_name"), result.getString("middle_name"),
                        result.getString("last_name"), result.getString("suffix"), result.getString("gender"), result.getInt("age"),
                        result.getDate("birth_date"), result.getString("religion"), result.getString("civil_status"),
                        result.getString("educational_status"), result.getString("education_attainment"), result.getString("occupation"),
                        result.getInt("personal_income"), result.getString("with_disability"), result.getInt("zone"),
                        result.getLong("phone_num"), result.getString("email"), result.getInt("barangay_id"), result.getString("voter_status"),
                        result.getString("nationality"), result.getString("blood_type"), result.getInt("living_duration"),
                        result.getString("birth_place"), result.getInt("household_id"), result.getString("relation_to_family_head"),
                        result.getInt("height"), result.getInt("weight"), result.getString("mother's_name"), result.getLong("mother's_phone_num"),
                        result.getString("father's_name"), result.getLong("father's_phone_num"), result.getString("status"));

                listData.add(residentDatas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return listData;
    }

    public static Connection connectDB() {

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/bbis", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void searchTable() {
        ObservableList<String> initialItems = FXCollections.observableArrayList(getSearchCedula.getItems());

        getSearchCedula.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {

                getSearchCedula.setItems(initialItems);
            } else {

                FilteredList<String> filteredList = new FilteredList<>(
                        initialItems,
                        s -> s.toUpperCase().startsWith(newValue.toUpperCase())
                );
                getSearchCedula.setItems(filteredList);
            }
        });
    }

    ObservableList<Resident> householdHead;

    public void showResidentList() {
        householdHead = residentList();
        ArrayList<String> residentFullname = new ArrayList<>();

        for (Resident resident : householdHead) {
            residentFullname.add(resident.getFullName());
            ObservableList listData = FXCollections.observableList(residentFullname);
            getSearchCedula.setItems(listData);
        }

    }

    @FXML
    public int selectResident() {

        getSearchCedula.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedResident = newValue;
                getResident(selectedResident);

            }
        });
        return -1;

    }

    private Resident getResident(String fullName) {
        householdHead = residentList();
        Resident selectedResident;

        for (Resident resident : householdHead) {
            if (fullName.equals(resident.getFullName())) {
                selectedResident = resident;

                getFirstname.setText(selectedResident.getfName());
                getMiddlename.setText(selectedResident.getmName());
                getLastname.setText(selectedResident.getlName());
                myGenderOptions.setValue(selectedResident.getGender());
                getAddress.setText("Bonifacio, San Fernando, Camarines Sur");
                myDatePicker.setValue(Instant.ofEpochMilli(selectedResident.getBirthdate().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDate());
                getPlaceOfBirth.setText(selectedResident.getBirthplace());
                myCitizenship.setValue(selectedResident.getNationality());
                myStatus.setValue(selectedResident.getCivilStatus());
                getProfession.setText(selectedResident.getOccupation());
                getTax.setText(String.valueOf(selectedResident.getPersonalIncome()));
                getHeight.setText(String.valueOf(selectedResident.getHeight()));
                getWeight.setText(String.valueOf(selectedResident.getWeight()));
                        

            }
        }
        return null;
    }

    // Clear all input fields
    @FXML
    public void handleReset(ActionEvent event) {

        getFirstname.clear();
        getMiddlename.clear();
        getLastname.clear();
        getAddress.clear();
        getProfession.clear();
        getPlaceOfBirth.clear();
        myGenderOptions.getSelectionModel().clearSelection();
        myDatePicker.setValue(null);
        myCitizenship.getSelectionModel().clearSelection();
        myTinNum.getSelectionModel().clearSelection();
        myStatus.getSelectionModel().clearSelection();
        getTax.clear();
        getAmount2.clear();
        getTotal.clear();
        getInterest.clear();
        getPurpose.clear();
        getTax2.clear();
        getTax3.clear();
        getAmount3.clear();
        getAmount4.clear();
        getInterest.clear();
        getFinalTotal.clear();
        getHeight.clear();
        getWeight.clear();
    }

    @FXML
public void handleSubmit(ActionEvent event) {
    // Gather information from input fields
    String firstname = getFirstname.getText();
    String middlename = getMiddlename.getText();
    String lastname = getLastname.getText();
    LocalDate birthDate = myDatePicker.getValue();
    int age = calculateAge(birthDate);
    String address = getAddress.getText();
    String gender = myGenderOptions.getValue();
    String residencyStatus = checkResidencyStatus(address);
    int income = (int) parseTextFieldValue(getTax);
    String dateIssued = getCurrentDate.getText();
    String purpose = getPurpose.getText();
    double communityTax = parseTextFieldValue(getAmount1);
    double addcommTax = parseTextFieldValue(getAmount2);
    double total = parseTextFieldValue(getTotal);
    String tinNum = myTinNum.getValue();
    int brgyId = 101;
    double salaryTax = parseTextFieldValue(getTax2);
    double propertyTax = parseTextFieldValue(getTax3);
    double salaryTotal = parseTextFieldValue(getAmount3);
    double propertyTotal = parseTextFieldValue(getAmount4);
    double interest = parseTextFieldValue(getInterest);
    double finalTotal = parseTextFieldValue(getFinalTotal);
    int height = (int) parseTextFieldValue(getHeight);
    int weight = (int) parseTextFieldValue(getWeight);
    String cedulaNum = getCedulaNum.getText();

    // Check if any required field is empty
    if (firstname.isEmpty() || lastname.isEmpty() || birthDate == null || address.isEmpty() || gender.isEmpty() || residencyStatus.isEmpty() || dateIssued.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
        return;
    }

    // Insert or update record in the database
    try (Connection conn = connectDB()) {
        String sql = "INSERT INTO cedula ( `barangay_Id`, `purpose`, `first_name`, `middle_name`, `last_name`, `address`, `gender`, `age`, `tin_no`, `residency_status`, `date_issued`, `income`, `community_tax`, `addcomm_tax`, `total`, `salaray_tax`, `property_tax`, `salary_total`, `property_total`, `interest`, `final_total`, `height`, `weight`, `cedula_num`, `treasury_Id`) VALUES ( ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(25, ranNum());
        stmt.setInt(1, brgyId);
        stmt.setString(2, purpose);
        stmt.setString(3, firstname);
        stmt.setString(4, middlename);
        stmt.setString(5, lastname);
        stmt.setString(6, address);
        stmt.setString(7, gender);
        stmt.setInt(8, age);
        stmt.setString(9, tinNum);
        stmt.setString(10, residencyStatus);
        stmt.setString(11, dateIssued);
        stmt.setInt(12, income);
        stmt.setDouble(13, communityTax);
        stmt.setDouble(14, addcommTax);
        stmt.setDouble(15, total);
        stmt.setDouble(16, salaryTax);
        stmt.setDouble(17, propertyTax);
        stmt.setDouble(18, salaryTotal);
        stmt.setDouble(19, propertyTotal);
        stmt.setDouble(20, interest);
        stmt.setDouble(21, finalTotal);
        stmt.setInt(22, Integer.parseInt(getHeight.getText()));
        System.out.println(height);
        stmt.setInt(23, Integer.parseInt(getWeight.getText()));
        stmt.setString(24, cedulaNum);

        stmt.executeUpdate();
        treasuryController.updateTableView();

        Stage stage = (Stage) getSubmit.getScene().getWindow();
        stage.close();

    } catch (SQLException e) {
        e.printStackTrace();
      
}}


    private String checkResidencyStatus(String address) {
        if (address.equals("Bonifacio, San Fernando, Camarines Sur")) {
            return "Resident";
        } else {
            return "Non-Resident";
        }
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return -1; // or any default value to indicate the absence of a valid birthDate
        }
    }
   private int ranNum() {
    try (Connection conn = connectDB()) {
        String sql = "SELECT MAX(treasury_Id) FROM cedula";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int lastGeneratedNumber = rs.getInt(1);
            return lastGeneratedNumber + 1;
        } else {
            // If no records exist yet, start from 1
            return 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception, return default value or throw it further
        return -1; // Or any default value
    }
    }
}