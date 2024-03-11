/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import assets.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.main;

/**
 * FXML Report Controller class
 *
 */
public class RequestedDocumentController implements Initializable {

    private static ObservableList<document> lists = FXCollections.observableArrayList();
    public static document selected = null;

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
    @FXML
    private Label Pcategory;
    @FXML
    private Button Cdelete;
    @FXML
    private Button Pdelete;
    @FXML
    private Button Cprint;
    @FXML
    private Button Pprint;
    @FXML
    private Text user_lname;
    @FXML
    private Text user_fname;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user_fname.setText(LogIn.LogInController.user_fname);
        user_lname.setText(LogIn.LogInController.user_lname);
        
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
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        cat.setCellValueFactory(new PropertyValueFactory<>("cat"));
        docType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        stats.setCellValueFactory(new PropertyValueFactory<>("stats"));
        dateReq.setCellValueFactory(new PropertyValueFactory<>("date"));
        documentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        documentList.getItems().clear();
        lists.setAll(setTable());
        documentList.setItems(lists);

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
        document d;
        ObservableList<document> list = FXCollections.observableArrayList();
        try {
            ResultSet result = database.executeQuery("""
                                                     SELECT rd.*,
                                                     CASE WHEN dp.OR IS NOT NULL THEN 'Paid' ELSE 'Pending' END AS payed
                                                     FROM requesteddocs AS rd
                                                     LEFT JOIN document_paid AS dp
                                                     ON rd.id = dp.id
                                                     ORDER BY date_requested DESC;""");
            while (result.next()) {
                d = new document(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9), result.getString(10), result.getString(11), result.getDate(12), result.getString(13));
                if(result.getString(11).equalsIgnoreCase("Indigency")){
                    d.setStats("No Payment");
                }
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void updateTable() {
        lists.setAll(setTable());
        documentList.setItems(lists);
    }

    @FXML
    private void cellSelected(MouseEvent event) {
        showCellSelected();
    }

    @FXML
    private void clear(MouseEvent event) {
        search.setText("");
        documentList.setItems(lists);
    }

    @FXML
    private void edit(ActionEvent event) {
        main main = new main();
        main.overlayWindow("/requestedDocuments/editDoc.fxml", "Update Document");
    }

    @FXML
    private void print(ActionEvent event) {
        if (selected.getCat().equals("Barangay Certification")) {
            new main().overlayWindow("/requestedDocuments/barangayCertificationTemp.fxml", "Barangay Certification");
        }else{
            new main().overlayWindow("/requestedDocuments/businessPermitTemp.fxml", "Business Permit");
        }
    }

    @FXML
    private void payment(ActionEvent event) {
        new main().overlayWindow("/treasury/documentPayment.fxml", "Payment");
    }

    @FXML
    private void search(KeyEvent event) {
        ObservableList<document> filtered = FXCollections.observableArrayList();
        if (!search.getText().isBlank()) {
            for (document record : lists) {
                if (record.getFirstname().toLowerCase().startsWith(search.getText().toLowerCase()) 
                        || record.getMiddlename().toLowerCase().startsWith(search.getText().toLowerCase())
                        || record.getLastname().toLowerCase().startsWith(search.getText().toLowerCase())) {
                    filtered.add(record);
                }
            }
            documentList.setItems(filtered);
        } else {
            documentList.setItems(lists);
        }
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Database database = new Database();
        selected = documentList.getSelectionModel().getSelectedItem();
        if (selected.getCat().equals("Barangay Permit")) {
            PreparedStatement prep = database.insertQuery(String.format("DELETE FROM `permit` WHERE `id` = '%s'", selected.getId()));
            System.out.println(prep.executeUpdate());

        } else if (selected.getCat().equals("Barangay Certification")) {
            PreparedStatement prep = database.insertQuery(String.format("DELETE FROM `certification` WHERE `id` = '%s'", selected.getId()));
            System.out.println(prep.executeUpdate());
        } else {
            System.out.println("No passed");
        }
        updateTable();
        permitView.setVisible(false);
        certificateView.setVisible(false);

    }

    public void showCellSelected() {
        permitView.setVvalue(0);
        certificateView.setVvalue(0);
        Database database = new Database();
        selected = documentList.getSelectionModel().getSelectedItem();
        permitView.setVisible(false);
        certificateView.setVisible(false);
        Ppayment.setDisable(false);
        Cpayment.setDisable(false);
        Pdelete.setDisable(false);
        Cdelete.setDisable(false);
        Cprint.setDisable(true);
        Pprint.setDisable(true);

        if (selected.getStats().equalsIgnoreCase("Paid")) {
            Ppayment.setDisable(true);
            Cpayment.setDisable(true);
            Pdelete.setDisable(true);
            Cdelete.setDisable(true);
            Cprint.setDisable(false);
            Pprint.setDisable(false);
        }else if(selected.getStats().equalsIgnoreCase("No Payment")){
            Ppayment.setDisable(true);
            Cpayment.setDisable(true);
            Cprint.setDisable(false);
            Pprint.setDisable(false);
        }

        if (selected.getCat().equalsIgnoreCase("Barangay Permit")) {
            permitView.setVisible(true);
            try {
                ResultSet result = database.executeQuery(String.format("""
                                                         SELECT `id`, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `purpose`
                                                         FROM `permit`
                                                         WHERE `id` = '%s';""", selected.getId()));
                while (result.next()) {
                    PDocsId.setText(selected.getId());
                    PrqstName.setText(selected.getRfullname());
                    Pname.setText(selected.getFullname());
                    PAddress.setText(result.getString(2));
                    PphoneNumber.setText(result.getString(3));
                    Pemail.setText(result.getString(4));
                    PBusinessName.setText(result.getString(5));
                    PBusinessType.setText(result.getString(6));
                    PbusinessAddress.setText(result.getString(7));
                    Pcategory.setText(selected.getCat());
                    PpayStats.setText(selected.getStats());
                    PDocsType.setText(selected.getDocumentType());
                    PrqstDate.setText(selected.getDate().toString());
                    Ppurpose.setText(result.getString(8));
                    if (PpayStats.getText().equalsIgnoreCase("Pending")) {
                        PpayStats.setStyle("-fx-text-fill: RED;");
                    } else {
                        PpayStats.setStyle("-fx-text-fill: GREEN;");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (selected.getCat().equalsIgnoreCase("Barangay Certification")) {
            certificateView.setVisible(true);
            try {
                ResultSet result = database.executeQuery(String.format("""
                                                         SELECT `id`, `address`, `phone_num`, `email`, `purpose`
                                                         FROM `certification`
                                                         WHERE `id` = '%s';""", selected.getId()));
                while (result.next()) {
                    CDocsId.setText(selected.getId());
                    CrqstName.setText(selected.getRfullname());
                    Cname.setText(selected.getFullname());
                    CAddress.setText(result.getString(2));
                    CphoneNumber.setText(result.getString(3));
                    Cemail.setText(result.getString(4));
                    CDocsType.setText(selected.getDocumentType());
                    Ccategory.setText(selected.getCat());
                    CrqstDate.setText(selected.getDate().toString());
                    CpayStats.setText(selected.getStats());
                    Cpurpose.setText(result.getString(5));
                    if (CpayStats.getText().equalsIgnoreCase("Pending")) {
                        CpayStats.setStyle("-fx-text-fill: RED;");
                    } else {
                        CpayStats.setStyle("-fx-text-fill: GREEN;");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
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
            for (document record : lists) {
                if (record.getDocumentType().equals(docsType.getValue())) {
                    filtered.add(record);
                }
            }
            documentList.setItems(filtered);
        } else {
            documentList.setItems(lists);
        }
    }
}
