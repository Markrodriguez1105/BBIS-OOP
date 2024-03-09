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
    private TextField getAmount2;
    @FXML
    private TextField getTotal;
    private TextField getInterest;
    @FXML
    private TextField getPurpose;
    @FXML
    private Button getReset;
    @FXML
    private Button getSubmit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        myGenderOptions.getItems().addAll("Male", "Female");
        myCitizenship.getItems().addAll("Filipino", "American", "Australian", "British ", "Chinese", "Korean", "Thai");
        myTinNum.getItems().addAll("None");
        myStatus.getItems().addAll("Single", "Married", "Widow/Widower/Legally Separated", "Divorced");

        //5 peso voluntary
        getAmount1.setText("5");

        //Date Issued
        setCurrentDateTextField();
        //getTax
        getTax.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmount2(newValue);
            updateTotal();

        });

        showResidentList();
        searchTable();

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

            double total = amount1 + amount2;

            DecimalFormat df = new DecimalFormat("#.##");
            getTotal.setText(df.format(total));
        } catch (NumberFormatException e) {

        }
    }

    //GetAmount2
    private void updateAmount2(String taxValue) {
        try {
            double tax = Double.parseDouble(taxValue);
            double amount2 = tax * 0.001;
            getAmount2.setText(String.valueOf(amount2));

            DecimalFormat df = new DecimalFormat("#.##");
            getAmount2.setText(df.format(amount2));

        } catch (NumberFormatException e) {

            getAmount2.setText("");
            System.err.println("");
        }
    }

    private double parseTextFieldValue(TextField textField) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }

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
        int income = Integer.parseInt(getTax.getText());
        String dateIssued = getCurrentDate.getText();
        String purpose = getPurpose.getText();
        double communityTax = Double.parseDouble(getAmount1.getText());
        double addcommTax = Double.parseDouble(getAmount2.getText());
        double total = Double.parseDouble(getTotal.getText());
        String tinNum = myTinNum.getValue();
        int brgyId = 101;

        // Insert or update record in the database
        try (Connection conn = connectDB()) {
            String sql = "INSERT INTO cedula ( barangay_Id, purpose, first_name, middle_name, "
                    + "last_name, address, gender, age, tin_no, residency_status, date_issued, income, "
                    + "community_tax, addcomm_tax, total) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
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

            stmt.executeUpdate();
            
            Stage stage = (Stage) getSubmit.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors

        }
    }

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

}
