/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import assets.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import main.main;

/**
 * FXML Controller class
 *
 * @author HELLO MARK
 */
public class EditDocController implements Initializable {

    document selected;

    @FXML
    private RowConstraints businessSection;
    @FXML
    private TextField fname;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNum;
    @FXML
    private ComboBox<String> zone;
    @FXML
    private TextField barangay;
    @FXML
    private TextArea purpose;
    @FXML
    private TextField mname;
    @FXML
    private TextField lname;
    @FXML
    private TextField suffix;
    @FXML
    private TextField municipal;
    @FXML
    private TextField province;
    @FXML
    private ComboBox<String> docsCat;
    @FXML
    private VBox businessDisplay1;
    @FXML
    private TextField businessName;
    @FXML
    private VBox businessDisplay2;
    @FXML
    private TextField businessAddress;
    @FXML
    private VBox businessDisplay3;
    @FXML
    private ComboBox<String> businessType;
    @FXML
    private VBox showDocsType;
    @FXML
    private ComboBox<String> docsType;

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
        businessSection.setMaxHeight(0);
        businessSection.setMinHeight(0);
        businessSection.setPrefHeight(0);
        businessDisplay1.setVisible(false);
        businessDisplay2.setVisible(false);
        businessDisplay3.setVisible(false);
        //Initialized ComboBox

        zone.getItems().add("1");
        zone.getItems().add("2");
        zone.getItems().add("3");
        zone.getItems().add("4");
        zone.getItems().add("5");
        zone.getItems().add("6");
        zone.getItems().add("7");

        loadDocument();
        showDocumentType();

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

    @FXML
    private void cancel(ActionEvent event) {
        main main = new main();
        main.closeWindow(event);
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        Database database = new Database();
        RequestedDocumentController request = new RequestedDocumentController();
        switch (docsCat.getValue()) {
            case "Barangay Permit": {
                try {
                    PreparedStatement prep = database.insertQuery("UPDATE `permit` SET `firstName` = ?,`middleName` = ?,`lastName` = ?,`address` = ?,`phone_num` = ?,`email` = ?,`business_name` = ?,`business_type` = ?,`business_address` = ?,`permit_type` = ?,`date_requested` = ?,`purpose` = ? WHERE `id` = ?;");
                    prep.setString(1, fname.getText());
                    prep.setString(2, mname.getText());
                    prep.setString(3, lname.getText());
                    prep.setString(4, address());
                    prep.setString(5, phoneNum.getText());
                    prep.setString(6, email.getText());
                    prep.setString(7, businessName.getText());
                    prep.setString(8, businessType.getValue());
                    prep.setString(9, businessAddress.getText());
                    prep.setString(10, docsType.getValue());
                    prep.setString(11, (ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    prep.setString(12, purpose.getText());
                    prep.setString(13, selected.getId());
                    int rowsAffected = prep.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                    } else {
                        System.out.println("Insertion failed.");
                    }

                } catch (NumberFormatException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Message");
                alert.setHeaderText("");
                alert.setContentText("Your changes have been saved");
                alert.showAndWait();
                request.updateTable();
                main main = new main();
                main.closeWindow(event);
                break;
            }
            case "Barangay Certification": {
                try {
                    PreparedStatement prep = database.insertQuery("UPDATE `certification` SET `firstName` = ?, `middleName` = ?, `lastName` = ?, `address` = ?, `phone_num` = ?, `email` = ?, `certification_type` = ?, `date_requested` = ?, `purpose` = ? WHERE id = ?");
                    prep.setString(1, fname.getText());
                    prep.setString(2, mname.getText());
                    prep.setString( 3, lname.getText());
                    prep.setString(4, address());
                    prep.setString(5, phoneNum.getText());
                    prep.setString(6, email.getText());
                    prep.setString(7, docsType.getValue());
                    prep.setString(8, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    prep.setString(9, purpose.getText());
                    prep.setString(10, selected.getId());
                    int rowsAffected = prep.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                    } else {
                        System.out.println("Insertion failed.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Message");
                alert.setHeaderText("");
                alert.setContentText("Your changes have been saved");
                alert.showAndWait();
                request.updateTable();
                main main = new main();
                main.closeWindow(event);
                break;
            }
        }
        main main = new main();
        main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
    }

    String address() {
        return "Zone " + zone.getValue() + ", " + barangay.getText() + ", " + municipal.getText() + ", " + province.getText();
    }

    void showDocumentType() {
        Database database = new Database();
        docsType.getItems().clear();
        if (docsCat.getValue().equals("Barangay Permit")) {
            businessSection.setMaxHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setMinHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setPrefHeight(Region.USE_COMPUTED_SIZE);
            businessDisplay1.setVisible(true);
            businessDisplay2.setVisible(true);
            businessDisplay3.setVisible(true);

        } else {
            businessSection.setMaxHeight(0);
            businessSection.setMinHeight(0);
            businessSection.setPrefHeight(0);
            businessDisplay1.setVisible(false);
            businessDisplay2.setVisible(false);
            businessDisplay3.setVisible(false);
        }
        if (!selected.getStats().equals("Paid")) {
            docsType.setDisable(false);
            if (docsCat.getValue().equals("Barangay Permit")) {
                docsType.getItems().addAll(setComboBox(database.executeQuery("SELECT `permit_type` FROM `permit` GROUP BY 1 ORDER BY 1;")));
                businessType.getItems().addAll(setComboBox(database.executeQuery("SELECT `business_type` FROM `business` GROUP BY 1 ORDER BY 1;")));
            } else {
                docsType.getItems().addAll(setComboBox(database.executeQuery("SELECT `certification_type` FROM `certification` GROUP BY 1 ORDER BY 1;")));
            }
        }
    }

    void loadDocument() {
        selected = RequestedDocumentController.selected;
        Database database = new Database();
        try {
            if (selected.getCat().equals("Barangay Permit")) {
                ResultSet result = database.executeQuery(String.format("SELECT * FROM `permit` WHERE id = '%s'", selected.getId()));
                while (result.next()) {
                    docsCat.setValue("Barangay Permit");
                    fname.setText(result.getString(3));
                    mname.setText(result.getString(4));
                    lname.setText(result.getString(5));
                    String[] address = result.getString(6).split(",");
                    zone.setValue(address[0].replace("Zone", "").trim());
                    barangay.setText(address[1].trim());
                    municipal.setText(address[2].trim());
                    province.setText(address[3].trim());
                    phoneNum.setText(result.getString(7));
                    email.setText(result.getString(8));
                    businessName.setText(result.getString(9));
                    businessType.setValue(result.getString(10));
                    businessAddress.setText(result.getString(11));
                    docsType.setValue(result.getString(12));
                    purpose.setText(result.getString(14));
                }
            } else if (selected.getCat().equals("Barangay Certification")) {
                ResultSet result = database.executeQuery(String.format("SELECT * FROM `certification` WHERE id = '%s'", selected.getId()));
                while (result.next()) {
                    docsCat.setValue("Barangay Certification");
                    fname.setText(result.getString(3));
                    mname.setText(result.getString(4));
                    lname.setText(result.getString(5));
                    String[] address = result.getString(6).split(",");
                    zone.setValue(address[0].replace("Zone", "").trim());
                    barangay.setText(address[1].trim());
                    municipal.setText(address[2].trim());
                    province.setText(address[3].trim());
                    phoneNum.setText(result.getString(7));
                    email.setText(result.getString(8));
                    docsType.setValue(result.getString(9));
                    purpose.setText(result.getString(11));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
