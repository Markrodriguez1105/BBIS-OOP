/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package treasury;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.*;
import requestedDocuments.*;
import assets.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author HELLO MARK
 */
public class DocumentPaymentController implements Initializable {

    static document selected;

    @FXML
    private TextField name;
    @FXML
    private TextField dateRqst;
    @FXML
    private TextField docCat;
    @FXML
    private TextField docType;
    @FXML
    private TextField tax;
    @FXML
    private TextField cost;
    @FXML
    private TextField amount;
    @FXML
    private TextField amountPay;
    @FXML
    private HBox showChange;
    @FXML
    private TextField change;
    @FXML
    private Button nextShow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showChange.setVisible(false);
        nextShow.setDisable(true);

        selected = RequestedDocumentController.selected;

        name.setText(selected.getFullName());
        dateRqst.setText(selected.getDate().toString());
        docType.setText(selected.getDocumentType());
        docCat.setText(selected.getCat());

        Amount();
        change();
    }

    @FXML
    private void cancel(ActionEvent event) {
        new main().closeWindow(event);
    }

    @FXML
    private void next(ActionEvent event) {
        Database database = new Database();
        if (Float.parseFloat(change.getText()) > 0) {
            if (selected.getCat().equals("Barangay Permit")) {
                try {
                    PreparedStatement p = database.insertQuery("INSERT INTO `permittreasury`(`id`, `document_id`, `tax`, `document_cost`, `amount_pay`, `date_issued`) VALUES (?,?,?,?,?,?)");
                    p.setString(1, ranNum("TRPT"));
                    p.setString(2, selected.getId());
                    p.setFloat(3, Float.parseFloat(tax.getText()));
                    p.setFloat(4, Float.parseFloat(cost.getText()));
                    p.setFloat(5, Float.parseFloat(amountPay.getText()));
                    p.setString(6, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    int rowsAffected = p.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("System Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Would you want to print the document?");
                        
                        ButtonType buttonTypeYes = new ButtonType("Print");
                        ButtonType buttonTypeNo = new ButtonType("No");

                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == buttonTypeYes) {
                            System.out.println("User clicked Yes");
                            new main().closeWindow(event);
                        } else {
                            new main().closeWindow(event);
                        }
                    } else {
                        System.out.println("Insertion failed.");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    PreparedStatement p = database.insertQuery("INSERT INTO `certificationtreasury` (`id`, `document_id`, `tax`, `document_cost`, `amount_pay`, `date_issued`) VALUES (?,?,?,?,?,?)");
                    p.setString(1, ranNum("TRPT"));
                    p.setString(2, selected.getId());
                    p.setFloat(3, Float.parseFloat(tax.getText()));
                    p.setFloat(4, Float.parseFloat(cost.getText()));
                    p.setFloat(5, Float.parseFloat(amountPay.getText()));
                    p.setString(6, String.valueOf(ZonedDateTime.now(java.time.ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    int rowsAffected = p.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User inserted successfully!");
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("System Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Would you want to print the document?");
                        
                        ButtonType buttonTypeYes = new ButtonType("Print");
                        ButtonType buttonTypeNo = new ButtonType("No");

                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == buttonTypeYes) {
                            System.out.println("User clicked Yes");
                            new main().closeWindow(event);
                        } else {
                            new main().closeWindow(event);
                        }
                    } else {
                        System.out.println("Insertion failed.");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("System Message");
            alert.setHeaderText(null);
            alert.setContentText("Insuffient Amount Pay.");
            alert.showAndWait();
        }
    }

    void Amount() {
        tax.textProperty().addListener((observable) -> {
            if (tax.getText().length() != 0 && tax.getText().charAt(0) == '.') {
                tax.setText("0.");
            }
            if (!tax.getText().isBlank() && !cost.getText().isBlank()) {
                float amount1 = Float.parseFloat(tax.getText());
                float amount2 = Float.parseFloat(cost.getText());
                amount.setText(String.valueOf(amount1 + amount2));
            } else {
                amount.setText(null);
            }
        });
        cost.textProperty().addListener((observable) -> {
            if (cost.getText().length() != 0 && cost.getText().charAt(0) == '.') {
                cost.setText("0.");
            }
            if (!tax.getText().isBlank() && !cost.getText().isBlank()) {
                float amount1 = Float.parseFloat(tax.getText());
                float amount2 = Float.parseFloat(cost.getText());
                amount.setText(String.valueOf(amount1 + amount2));
            } else {
                amount.setText(null);
            }
        });
    }

    void change() {
        amountPay.textProperty().addListener((observable) -> {
            if (!amountPay.getText().isBlank() && !amount.getText().isBlank()) {
                float amount1 = Float.parseFloat(amountPay.getText());
                float amount2 = Float.parseFloat(amount.getText());
                showChange.setVisible(true);
                nextShow.setDisable(false);
                change.setText(String.valueOf(amount1 - amount2));
            }
        });
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

}
