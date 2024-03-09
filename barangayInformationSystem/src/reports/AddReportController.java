package reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;

public class AddReportController implements Initializable {

    @FXML
    private RadioButton InBarangay;

    @FXML
    private RadioButton OutBarangay;
    
    @FXML
    private TextField reportIdLabel;

    @FXML
    private ComboBox<String> reportTypeInput;
    
    @FXML
    private ComboBox<String> CBnameInput;
    
    @FXML
    private TextField firstnameInput;
    
    @FXML
    private TextField middlenameInput;
    
    @FXML
    private TextField lastnameInput;
    
    @FXML
    private TextField suffixnameInput;

    @FXML
    private TextField contactNumberInput;

    @FXML
    private TextField emailInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private TextArea reasonInput;

    private DatabaseConnector dbConnector = new DatabaseConnector();
    
    private ToggleGroup barangayToggleGroup;

    private SimpleIntegerProperty reportId = new SimpleIntegerProperty();
    
    private boolean ignoreListener = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportTypeInput.setItems(FXCollections.observableArrayList(dbConnector.getDistinctReportTypes()));
        reportId.set(getNextReportId());
        reportIdLabel.textProperty().bind(reportId.asString());

        List<Resident> residents = dbConnector.getResidents();
        List<String> formattedResidentNames = getFormattedResidentNames(residents);

        FilteredList<String> filteredResidentNames = new FilteredList<>(FXCollections.observableArrayList(formattedResidentNames));

        CBnameInput.setItems(filteredResidentNames);
        CBnameInput.setEditable(true); // Allow editing to select from filtered items

        // Add a listener to CBnameInput text property
        CBnameInput.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ignoreListener) {
                filteredResidentNames.setPredicate(residentName -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all residents if the filter is empty
                    }

                    // Convert both residentName and newValue to lowercase for case-insensitive search
                    return residentName.toLowerCase().contains(newValue.toLowerCase());
                });
            }
        });

        // Add a listener to handle the selection change event
        CBnameInput.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!Objects.equals(oldValue, newValue)) {
                    ignoreListener = true; // Ignore listener updates during item selection
                    CBnameInput.getEditor().setText(newValue); // Update the editor text
                    ignoreListener = false; // Re-enable the listener
                    Resident selectedResident = getResidentByName(residents, newValue);
                    if (selectedResident != null) {
                        populateFields(selectedResident);
                    }
                }
            }
        });

        // Set the default selected radio button to "In barangay"
        InBarangay.setSelected(true);

        barangayToggleGroup = new ToggleGroup();
        InBarangay.setToggleGroup(barangayToggleGroup);
        OutBarangay.setToggleGroup(barangayToggleGroup);

        // Add a listener to handle radio button selection change
        barangayToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                handleBarangayToggleChange(newValue);
            }
        });

        addValidationListener(firstnameInput);
        addValidationListener(middlenameInput);
        addValidationListener(lastnameInput);
        addValidationListener(suffixnameInput);
        addValidationListener(reportTypeInput);
        addValidationListener(emailInput);
        addValidationListener(contactNumberInput);
        addValidationListener(reasonInput);
    }




    
    @FXML
    private void addReport(ActionEvent event) {
        String errorMessage = validateInputs();

        if (errorMessage.isEmpty()) {
            addReportToDatabase();
            closeDialog();
        } else {
            showAlert("Please input the required fields:\n" + errorMessage);
        }
    }

    @FXML
    private void cancelAddReport(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) firstnameInput.getScene().getWindow();
        stage.close();
    }
    
    private void handleBarangayToggleChange(Toggle toggle) {
        if (toggle == InBarangay) {
            // Enable CBnameInput and populate fields if resident is selected
            CBnameInput.setDisable(false);
            CBnameInput.getSelectionModel().clearSelection();
        } else if (toggle == OutBarangay) {
            // Disable CBnameInput and clear its selection
            CBnameInput.setDisable(true);
            CBnameInput.getSelectionModel().clearSelection();

            // Manually input data in the required fields
            // You may add additional logic here
            firstnameInput.setText("");
            middlenameInput.setText("");
            lastnameInput.setText("");
            suffixnameInput.setText("");
            contactNumberInput.setText("");
            emailInput.setText("");
        }
    }
    
    private void populateFields(Resident selectedResident) {
        firstnameInput.setText(selectedResident.getFirstName());
        middlenameInput.setText(selectedResident.getMiddleName());
        lastnameInput.setText(selectedResident.getLastName());
        suffixnameInput.setText(selectedResident.getSuffix());
        contactNumberInput.setText(selectedResident.getContactNumber());
        emailInput.setText(selectedResident.getEmail());
    }

    private void addReportToDatabase() {
        try (Connection connection = dbConnector.connect()) {
            String insertQuery = "INSERT INTO `report` (`report_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, `date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status`, `InOutBarangay`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'pending', '1', ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, getNextReportId());
                preparedStatement.setString(2, firstnameInput.getText());
                preparedStatement.setString(3, middlenameInput.getText());
                preparedStatement.setString(4, lastnameInput.getText());
                preparedStatement.setString(5, suffixnameInput.getText());
                preparedStatement.setString(6, reportTypeInput.getValue() != null ? reportTypeInput.getValue().toString() : null);

                java.sql.Date sqlDate;
                if (dateInput.getValue() != null) {
                    sqlDate = java.sql.Date.valueOf(dateInput.getValue());
                } else {
                    sqlDate = java.sql.Date.valueOf(LocalDate.now());
                }
                preparedStatement.setDate(7, sqlDate);

                preparedStatement.setString(8, emailInput.getText());
                preparedStatement.setString(9, contactNumberInput.getText());
                preparedStatement.setString(10, reasonInput.getText());

                if (InBarangay.isSelected()) {
                    preparedStatement.setString(11, "In");
                } else if (OutBarangay.isSelected()) {
                    preparedStatement.setString(11, "Out");
                } else {
                    preparedStatement.setString(11, null);
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }



    
    // Method to add event listener for input validation
    private void addValidationListener(TextInputControl inputControl) {
        inputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputControl == firstnameInput || inputControl == middlenameInput || inputControl == lastnameInput || inputControl == suffixnameInput) {
                if (!newValue.matches("[a-zA-Z]*")) {
                    showAlert("Invalid input. Only alphabetic characters allowed.");
                    inputControl.setText(oldValue);
                }
            } else if (inputControl == contactNumberInput) {
                if (!newValue.matches("\\d*")) {
                    showAlert("Invalid input. Only numeric characters allowed.");
                    inputControl.setText(oldValue);
                }
            } else {
                if (!newValue.trim().isEmpty()) {
                    setValidInputStyle(inputControl);
                }
            }
        });
    }


    private void addValidationListener(ComboBox<?> comboBox) {
        comboBox.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    setValidInputStyle(comboBox);
                }
            }
        });
    }

    private String validateInputs() {
    StringBuilder errorMessage = new StringBuilder();

    // Counter to keep track of the number of missing fields
    int missingFieldsCount = 0;

    missingFieldsCount += validateField(firstnameInput, "First Name", errorMessage);
    missingFieldsCount += validateField(lastnameInput, "Last Name", errorMessage);
    missingFieldsCount += validateField(reportTypeInput, "Report type", errorMessage);
    missingFieldsCount += validateField(emailInput, "Email", errorMessage);
    missingFieldsCount += validateField(contactNumberInput, "Contact number", errorMessage);
    missingFieldsCount += validateField(reasonInput, "Reason", errorMessage);

    // Check if more than one field is missing
    if (missingFieldsCount > 1) {
        errorMessage = new StringBuilder("Please input the required fields.");
    }

    return errorMessage.toString();
}

    private int validateField(Control control, String fieldName, StringBuilder errorMessage) {
        int missingField = 0;

        if (control instanceof TextInputControl) {
            String text = ((TextInputControl) control).getText().trim();
            if (text.isEmpty()) {
                setInvalidInputStyle(control);
                errorMessage.append(fieldName).append("\n");
                missingField = 1;
            } else {
                setValidInputStyle(control);
            }
        } else if (control instanceof ComboBox) {
            ComboBox<?> comboBox = (ComboBox<?>) control;
            if (comboBox.getValue() == null) {
                setInvalidInputStyle(control);
                errorMessage.append(fieldName).append("\n");
                missingField = 1;
            } else {
                setValidInputStyle(control);
            }
        }

        return missingField;
    }

    private <T extends Control> boolean validateAndHighlight(T control, String errorMessage) {
        if (control instanceof TextInputControl || control instanceof ComboBox || control instanceof TextArea) {
            if (isValidInput(control)) {
                setValidInputStyle(control);
                return true;
            } else {
                setInvalidInputStyle(control);
                showAlert(errorMessage);
                return false;
            }
        }
        return true;
    }

    private void setValidInputStyle(Control control) {
        if (control instanceof TextInputControl || control instanceof ComboBox || control instanceof TextArea) {
            control.setStyle(""); // Reset the style to default (remove red border)
        }
    }

    private boolean isValidInput(Control control) {
        if (control instanceof TextInputControl) {
            String text = ((TextInputControl) control).getText().trim();
            return !text.isEmpty();
        } else if (control instanceof ComboBox) {
            ComboBox<?> comboBox = (ComboBox<?>) control;
            return comboBox.getValue() != null;
        }
        return true;
    }


    private void showAlert(String message) {
        Alert validationAlert = new Alert(AlertType.ERROR);
        validationAlert.setTitle("Validation Error");
        validationAlert.setHeaderText(null);
        validationAlert.setContentText(message);
        validationAlert.showAndWait();
    }


    private void setInvalidInputStyle(Control control) {
        if (control instanceof TextInputControl || control instanceof ComboBox || control instanceof TextArea) {
            control.setStyle("-fx-border-color: red;");
        }
    }


    private int getNextReportId() {
        return dbConnector.getMaxReportId() + 1;
    }
    private List<String> getFormattedResidentNames(List<Resident> residents) {
        List<String> formattedNames = new ArrayList<>();
        for (Resident resident : residents) {
            String formattedName = String.format("%s %s %s %s",
                    resident.getFirstName(),
                    resident.getMiddleName(),
                    resident.getLastName(),
                    resident.getSuffix());
            formattedNames.add(formattedName);
        }
        return formattedNames;
    }

    private Resident getResidentByName(List<Resident> residents, String selectedName) {
        for (Resident resident : residents) {
            String formattedName = String.format("%s %s %s %s",
                    resident.getFirstName(),
                    resident.getMiddleName(),
                    resident.getLastName(),
                    resident.getSuffix());
            if (Objects.equals(selectedName, formattedName)) {
                return resident;
            }
        }
        return null;
    }
}

