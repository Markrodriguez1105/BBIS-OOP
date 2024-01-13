package LogIn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.sql.*;
import main.*;

public class LogInController implements Initializable {
    
    private Label label;
    @FXML
    private ComboBox<String> dropBox;
    @FXML
    private Label indicator;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dropBox.setValue("RESIDENT");
        dropBox.setItems(FXCollections.observableArrayList("RESIDENT", "ADMIN"));
    }

    @FXML
    private void userLogIn(ActionEvent event) throws IOException, SQLException {
        Main main = new Main();
        main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
//        main.changeScene("bbis.dashboard/dashboard.fxml");
//        Database database = new Database();
//        Main main = new Main();
//        Encryption encryp = new Encryption();
//        ResultSet result = database.getFromDatabase(String.format("SELECT * FROM `user_info` WHERE `username` = '%s' AND `password` = '%s'", userName.getText(), password.getText()));
//        if (!userName.getText().isEmpty() || !password.getText().isEmpty()) {
//            indicator.setText("");
//            if (result.next()) {
//                if (result.getBoolean("status")) {
//                    if (result.getInt("token") == encryp.ecryptionToken(dropBox.getSelectionModel().getSelectedItem())) {
//                        switch (result.getInt("token")) {
//                            case 1 :
//                                ResidentController.residentID = result.getInt("residentID");
//                                main.changeScene("Resident.fxml");
//                                break;
//                            case 2 :
//                                main.changeScene("Admin.fxml");
//                                break;
//                            default :
//                                System.out.println("System Error, Please Restart The Program!");
//                        }
//                    }
//                    else{
//                        indicator.setText("Wrong Log In As, Username and Password!");
//                    }
//                }
//                else {
//                    indicator.setText("Deactiviated Account!");
//                }
//            }
//            else {
//                indicator.setText("Wrong Log In As, Username and Password!");
//            }
//        }
//        else {
//            indicator.setText("Please Input Your Username and Password!");
//        }

    }
}
