/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package treasury;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.main;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class TreasuryController implements Initializable {
    

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker myDatePicker;
    @FXML
    private TextField getFinalTotal;
     @FXML
    private TextField getCurrentDate;
    
    @FXML
    private ComboBox<String> myGenderOptions;

    @FXML
    private ComboBox<String> myCitizenship;

    @FXML
    private ComboBox<String> myTinNum;

    @FXML
    private ComboBox<String> myStatus;
    @FXML
    private TextField getTax1;
    @FXML
    private TextField getTax2;
    @FXML
    private TextField getTax3;
    @FXML
    private TextField getAmount1;
    @FXML
    private TextField getAmount2;
    @FXML
    private TextField getAmount3;
    @FXML
    private TextField getAmount4;
    @FXML
    private TextField getTotal;
  
     @FXML
    private TextField getInterest;
    @FXML
    private Button getResult;
    
    /*    //Preview
    @FXML
    private TextField getFirstname;
    @FXML
    private TextField getMIddlename;
    @FXML
    private TextField getLastname;
    @FXML
    private TextField getAdress;
   
    @FXML
    private DatePicker myDatePicker;
    @FXML
    private TextField getPlaceOfBirth;
    @FXML
    private TextField getProfession;
    @FXML
    private TextField getPlaceOfIssue;
    @FXML
    private TextField getCurrentDate;
    @FXML
    private TextField getFinalTotal;
    
    
   */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        myGenderOptions.getItems().addAll("Male", "Female");
        myCitizenship.getItems().addAll("Filipino", "American", "Australian", "British ", "Chinese" ,"Korean",  "Thai");
        myTinNum.getItems().addAll("None");
        myStatus.getItems().addAll("Single", "Married", "Widow/Widower/Legally Separated", "Divorced");
        
        //5 peso voluntary
        getAmount1.setText("5");
        
        addChangeListener(getAmount1, getTotal);
        addChangeListener(getAmount2, getTotal);
        addChangeListener(getAmount3, getTotal);
        addChangeListener(getAmount4, getTotal);
        
        //Date Issued
        setCurrentDateTextField();
        
        
        
        //getTax1
        getTax1.textProperty().addListener((observable, oldValue, newValue) -> {
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
        
    }    
    
    //Left-Nav Controller for buttons
    @FXML
    private void dashboardClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
    }

    @FXML
    private void barangayInfoClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayInformation/barangayInformation.fxml", "Barangay Information");
    }

    @FXML
    private void barangayOfficialClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayOfficial/barangayOfficial.fxml", "Barangay Official");
    }

    @FXML
    private void residentRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/residentRecord/residentRecord.fxml", "Resident Record");
    }

    @FXML
    private void businessRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/businessRecord/businessRecord.fxml", "Business Record");
    }

    @FXML
    private void householdRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/householdRecord/householdRecord.fxml", "Household Record");
    }

    @FXML
    private void requestedDocsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
    }

    @FXML
    private void treasuryClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/treasury/treasury.fxml", "Treasury");
    }

    @FXML
    private void reportsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/reports/reports.fxml", "Reports");
    }

    @FXML
    private void logOutClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");
    
        
        
    }
    
    //Select Gender
    @FXML
    public void Select() {
        // Handle Gender event
        String selectedGender = myGenderOptions.getValue();
        
    }

    @FXML
    public void getDate() {
        // Handle DatePicker event
        LocalDate date = myDatePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
       
    }
    
    @FXML
    private void handleResult(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Preview.fxml"));
        Parent root = loader.load();
        
        // Access the controller of the preview window
        PreviewController previewController = loader.getController();
        
        // Set the input values in the preview controller
       /* previewController.setPreviewData(
            getFirstname.getText(),
            getMIddlename.getText(),
            getLastname.getText(),
            getAdress.getText(),
            myGenderOptions.getValue(),
            myDatePicker.getValue(),
            getPlaceOfBirth.getText(),
            myCitizenship.getValue(), // Use getValue() for ComboBox
            myTinNum.getValue(), // Use getValue() for ComboBox
            myStatus.getValue(), // Use getValue() for ComboBox
            getProfession.getText(),
            getPlaceOfIssue.getText(),
            getCurrentDate.getText(),
            getFinalTotal.getText()
        );
        
        */
        // Create a new stage for the preview window and display it
        Stage previewStage = new Stage();
        previewStage.setScene(new Scene(root));
        previewStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void updateFinalTotal() {
        try {
            double amount1 = parseTextFieldValue(getAmount1);
            double amount2 = parseTextFieldValue(getAmount2);
            double amount3 = parseTextFieldValue(getAmount3);
            double amount4 = parseTextFieldValue(getAmount4);
            double interest = parseTextFieldValue(getInterest);
           
            double total = amount1 + amount2 + amount3 + amount4 + interest;

            // Format the total with at least 2 decimal points
            DecimalFormat df = new DecimalFormat("#.##");
            getFinalTotal.setText(df.format(total));
        } catch (NumberFormatException e) {
            
            
        }
    }

    private void addChangeListener(TextField sourceField, TextField targetField) {
        sourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTotal();
            updateFinalTotal(); 
        });
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
    //GetAmount3
    private void updateAmount3(String taxValue) {
    try {
        double tax = Double.parseDouble(taxValue);
        double amount2 = tax * 0.001; 
        getAmount3.setText(String.valueOf(amount2));
    } catch (NumberFormatException e) {
        
        getAmount3.setText("");
        System.err.println("");
       }
    }
    //GetAmount4
    private void updateAmount4(String taxValue) {
    try {
        double tax = Double.parseDouble(taxValue);
        double amount2 = tax * 0.001; 
        getAmount4.setText(String.valueOf(amount2));
    } catch (NumberFormatException e) {
        
        getAmount4.setText("");
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
    
}
    

