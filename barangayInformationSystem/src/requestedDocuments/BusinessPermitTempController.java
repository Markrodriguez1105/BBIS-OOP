/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import assets.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HELLO MARK
 */
public class BusinessPermitTempController implements Initializable {

    @FXML
    private ScrollPane docContainer;
    @FXML
    private StackPane document;
    @FXML
    private Label docType;
    @FXML
    private Text docId;
    @FXML
    private Label yearIssued;
    @FXML
    private Label ownerFullanme;
    @FXML
    private Label address;
    @FXML
    private Label phoneNum;
    @FXML
    private Label purpose;
    @FXML
    private Label businessName;
    @FXML
    private Label businessAddress;
    @FXML
    private Label sigOwner;
    @FXML
    private Label issuedDate;
    @FXML
    private Label valMes;
    @FXML
    private Label issuedDate2;
    @FXML
    private Label ORNum;
    @FXML
    private Label docFee;
    @FXML
    private Label stampFee;
    @FXML
    private Label ctcIssuedDate;
    @FXML
    private Label ctcFee;
    @FXML
    private Label ctcNum;
    @FXML
    private Label placeIssued;
    @FXML
    private ComboBox<Printer> printerAvailable;
    
    document selected;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Database database = new Database();
        selected = requestedDocuments.RequestedDocumentController.selected;
        docContainer.setVvalue(0);
        printerAvailable.getItems().addAll(Printer.getAllPrinters());
        printerAvailable.setValue(Printer.getDefaultPrinter());
        try {
            ResultSet result = database.executeQuery(String.format("SELECT * FROM `document_paid` WHERE id = '%s';", selected.getId()));
            ResultSet result2 = database.executeQuery(String.format("SELECT `phone_num`, `business_name`, `business_address` FROM permit WHERE `id` = '%s';", selected.getId()));
            while (result.next()) {
                address.setText(result.getString(3));
                purpose.setText(result.getString(4));
                ORNum.setText(result.getString(5));
                stampFee.setText(result.getString(6));
                docFee.setText(result.getString(7));
                issuedDate.setText(result.getString(9));
                issuedDate2.setText(result.getString(9));
                ctcIssuedDate.setText(result.getString(10));
                ctcFee.setText(result.getString(11));
                ctcNum.setText(result.getString(12));
                placeIssued.setText(result.getString(13));
                yearIssued.setText(String.valueOf(result.getDate(9).toLocalDate().getYear()));

                docId.setText(selected.getId());
                docType.setText("BARANGAY " + selected.getDocumentType().toUpperCase());
                ownerFullanme.setText(selected.getFullname());
                sigOwner.setText(selected.getFullname().toUpperCase());
                valMes.setText("Validity of the " + selected.getDocumentType().toUpperCase() + " from the Date of Issuance");
            }
            while(result2.next()){
                phoneNum.setText(result2.getString(1));
                businessName.setText(result2.getString(2).toUpperCase());
                businessAddress.setText(result2.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void print(ActionEvent event) {
        PrinterJob print = PrinterJob.createPrinterJob();
        if (print != null) {
            print.setPrinter(printerAvailable.getValue());
            JobSettings settings = print.getJobSettings();
            settings.setJobName(docId.getText() + "_" + selected.getFullname().toUpperCase());
            boolean printed = print.printPage(document);
            if (printed) {
                print.endJob();
                new main.main().closeWindow(event);
            }
        }
    }

}
