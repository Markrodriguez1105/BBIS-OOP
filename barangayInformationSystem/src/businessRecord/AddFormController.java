package businessRecord;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.regex.Pattern;

public class AddFormController implements Initializable {

    @FXML
    private AnchorPane addForm;

    @FXML
    private TextField coorFid;

    @FXML
    private TextField cpNumFid;

    @FXML
    private DatePicker dateFid;

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
    private DataValidator dataValidator;

    public void setBusinessController(businessRecordController businessController) {
        this.businessController = businessController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vatFid.setItems(FXCollections.observableArrayList("Registered", "Not Registered"));
        this.statFid.setItems(FXCollections.observableArrayList("Active", "Inactive"));
        this.dataValidator = new DataValidator();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void close() {
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void submit(ActionEvent event) {
        connection = DbConnect.getConnection();

        // Retrieve data from text fields
        String name = nameFid.getText(); // Assign the value of nameFid to name
        String type = typeFid.getText();
        String firstName = firstNameFid.getText();
        String middleName = middleNameFid.getText();
        String lastName = lastNameFid.getText();
        String cpNumText = cpNumFid.getText();
        String address = addressFid.getText();
        String incomeText = incomeFid.getText();
        String empText = empFid.getText();
        String tinText = tinFid.getText();
        String vatStatus = vatFid.getValue();
        String email = emailFid.getText();
        String activeStatus = statFid.getValue(); // Retrieve active status from ComboBox
        LocalDate dateValue = dateFid.getValue(); // Retrieve date value

// Check if any field is empty
        if (name.isEmpty() || address.isEmpty() || type.isEmpty() || incomeText.isEmpty()
                || cpNumText.isEmpty() || empText.isEmpty() || firstName.isEmpty()
                || middleName.isEmpty() || lastName.isEmpty()
                || tinText.isEmpty() || vatStatus == null || email.isEmpty() || activeStatus == null || dateValue == null) {
            showAlert("Please Fill All DATA");
        } else {
            // Perform data validation
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

                if (businessController != null) {
                    businessController.loadData();
                    businessController.autoUpdate(); // Call autoUpdate method after successful submission
                }

                close();
            } catch (NumberFormatException e) {
                showAlert("Invalid numeric input for Income, TIN, Contact Number, or Number of Employees");
            }
        }
    }

    private void getQuery() {
        if (!update) {
            query = "INSERT INTO `business` ( `business_name`, `business_type`, `first_name`,`middle_name`,`last_name`, `owner_phone_num`, `address`, `monthly_income`, `date_establishment`, `tin`, `vat_status`, `num_employees`,`date_registered`, `owner_email`, `active_status`, `barangay_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                    + "`owner_email`=?"
                    + "`active_status`=?"
                    + "`barangay_id`=? WHERE business_id = ?";
        }
    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nameFid.getText());
            preparedStatement.setString(2, typeFid.getText());
            preparedStatement.setString(3, firstNameFid.getText());
            preparedStatement.setString(4, middleNameFid.getText());
            preparedStatement.setString(5, lastNameFid.getText());
            preparedStatement.setLong(6, Long.parseLong(cpNumFid.getText()));
            preparedStatement.setString(7, addressFid.getText());
            preparedStatement.setInt(8, Integer.parseInt(incomeFid.getText()));
            preparedStatement.setDate(9, dateFid.getValue() != null ? java.sql.Date.valueOf(dateFid.getValue()) : null);
            preparedStatement.setInt(10, Integer.parseInt(tinFid.getText()));
            preparedStatement.setString(11, vatFid.getValue());  // Assuming vatFid is a ComboBox
            preparedStatement.setInt(12, Integer.parseInt(empFid.getText()));
            preparedStatement.setDate(13, java.sql.Date.valueOf(LocalDate.now())); // Date Registered at index 11
            preparedStatement.setString(14, emailFid.getText()); // Email at index 12
            preparedStatement.setString(15, statFid.getValue());

            // Set the barangay_id
            int barangayId = getNextBarangayIdForBusiness(); // You need to implement this method
            preparedStatement.setInt(16, barangayId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating record failed, no rows affected.");
            }

            // Get the generated keys after executing the PreparedStatement
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                } else {
                    // Handle the case where no keys were generated
                }
            }

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(AddFormController.class.getName()).log(Level.SEVERE, null, ex);
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

    private int getNextBarangayIdForBusiness() {
        int nextBarangayId = 0;
        try {
            // Create the query to find the maximum barangay_id for the business
            String query = "SELECT MAX(barangay_id) FROM business";

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(query);

            // Retrieve the maximum barangay_id
            if (resultSet.next()) {
                int maxBarangayId = resultSet.getInt(1);
                nextBarangayId = maxBarangayId + 1;
            }

            // Close the statement and result set
            statement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddFormController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Error retrieving next barangay_id: " + ex.getMessage());
        }
        return nextBarangayId;
    }

    public void setTextFieldRecord(String business_name, String business_type, String first_name, String middle_name, String last_name, long owner_phone_num, String coordinates, String address, int monthly_income, String status, int tin, String vat_status, int num_employees, String business_logo_url, String owner_email, String active_status) {
        // Assuming you have corresponding fields in your AddFormController

        nameFid.setText(business_name);
        typeFid.setText(business_type);
        firstNameFid.setText(first_name);
        middleNameFid.setText(middle_name);
        lastNameFid.setText(last_name);
        cpNumFid.setText(Long.toString(owner_phone_num));
        addressFid.setText(address);
        incomeFid.setText(Integer.toString(monthly_income));
        tinFid.setText(Integer.toString(tin));
        vatFid.setValue(vat_status);
        empFid.setText(Integer.toString(num_employees));
        emailFid.setText(owner_email);
        statFid.setValue(active_status);
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    void setTextFieldRecordExceptIds(String business_name, String business_type, long owner_phone_num, String coordinates, String address, int monthly_income, String status, int tin, String vat_status, int num_employees, String business_logo_url) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setUpdateFormController(UpdateFormController aThis) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void closeForm() {
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }
}
