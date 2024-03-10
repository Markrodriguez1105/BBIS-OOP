package businessRecord;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateFormController implements Initializable {

    @FXML
    private TextField cpNumFid;

    @FXML
    private DatePicker dateFid;

    @FXML
    private TableView<Record> recordsTable;

    @FXML
    private TextField empFid;

    @FXML
    private TextField incomeFid;

    @FXML
    private TextField nameFid;

    @FXML
    private TextField addressFid;

    @FXML
    private TextField firstNameFid;
    @FXML
    private TextField middleNameFid;
    @FXML
    private TextField lastNameFid;

    @FXML
    private Button submit;

    @FXML
    private TextField tinFid;

    @FXML
    private TextField typeFid;

    @FXML
    private ComboBox<String> vatFid;
    
    @FXML
private ComboBox<String> statFid; 

    @FXML
    private TextField emailFid;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement;
    private boolean update;

    private businessRecordController businessController;
    private String business_id;
    private int businessId;

    @Override
public void initialize(URL location, ResourceBundle resources) {
    this.vatFid.setItems(FXCollections.observableArrayList("Registered", "Not Registered"));
    this.statFid.setItems(FXCollections.observableArrayList("Active", "Inactive"));
    dateFid.setValue(LocalDate.now());
    setUpdate(true);
}

    ObservableList<Record> RecordList = FXCollections.observableArrayList();

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openAddForm(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/businessRecord/addForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Show the "Add Form" window and wait for it to be closed
        } catch (IOException ex) {
            Logger.getLogger(AddFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeForm() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/businessrecord/businessRecord.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.close();
            businessController.autoUpdate();
        } catch (IOException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getQuery() {
    if (!update) {
        query = "INSERT INTO `business` (`business_name`, `business_type`, `first_name`,`middle_name`,`last_name`, `owner_phone_num`, `address`, `monthly_income`, `date_establishment`, `tin`, `vat_status`, `num_employees`, `owner_email`, `active_status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    } else {
        query = "UPDATE `business` SET "
                + "`business_name`=?,"
                + "`business_type`=?,"
                + "`first_name`=?,"
                + "`middle_name`=?,"
                + "`last_name`=?,"
                + "`owner_phone_num`=?,"
                + "`address`=?,"
                + "`monthly_income`=?,"
                + "`date_establishment`=?,"
                + "`tin`=?,"
                + "`vat_status`=?,"
                + "`num_employees`=?,"
                + "`owner_email`=?,"
                + "`active_status`=? "
                + "WHERE `business_id`=?";
    }
}


    private void insert() {
        try {
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Set parameters for the UPDATE query
            preparedStatement.setString(1, nameFid.getText()); // business_name
            preparedStatement.setString(2, typeFid.getText()); // business_type
            preparedStatement.setString(3, firstNameFid.getText()); // owner
            preparedStatement.setString(4, middleNameFid.getText()); // owner
            preparedStatement.setString(5, lastNameFid.getText()); // owner
            preparedStatement.setLong(6, Long.parseLong(cpNumFid.getText())); // owner_phone_num
            preparedStatement.setString(7, addressFid.getText()); // address
            preparedStatement.setInt(8, Integer.parseInt(incomeFid.getText())); // monthly_income
            preparedStatement.setDate(9, dateFid.getValue() != null ? java.sql.Date.valueOf(dateFid.getValue()) : null); // date_establishment
            preparedStatement.setInt(10, Integer.parseInt(tinFid.getText())); // tin
            preparedStatement.setString(11, vatFid.getValue()); // vat_status
            preparedStatement.setInt(12, Integer.parseInt(empFid.getText())); // num_employees
            preparedStatement.setString(13, emailFid.getText()); // owner_email
            preparedStatement.setString(14, statFid.getValue()); // owner_email
            preparedStatement.setInt(15, businessId); // Set the businessId here

            // Debugging output to print the businessId
            System.out.println("Business ID: " + businessId);
            System.out.println("SQL Query: " + preparedStatement.toString()); // Print the entire query for further debugging
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating record failed, no rows affected.");
            }

            // Get the generated keys after executing the PreparedStatement
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Handle generated keys here
                    int generatedId = generatedKeys.getInt(1); // Assuming the generated key is an integer
                    System.out.println("Generated ID: " + generatedId);
                } else {
                    // Handle the case where no keys were generated
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateFormController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Error executing SQL query: " + ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void setTextFieldRecordExceptIds(String business_name, String business_type, String first_name,String middle_name,String last_name, String owner_phone_num, String address, int monthly_income, int tin, String vat_status, int num_employees, Date date, String owner_email, String active_status) {
        // Set the values in the text fields
        nameFid.setText(business_name);
        typeFid.setText(business_type);
        firstNameFid.setText(first_name);
        middleNameFid.setText(middle_name);
        lastNameFid.setText(last_name);
        cpNumFid.setText(owner_phone_num); // Set the phone number as a string directly
        addressFid.setText(address);
        incomeFid.setText(Integer.toString(monthly_income));
        tinFid.setText(Integer.toString(tin));
        vatFid.setValue(vat_status);
        empFid.setText(Integer.toString(num_employees));
        dateFid.setValue(date.toLocalDate());
        emailFid.setText(owner_email);
        statFid.setValue(active_status);

        setUpdate(true);
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    private void setTextFieldRecordExceptIds(String businessId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public void initData(Record rec) {
        // Populate the fields with data from the Record object
        nameFid.setText(rec.getBusiness_name());
        typeFid.setText(rec.getBusiness_type());
        firstNameFid.setText(rec.getFirst_name());
        middleNameFid.setText(rec.getMiddle_name());
        lastNameFid.setText(rec.getLast_name());
        cpNumFid.setText(String.valueOf(rec.getOwner_phone_num()));
        addressFid.setText(rec.getAddress());
        incomeFid.setText(String.valueOf(rec.getMonthly_income()));
        tinFid.setText(String.valueOf(rec.getTin()));
        vatFid.setValue(rec.getVat_status());
        empFid.setText(String.valueOf(rec.getNum_employees()));
        emailFid.setText(rec.getOwner_Email());
        statFid.setValue(rec.getActive_status());
        setBusinessId(rec.getBusiness_id());
        setUpdate(true);
    }

    @FXML
public void submit(ActionEvent event) {
    System.out.println("Submit button clicked!");
    connection = DbConnect.getConnection();

    // Retrieve data from text fields
    String name = nameFid.getText();
    String cpNumText = cpNumFid.getText();
    String address = addressFid.getText();
    String type = typeFid.getText();
    String incomeText = incomeFid.getText();
    String empText = empFid.getText();
    String firstName = firstNameFid.getText();
    String middleName = middleNameFid.getText();
    String lastName = lastNameFid.getText();
    String tinText = tinFid.getText();
    String vatStatus = vatFid.getValue();
    String email = emailFid.getText();
    String activeStatus = statFid.getValue(); // Retrieve active status from ComboBox
    LocalDate dateValue = dateFid.getValue(); // Retrieve date value

    // Check if any field is empty
    if (name.isEmpty() || address.isEmpty() || type.isEmpty() || incomeText.isEmpty()
            || cpNumText.isEmpty() || empText.isEmpty() || firstName.isEmpty()
            || middleName.isEmpty() || lastName.isEmpty() ||tinText.isEmpty() || vatStatus == null || email.isEmpty() || activeStatus == null || dateValue == null) {
        showAlert("Please Fill All DATA");
    } else {
        // Perform data validation
        DataValidator dataValidator = new DataValidator();

        if (!dataValidator.isValidEmail(email)) {
            showAlert("Invalid email format. Please enter a valid email address.");
            return;
        }

        if (!dataValidator.isValidContactNumber(cpNumText)) {
            showAlert("Invalid contact number format. Please enter a valid contact number.");
            return;
        }

        if (!dataValidator.isValidTIN(tinText)) {
            showAlert("Invalid TIN format. TIN should be a 9-digit number.");
            return;
        }

        if (!dataValidator.isValidMonthlyIncome(incomeText)) {
            showAlert("Invalid monthly income. Monthly income should be at least 1000.");
            return;
        }

        try {
            // Parse numeric values
            int income = Integer.parseInt(incomeText);
            int tin = Integer.parseInt(tinText);
            long cpNum = Long.parseLong(cpNumText);
            int emp = Integer.parseInt(empText);

            // Continue with the logic (you can use the parsed values in your methods)
            getQuery();
            insert();

            if(businessController != null){
            businessController.loadData();
            businessController.autoUpdate(); // Call autoUpdate method after successful submission
            }

            // Get the current stage and close it
            close();
        } catch (Exception e) {
            showAlert("Invalid numeric input for Income, TIN, Contact Number, or Number of Employees");
            e.printStackTrace();
        }

    }
}

    private boolean validateFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void close() {
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }

    private static class recordsTable {

        private static void refresh() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void setItems(ObservableList<Record> RecordList) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public recordsTable() {
        }
    }
}