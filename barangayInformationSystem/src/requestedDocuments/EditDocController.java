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
    private ComboBox<String> suffix;
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

        suffix.setValue("");
        suffix.getItems().add("");
        suffix.getItems().add("Jr.");
        suffix.getItems().add("Sr.");

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
        if (isFieldEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Please Fill in all the black");
            alert.showAndWait();
        } else if (isText(fname.getText()) || isText(mname.getText()) || isText(lname.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Error at full name section");
            alert.showAndWait();
        } else if (isEmail(email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Error at Email Section");
            alert.showAndWait();
        } else if (isPhoneNum(phoneNum.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Error at Phone Number Section");
            alert.showAndWait();
        } else if (isText(municipal.getText()) || isText(province.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Error at Address Section");
            alert.showAndWait();
        } else {
            switch (docsCat.getValue()) {
                case "Barangay Permit": {
                    try {
                        PreparedStatement prep = database.insertQuery("UPDATE `permit` SET `firstName` = ?,`middleName` = ?,`lastName` = ?,`suffix` = ?,`address` = ?,`phone_num` = ?,`email` = ?,`business_name` = ?,`business_type` = ?,`business_address` = ?,`permit_type` = ?,`date_requested` = ?,`purpose` = ? WHERE `id` = ?;");
                        prep.setString(1, fname.getText());
                        prep.setString(2, mname.getText());
                        prep.setString(3, lname.getText());
                        prep.setString(4, suffix.getValue());
                        prep.setString(5, address());
                        prep.setString(6, phoneNum.getText());
                        prep.setString(7, email.getText());
                        prep.setString(8, businessName.getText());
                        prep.setString(9, businessType.getValue());
                        prep.setString(10, businessAddress.getText());
                        prep.setString(11, docsType.getValue());
                        prep.setString(12, (ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                        prep.setString(13, purpose.getText());
                        prep.setString(14, selected.getId());
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
                        PreparedStatement prep = database.insertQuery("UPDATE `certification` SET `firstName` = ?, `middleName` = ?, `lastName` = ?,`suffix` = ?, `address` = ?, `phone_num` = ?, `email` = ?, `certification_type` = ?, `date_requested` = ?, `purpose` = ? WHERE id = ?");
                        prep.setString(1, fname.getText());
                        prep.setString(2, mname.getText());
                        prep.setString(3, lname.getText());
                        prep.setString(4, suffix.getValue());
                        prep.setString(5, address());
                        prep.setString(6, phoneNum.getText());
                        prep.setString(7, email.getText());
                        prep.setString(8, docsType.getValue());
                        prep.setString(9, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                        prep.setString(10, purpose.getText());
                        prep.setString(11, selected.getId());
                        int rowsAffected = prep.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User inserted successfully!");
                        } else {
                            System.out.println("Insertion failed.");
                        }
                    } catch (SQLException e) {
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
        }
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
                    docsCat.setValue(selected.getCat());
                    fname.setText(selected.getFirstname());
                    mname.setText(selected.getMiddlename());
                    lname.setText(selected.getLastname());
                    suffix.setValue(selected.getSuffix());
                    String[] address = result.getString(7).split(",");
                    zone.setValue(address[0].replace("Zone", "").trim());
                    barangay.setText(address[1].trim());
                    municipal.setText(address[2].trim());
                    province.setText(address[3].trim());
                    phoneNum.setText(result.getString(8));
                    email.setText(result.getString(9));
                    businessName.setText(result.getString(10));
                    businessType.setValue(result.getString(11));
                    businessAddress.setText(result.getString(12));
                    docsType.setValue(selected.getDocumentType());
                    purpose.setText(result.getString(15));
                }
            } else if (selected.getCat().equals("Barangay Certification")) {
                ResultSet result = database.executeQuery(String.format("SELECT * FROM `certification` WHERE id = '%s'", selected.getId()));
                while (result.next()) {
                    docsCat.setValue(selected.getCat());
                    fname.setText(selected.getFirstname());
                    mname.setText(selected.getMiddlename());
                    lname.setText(selected.getLastname());
                    suffix.setValue(selected.getSuffix());
                    String[] address = result.getString(7).split(",");
                    zone.setValue(address[0].replace("Zone", "").trim());
                    barangay.setText(address[1].trim());
                    municipal.setText(address[2].trim());
                    province.setText(address[3].trim());
                    phoneNum.setText(result.getString(8));
                    email.setText(result.getString(9));
                    docsType.setValue(selected.getDocumentType());
                    purpose.setText(result.getString(12));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean isFieldEmpty() {
        if (fname.getText().isEmpty() || email.getText().isEmpty() || phoneNum.getText().isEmpty()
                || zone.getValue() == null || barangay.getText().isEmpty() || purpose.getText().isEmpty()
                || lname.getText().isEmpty() || municipal.getText().isEmpty() || province.getText().isEmpty() || docsCat.getValue() == null
                || docsType.getValue().isBlank()) {
            if (docsType.getValue() != null && docsCat.getValue().equals("Barangay Permit") && (businessName.getText().isEmpty() || businessAddress.getText().isEmpty() || businessType.getValue() == null)) {
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean isText(String input) {
        // Regular expression for a valid text
        return !(input.matches("^[a-zA-Z\\s]+$") || input.isBlank());
    }

    private boolean isNum(String input) {
        // Regular expression for a valid number
        return !input.matches("^[0-9]+$");
    }

    private boolean isPhoneNum(String input) {
        // Regular expression for a valid Phone number
        if (input.matches("^09\\d{9}$")) {
            return false;
        } else if (input.matches("^\\+63\\d{10}$")) {
            return false;
        }
        return true;
    }

    private boolean isEmail(String input) {
        // Regular expression for a valid number
        return !input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
