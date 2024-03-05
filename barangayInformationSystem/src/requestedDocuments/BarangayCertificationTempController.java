/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import assets.*;
import java.net.URL;
import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HELLO MARK
 */
public class BarangayCertificationTempController implements Initializable {

    @FXML
    private Label docType;
    @FXML
    private Text docId;
    @FXML
    private Label docContent;
    @FXML
    private Label lname;
    @FXML
    private Label fname;
    @FXML
    private Label mname;
    @FXML
    private Label address;
    @FXML
    private Label purpose;
    @FXML
    private Label remarks;
    @FXML
    private Label sigFullName;
    @FXML
    private Label issuedDate;
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
    private ScrollPane docContainer;
    @FXML
    private StackPane document;
    @FXML
    private Label valMes;
    @FXML
    private ComboBox<Printer> printerAvailable;

    document selected;
    @FXML
    private GridPane showPay;
    @FXML
    private GridPane showCTC;

    /**
     * Initializes the controller class.
     *
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

        if (selected.getDocumentType().equalsIgnoreCase("Indigency")) {
            showCTC.setVisible(false);
            showPay.setVisible(false);
            try {
                ResultSet result = database.executeQuery(String.format("SELECT * FROM `certification` WHERE id = '%s';", selected.getId()));
                while (result.next()) {
                    address.setText(result.getString(7));
                    purpose.setText(result.getString("purpose"));
                    issuedDate.setText(String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

                    docId.setText(selected.getId());
                    docType.setText("BARANGAY " + selected.getDocumentType().toUpperCase());
                    docContent.setText(setContent(selected.getDocumentType()));
                    lname.setText(selected.getLastname());
                    fname.setText(selected.getFirstname());
                    mname.setText(selected.getMiddlename());
                    sigFullName.setText(selected.getFullname().toUpperCase());
                    valMes.setText("Validity of the " + selected.getDocumentType().toUpperCase() + " from the Date of Issuance");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                ResultSet result = database.executeQuery(String.format("SELECT * FROM `document_paid` WHERE id = '%s';", selected.getId()));
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

                    docId.setText(selected.getId());
                    docType.setText("BARANGAY " + selected.getDocumentType().toUpperCase());
                    docContent.setText(setContent(selected.getDocumentType()));
                    lname.setText(selected.getLastname());
                    fname.setText(selected.getFirstname());
                    mname.setText(selected.getMiddlename());
                    sigFullName.setText(selected.getFullname().toUpperCase());
                    valMes.setText("Validity of the " + selected.getDocumentType().toUpperCase() + " from the Date of Issuance");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    String setContent(String type) {
        return "           This is to certify that as per record, the person whose name and signature appearing herein has requested a " + type.toUpperCase() + " from this office with the following detail/s;";
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
