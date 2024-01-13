package businessRecord;

import businessRecord.Record;
import businessRecord.DbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

public class AddFormController implements Initializable {

    @FXML
    private AnchorPane addForm;

    @FXML
    private TextField addressFid;

    @FXML
    private TextField costFid;
    
    @FXML
    private Button submit;

    @FXML
    private TextField incomeFid;

    @FXML
    private TextField nameFid;

    @FXML
    private TextField typeFid;

    @FXML
    private TextField idFid;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement;
    private boolean update;
    int recordId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize method body if needed
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void submit(MouseEvent event) {
//        connection = DbConnect.getConnection();
//
//        String idText = idFid.getText();
//        String Name = nameFid.getText();
//        String Address = addressFid.getText();
//        String type = typeFid.getText();
//        String IncomeText = incomeFid.getText();
//        String CostText = costFid.getText();
//
//        // Check if any field is empty
//        if (idText.isEmpty() || Name.isEmpty() || Address.isEmpty() || type.isEmpty() || IncomeText.isEmpty() || CostText.isEmpty()) {
//            showAlert("Please Fill All DATA");
//            
//            
//        } else {
//            try {
//                // Parse income and cost values
//                int id = Integer.parseInt(idText);
//                int income = Integer.parseInt(IncomeText);
//                int cost = Integer.parseInt(CostText);
//
//                // Continue with the logic
//                getQuery();
//                insert();
//            } catch (NumberFormatException e) {
//                showAlert("Invalid numeric input for Income or Cost");
//            }
//        }
    }

    private void getQuery() {
    if (!update) {
        query = "INSERT INTO `record`( `Name`, `Address`, `Type`, `Income`, `Cost`) VALUES (?,?,?,?,?)";
    } else {
        query = "UPDATE `record` SET "
                + "`Name`=?,"
                + "`Address`=?,"
                + "`Type`=?,"
                + "`Income`=?,"
                + "`Cost`=? WHERE id = '" + recordId + "'";
    }
}


    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);

            
            preparedStatement.setString(1, nameFid.getText());
            preparedStatement.setString(2, addressFid.getText());
            preparedStatement.setString(3, typeFid.getText());
            preparedStatement.setInt(4, Integer.parseInt(incomeFid.getText()));
            preparedStatement.setInt(5, Integer.parseInt(costFid.getText()));
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddFormController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Error executing SQL query: " + ex.getMessage());
        }
    }

    public void setTextField(int id, String name, String address, String type, int income, int cost) {
        recordId = id;
        nameFid.setText(name);
        addressFid.setText(address);
        typeFid.setText(type);
        incomeFid.setText(Integer.toString(income));
        costFid.setText(Integer.toString(cost));
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}


