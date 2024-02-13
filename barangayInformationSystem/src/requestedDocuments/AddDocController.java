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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import main.main;
import LogIn.LogInController;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;
import javafx.scene.control.Alert;

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
    private void save(ActionEvent event) {
        Database database = new Database();
        RequestedDocumentController request = new RequestedDocumentController();
        if (docsCat.getValue().equals("Barangay Permit")) {
            try {
                PreparedStatement prep = database.insertQuery("INSERT INTO `permit`(`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `permit_type`, `date_requested`, `purpose` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                prep.setInt(1, ranNum());
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
                prep.setString(13, String.valueOf(LocalDate.now()));
                prep.setString(14, purpose.getText());
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
            alert.setContentText("Successfuly Request");
            alert.showAndWait();
            request.updateTable();
            main main = new main();
            main.closeWindow(event);
        } else if (docsCat.getValue().equals("Barangay Certification")) {
            try {
                PreparedStatement prep = database.insertQuery("INSERT INTO `certification`(`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `certification_type`, `date_requested`, `purpose`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                prep.setInt(1, ranNum());
                prep.setInt(2, LogInController.resident_id);
                prep.setString(3, fname.getText());
                prep.setString(4, mname.getText());
                prep.setString(5, lname.getText());
                prep.setString(6, address());
                prep.setString(7, phoneNum.getText());
                prep.setString(8, email.getText());
                prep.setString(9, docsType.getValue());
                prep.setString(10, String.valueOf(LocalDate.now()));
                prep.setString(11, purpose.getText());
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
            alert.setContentText("Successfuly Request");
            alert.showAndWait();
            request.updateTable();
            main main = new main();
            main.closeWindow(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System Message");
            alert.setHeaderText("");
            alert.setContentText("Please Select Document Type");
            alert.showAndWait();
        }
    }

    int ranNum() {
        return (int) Math.round(Math.random() * (9999 - 1000 + 1) - 1000);
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
        docsType.getItems().clear();
        if (docsCat.getValue().equals("Barangay Permit")) {
            businessSection.setMaxHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setMinHeight(Region.USE_COMPUTED_SIZE);
            businessSection.setPrefHeight(Region.USE_COMPUTED_SIZE);
            businessDisplay1.setVisible(true);
            businessDisplay2.setVisible(true);
            businessDisplay3.setVisible(true);
            docsType.getItems().addAll(setComboBox(database.executeQuery("SELECT `permit_type` FROM `permit` GROUP BY 1 ORDER BY 1;")));
            businessType.getItems().addAll(setComboBox(database.executeQuery("SELECT `business_type` FROM `business` GROUP BY 1 ORDER BY 1;")));

        } else {
            businessSection.setMaxHeight(0);
            businessSection.setMinHeight(0);
            businessSection.setPrefHeight(0);
            businessDisplay1.setVisible(false);
            businessDisplay2.setVisible(false);
            businessDisplay3.setVisible(false);
            docsType.getItems().addAll(setComboBox(database.executeQuery("SELECT `certification_type` FROM `certification` GROUP BY 1 ORDER BY 1;")));
        }
    }
}
