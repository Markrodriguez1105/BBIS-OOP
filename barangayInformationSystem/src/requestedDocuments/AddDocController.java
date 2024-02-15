/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package requestedDocuments;

import LogIn.LogInController;
import assets.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
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
public class AddDocController implements Initializable {

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
    private ComboBox<String> docsType;
    @FXML
    private RowConstraints businessSection;
    @FXML
    private VBox showDocsType;
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        businessSection.setMaxHeight(0);
        businessSection.setMinHeight(0);
        businessSection.setPrefHeight(0);
        businessDisplay1.setVisible(false);
        businessDisplay2.setVisible(false);
        businessDisplay3.setVisible(false);
        showDocsType.setDisable(true);
        //Initialized ComboBox
        docsCat.getItems().add("Barangay Certification");
        docsCat.getItems().add("Barangay Permit");

        zone.getItems().add("1");
        zone.getItems().add("2");
        zone.getItems().add("3");
        zone.getItems().add("4");
        zone.getItems().add("5");
        zone.getItems().add("6");
        zone.getItems().add("7");

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
                    PreparedStatement prep = database.insertQuery("INSERT INTO `permit`(`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `permit_type`, `date_requested`, `purpose`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    prep.setString(1, ranNum("PRMT"));
                    prep.setInt(2, LogInController.resident_id);
                    prep.setString(3, fname.getText());
                    prep.setString(4, mname.getText());
                    prep.setString(5, lname.getText());
                    prep.setString(6, address());
                    prep.setString(7, phoneNum.getText());
                    prep.setString(8, email.getText());
                    prep.setString(9, businessName.getText());
                    prep.setString(10, businessType.getValue());
                    prep.setString(11, businessAddress.getText());
                    prep.setString(12, docsType.getValue());
                    prep.setString(13, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    prep.setString(14, purpose.getText());
                    int rowsAffected = prep.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                    } else {
                        System.out.println("Insertion failed.");
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System Message");
                    alert.setHeaderText("");
                    alert.setContentText("New Barangay Permit added successfully.");
                    alert.showAndWait();
                    request.updateTable();
                    main main = new main();
                    main.closeWindow(event);

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("System Message");
                    alert.setHeaderText("");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                break;
            }
            case "Barangay Certification": {
                try {
                    PreparedStatement prep = database.insertQuery("INSERT INTO `certification`(`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `certification_type`, `date_requested`, `purpose`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                    prep.setString(1, ranNum("CTFT"));
                    prep.setInt(2, LogInController.resident_id);
                    prep.setString(3, fname.getText());
                    prep.setString(4, mname.getText());
                    prep.setString(5, lname.getText());
                    prep.setString(6, address());
                    prep.setString(7, phoneNum.getText());
                    prep.setString(8, email.getText());
                    prep.setString(9, docsType.getValue());
                    prep.setString(10, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    prep.setString(11, purpose.getText());
                    int rowsAffected = prep.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                    } else {
                        System.out.println("Insertion failed.");
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System Message");
                    alert.setHeaderText("");
                    alert.setContentText("New Barangay Certificate added successfully.");
                    alert.showAndWait();
                    request.updateTable();
                    main main = new main();
                    main.closeWindow(event);
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("System Message");
                    alert.setHeaderText("");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                break;
            }
            default: {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Message");
                alert.setHeaderText("");
                alert.setContentText("Please Select Document Type");
                alert.showAndWait();
                break;
            }
        }
    }

    String ranNum(String initial) {
        Database database = new Database();
        String genId = initial + String.valueOf(LocalDate.now().getYear()).substring(2) + "-" + (int) Math.round(Math.random() * (99999 - 10000 + 1) + 10000);
        try {
            ResultSet result = database.executeQuery(String.format("SELECT * FROM `requesteddocs` WHERE id = '%s'", genId));
            while (result.next()) {
                ranNum(initial);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return genId;
    }

    String address() {
        return "Zone " + zone.getValue() + ", " + barangay.getText() + ", " + municipal.getText() + ", " + province.getText();
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
    private void docsCatSelected(ActionEvent event) {
        Database database = new Database();
        showDocsType.setDisable(false);
        if (docsCat.getValue().equals("Barangay Permit")) {
            businessSection.setMaxHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setMinHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setPrefHeight(Region.USE_COMPUTED_SIZE);
            businessDisplay1.setVisible(true);
            businessDisplay2.setVisible(true);
            businessDisplay3.setVisible(true);
            docsType.setItems(setComboBox(database.executeQuery("SELECT `permit_type` FROM `permit` GROUP BY 1 ORDER BY 1;")));
            businessType.setItems(setComboBox(database.executeQuery("SELECT `business_type` FROM `business` GROUP BY 1 ORDER BY 1;")));

        } else {
            businessSection.setMaxHeight(0);
            businessSection.setMinHeight(0);
            businessSection.setPrefHeight(0);
            businessDisplay1.setVisible(false);
            businessDisplay2.setVisible(false);
            businessDisplay3.setVisible(false);
            docsType.setItems(setComboBox(database.executeQuery("SELECT `certification_type` FROM `certification` GROUP BY 1 ORDER BY 1;")));
        }
    }
}
