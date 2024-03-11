/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package barangayInformation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import main.main;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class BarangayInformationController implements Initializable {

    @FXML
    private Text user_lname;
    @FXML
    private Text user_fname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_fname.setText(LogIn.LogInController.user_fname);
        user_lname.setText(LogIn.LogInController.user_lname);
        // TODO
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
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Secretary")) {
            main main = new main();
            main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void treasuryClick(ActionEvent event) throws IOException {
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Treasurer")) {
            main main = new main();
            main.changeScene("/treasury/treasury.fxml", "Treasury");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void reportsClick(ActionEvent event) throws IOException {
        if (LogIn.LogInController.position.equals("Punong Barangay")
                || LogIn.LogInController.position.equals("Barangay Secretary")) {
            main main = new main();
            main.changeScene("/reports/reports.fxml", "Reports");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("No Access");
            alert.showAndWait();
        }
    }

    @FXML
    private void logOutClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");
    }

    
}
