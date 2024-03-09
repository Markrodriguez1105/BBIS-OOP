package LogIn;

import assets.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.*;

public class LogInController implements Initializable {

    public static int resident_id;
    public static String user_fname;
    public static String user_mname;
    public static String user_lname;

    @FXML
    private Label indicator;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        indicator.setText("");
        keyPressed(userName);
        keyPressed(password);
    }

    @FXML
    private void userLogIn(ActionEvent event) throws IOException, SQLException {
        passed();
    }

    void keyPressed(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Handle Enter key press here
                passed();
            }
        });
    }

    private void passed() {
        main main = new main();
        Database database = new Database();
        ResultSet result = database.executeQuery(String.format("SELECT * FROM `account` WHERE BINARY `admin_id` = '%s' && BINARY `password` = '%s';", userName.getText(), password.getText()));
        ResultSet result2 = database.executeQuery(String.format("SELECT first_name, middle_name, last_name FROM `resident` WHERE `resident_id` = %s", userName.getText()));
        try {
            if (!userName.getText().isBlank() && !password.getText().isBlank()) {
                if (result.next() && result2.next()) {
                    resident_id = result.getInt(1);
                    user_fname = result2.getString(1);
                    user_mname = result2.getString(2);
                    user_lname = result2.getString(3);
                    main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
                } else {
                    indicator.setText("Wrong Username and Password");
                }
            } else {
                indicator.setText("Please Input Username and Password");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
