package businessRecord;

import businessRecord.businessRecordController;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewController implements Initializable {

    private Record rec;

    @FXML
    private TextField addId;

    @FXML
    private TextField busId;

    @FXML
    private TextField busNameId;

    @FXML
    private TextField busTypeId;

    @FXML
    private TextField contactId;

    @FXML
    private TextField emplooyeId;

    @FXML
    private TextField establishId;

    @FXML
    private TextField incomeId;

    @FXML
    private TextField firstNId;
    @FXML
    private TextField middleNId;
    @FXML
    private TextField lastNId;

    @FXML
    private TextField registerId;

    @FXML
    private TextField barangayId;

    @FXML
    private TextField tinId;

    @FXML
    private TextField vatId;

    @FXML
    private TextField emailId;
    
    @FXML
    private TextField statId;

    private businessRecordController businessController;
    private businessRecordController controller;

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openUpdateForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/businessRecord/updateForm.fxml"));
            Parent parent = loader.load();
            UpdateFormController controller = loader.getController();
            controller.initData(rec); // Pass the Record object to UpdateFormController
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Show the "Update Form" window and wait for it to be closed
            initData(rec); // Update the view form
            close(event); // Close the view form
        } catch (IOException ex) {
            Logger.getLogger(UpdateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            close(event); // Close the view form
            initData(rec); // Update the view form
        } catch (IOException ex) {
            Logger.getLogger(UpdateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void autoUpdate() {
        if (businessController != null) {
            businessController.autoUpdate();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Record record) {
        rec = record;
        businessController = controller;
        this.controller = controller;

        // Interchanged mappings for VAT status and address
        vatId.setText(rec.getVat_status()); // VAT status field now displays the address
        busId.setText(String.valueOf(rec.getBusiness_id()));
        busNameId.setText(rec.getBusiness_name());
        busTypeId.setText(rec.getBusiness_type());
        statId.setText(rec.getStatus());

        // Correct the mappings
        tinId.setText(String.valueOf(rec.getTin())); // Mapping TIN to the tinId TextField
        contactId.setText(String.valueOf(rec.getOwner_phone_num())); // Mapping Contact Number to the contactId TextField

        emplooyeId.setText(String.valueOf(rec.getNum_employees())); // Mapping Number of Employees to the emplooyeId TextField

        incomeId.setText(String.valueOf(rec.getMonthly_income()));

        firstNId.setText(rec.getFirst_name());
        middleNId.setText(rec.getMiddle_name());
        lastNId.setText(rec.getLast_name());
        barangayId.setText(String.valueOf(rec.getBarangay_id()));

        System.out.println("Address: " + rec.getAddress());
        addId.setText(rec.getAddress());

        establishId.setText(formatDate(rec.getDate_establishment()));
        registerId.setText(formatDate(rec.getDate_registered()));
        System.out.println("Email ID: " + rec.getOwner_email());

        emailId.setText(rec.getOwner_email());

        autoUpdate(); // Call the autoUpdate() method here to trigger the auto-update functionality
    }

// Example DateFormat method (replace with your desired format)
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
