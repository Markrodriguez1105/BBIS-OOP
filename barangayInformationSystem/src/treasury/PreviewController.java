/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package treasury;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Weneilyn
 */
public class PreviewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}


/*package treasury;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PreviewController {
    @FXML
    private TextField dataFirstname;
    @FXML
    private TextField dataMiddlename;
    @FXML
    private TextField dataLastname;
    @FXML
    private TextField dataAddress;
    @FXML
    private TextField dataGender;
    @FXML
    private TextField dataDate;
    @FXML
    private TextField dataPlaceOfBirth;
    @FXML
    private TextField dataCitizenship	;
    @FXML
    private TextField dataTinNum;
    @FXML
    private TextField dataCivilStatus	;
    @FXML
    private TextField dataProfession;
    @FXML
    private TextField dataPlaceIssue;
    @FXML
    private TextField dataCurrentDate;
    @FXML
    private TextField dataTotal;

    public void setPreviewData(
        String firstname,
        String middlename,
        String lastname,
        String address,
        String gender,
        LocalDate birthdate,
        String placeOfBirth,
        String citizenship,
        String tinNum,
        String status,
        String profession,
        String placeOfIssue,
        String currentDate,
        String finalTotal
    ) {
        dataFirstname.setText(firstname);
        dataMiddlename.setText(middlename);
        dataLastname.setText(lastname);
        dataAddress.setText(address);
        dataGender.setText(gender);
        dataDate.setText(birthdate.toString());
        dataPlaceOfBirth.setText(placeOfBirth);
        dataCitizenship.setText(citizenship);
        dataTinNum.setText(tinNum);
        dataCivilStatus.setText(status);
        dataProfession.setText(profession);
        dataPlaceIssue.setText(placeOfIssue);
        dataCurrentDate.setText(currentDate);
        dataTotal.setText(finalTotal);
    }
}*/