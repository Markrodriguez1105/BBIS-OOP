/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.main;
import assets.*;
import com.mysql.cj.protocol.Resultset;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import reports.DatabaseConnector;
import java.sql.*;

/**
 * FXML Report Controller class
 *
 * @author Hello Jovel
 */
public class RequestedDocumentController implements Initializable {
    private static ObservableList<document> list = FXCollections.observableArrayList();
    document selected;

    @FXML
    private ComboBox<String> docsType;
    @FXML
    private TableColumn<document, String> fullName;
    @FXML
    private TableColumn<document, String> cat;
    @FXML
    private TableColumn<document, String> docType;
    @FXML
    private TableColumn<document, String> stats;
    @FXML
    private TableView<document> documentList = new TableView<>();
    @FXML
    private TableColumn<document, Date> dateReq;
    @FXML
    private ScrollPane certificateView;
    @FXML
    private ScrollPane permitView;
    @FXML
    private Text CrqstName;
    @FXML
    private Text CDocsId;
    @FXML
    private Text CrqstDate;
    @FXML
    private Label Cname;
    @FXML
    private Label CphoneNumber;
    @FXML
    private Label Cemail;
    @FXML
    private Label CAddress;
    @FXML
    private Label Ccategory;
    @FXML
    private Label CDocsType;
    @FXML
    private Label CpayStats;
    @FXML
    private Label Cpurpose;
    @FXML
    private Text PrqstName;
    @FXML
    private Text PDocsId;
    @FXML
    private Text PrqstDate;
    @FXML
    private Label Pname;
    @FXML
    private Label PphoneNumber;
    @FXML
    private Label Pemail;
    @FXML
    private Label PBusinessName;
    @FXML
    private Label PBusinessType;
    @FXML
    private Label PbusinessAddress;
    @FXML
    private Label PpayStats;
    @FXML
    private Label PDocsType;
    @FXML
    private Label PAddress;
    @FXML
    private Label Ppurpose;
    @FXML
    private Button Cpayment;
    @FXML
    private Button Ppayment;
    @FXML
    private TextField search;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Database database = new Database();
        permitView.setVisible(false);
        certificateView.setVisible(false);
        permitView.setVvalue(0);
        certificateView.setVvalue(0);

        //Initialized ComboBox
        docsType.getItems().add("All Documents");
        docsType.getItems().addAll(setComboBox(database.executeQuery("""
                                                                  SELECT `document_type` 
                                                                  FROM `requesteddocs`
                                                                  GROUP BY 1
                                                                  ORDER BY 1;""")));
        docsType.setValue(docsType.getItems().getFirst());

        //Initialized TableView
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cat.setCellValueFactory(new PropertyValueFactory<>("cat"));
        docType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        stats.setCellValueFactory(new PropertyValueFactory<>("stats"));
        dateReq.setCellValueFactory(new PropertyValueFactory<>("date"));
        documentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        documentList.getItems().clear();
        list.setAll(setTable());
        documentList.setItems(list);

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

    @FXML
    private void copy(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(((Labeled) event.getSource()).getText());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System Messsage");
        alert.setHeaderText(null);
        alert.setContentText("Text Copied to Clipboard");
        alert.showAndWait();

    }

    public ObservableList<String> setComboBox(ResultSet result) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    ObservableList<document> setTable() {
        Database database = new Database();
        document rd;
        ObservableList<document> list = FXCollections.observableArrayList();
        try {
            ResultSet result = database.executeQuery("""
                                                     SELECT *
                                                     FROM `requesteddocs`
                                                     ORDER BY `date_requested` DESC;""");
            while (result.next()) {
                rd = new document(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getDate(6));
                list.add(rd);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    void updateTable(){
        list.setAll(setTable());
        documentList.setItems(list);
    }

    @FXML
    private void cellSelected(MouseEvent event) {
        permitView.setVvalue(0);
        certificateView.setVvalue(0);
        Database database = new Database();
        selected = documentList.getSelectionModel().getSelectedItem();
        permitView.setVisible(false);
        certificateView.setVisible(false);
        Ppayment.setDisable(false);
        Cpayment.setDisable(false);

        if (selected.getStats().equalsIgnoreCase("Paid")) {
            Ppayment.setDisable(true);
            Cpayment.setDisable(true);
        }

        if (selected.getCat().equalsIgnoreCase("Barangay Permit")) {
            permitView.setVisible(true);
            try {
                ResultSet result = database.executeQuery(String.format("""
                                                         SELECT `id`, `resident_id`, CONCAT(lastName, ", ", firstName, " ", 
                                                         CASE
                                                         WHEN LENGTH(middleName) > 0 THEN CONCAT(SUBSTRING(middleName, 1, 1),'.')
                                                         ELSE '' END) AS fullName, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `permit_type`, `date_requested`, `purpose`
                                                         FROM `permit`
                                                         WHERE `id` = %s;""", selected.getId()));
                while (result.next()) {
                    PDocsId.setText(result.getString(1));
                    PrqstName.setText(result.getString(2));
                    Pname.setText(result.getString(3));
                    PAddress.setText(result.getString(4));
                    PphoneNumber.setText(result.getString(5));
                    Pemail.setText(result.getString(6));
                    PBusinessName.setText(result.getString(7));
                    PBusinessType.setText(result.getString(8));
                    PbusinessAddress.setText(result.getString(9));
                    PpayStats.setText("Paid");
                    PDocsType.setText(result.getString(10));
                    PrqstDate.setText(result.getString(11));
                    Ppurpose.setText(result.getString(12));
                    if(PpayStats.getText().equalsIgnoreCase("Paid")){
                        PpayStats.setStyle("-fx-text-fill: GREEN;");
                    }else{
                        PpayStats.setStyle("-fx-text-fill: RED;");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (selected.getCat().equalsIgnoreCase("Barangay Certification")) {
            certificateView.setVisible(true);
            try {
                ResultSet result = database.executeQuery(String.format("""
                                                         SELECT `id`, `resident_id`, CONCAT(lastName, ", ", firstName, " ", 
                                                         CASE
                                                         WHEN LENGTH(middleName) > 0 THEN CONCAT(SUBSTRING(middleName, 1, 1),'.')
                                                         ELSE '' END) AS fullName, `address`, `phone_num`, `email`, `certification_type`, `date_requested`, `purpose`
                                                         FROM `certification`
                                                         WHERE `id` = %s;""", selected.getId()));
                while (result.next()) {
                    CDocsId.setText(result.getString(1));
                    CrqstName.setText(result.getString(2));
                    Cname.setText(result.getString(3));
                    CAddress.setText(result.getString(4));
                    CphoneNumber.setText(result.getString(5));
                    Cemail.setText(result.getString(6));
                    CDocsType.setText(result.getString(7));
                    CrqstDate.setText(result.getString(8));
                    CpayStats.setText("Pending");
                    Cpurpose.setText(result.getString(9));
                    if(CpayStats.getText().equalsIgnoreCase("Paid")){
                        CpayStats.setStyle("-fx-text-fill: GREEN;");
                    }else{
                        CpayStats.setStyle("-fx-text-fill: RED;");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    ObservableList<document> filter(ResultSet result) {
        ObservableList<document> filtered = FXCollections.observableArrayList();
        try {
            while (result.next()) {
                filtered.addAll(new document(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getDate(6)));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtered;
    }

    @FXML
    private void createDocs(ActionEvent event) throws IOException {
        main main = new main();
        main.overlayWindow("/requestedDocuments/addDoc.fxml", "Create New Document");
    }

    @FXML
    private void docsType(ActionEvent event) {
        ObservableList<document> filtered = FXCollections.observableArrayList();
        if (!docsType.getValue().equals("All Documents")) {
            for (document record : list) {
                if (record.getDocumentType().equals(docsType.getValue())) {
                    filtered.add(record);
                }
            }
            documentList.setItems(filtered);
        } else {
            documentList.setItems(list);
        }
    }

    @FXML
    private void clear(MouseEvent event) {
        search.setText("");
        documentList.setItems(list);
    }

    @FXML
    private void edit(ActionEvent event) {
    }

    @FXML
    private void print(ActionEvent event) {
    }

    @FXML
    private void payment(ActionEvent event) {
    }

    @FXML
    private void search(KeyEvent event) {
//        docsType.setValue(docsType.getItems().getFirst());
        ObservableList<document> filtered = FXCollections.observableArrayList();
        if (!search.getText().isBlank()) {
            for (document record : list) {
                if (record.getFullName().toLowerCase().contains(search.getText().toLowerCase())) {
                    filtered.add(record);
                }
            }
            documentList.setItems(filtered);
        }else{
            documentList.setItems(list);
        }
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Database database = new Database();
        selected = documentList.getSelectionModel().getSelectedItem();
        if(selected.getCat().equals("Barangay Permit")){
            PreparedStatement prep = database.insertQuery(String.format("DELETE FROM `permit` WHERE `id` = %s", Integer.valueOf(selected.id)));
            System.out.println(prep.executeUpdate());
            
        }else if(selected.getCat().equals("Barangay Certification")){
            PreparedStatement prep = database.insertQuery(String.format("DELETE FROM `certification` WHERE `id` = %s", Integer.valueOf(selected.id)));
            System.out.println(prep.executeUpdate());
        }else{
            System.out.println("No passed");
        }
        updateTable();
    }
}
