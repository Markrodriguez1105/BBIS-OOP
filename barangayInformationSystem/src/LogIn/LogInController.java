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
import main.*;

public class LogInController implements Initializable {

    public static int resident_id;
    public static String user_fname;
    public static String user_mname;
    public static String user_lname;
    public static String position;

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
        try {
            if (!userName.getText().isBlank() && !password.getText().isBlank()) {
                ResultSet result = database.executeQuery(String.format("SELECT a.admin_id, a.password, o.first_name, o.middle_name, o.last_name , o.position FROM `admin_user` AS a LEFT JOIN official AS o ON a.admin_id = o.official_id WHERE BINARY `username` = '%s' && BINARY `password` = '%s';", userName.getText(), password.getText()));
                if (result.next()) {
                    resident_id = result.getInt(1);
                    user_fname = result.getString(6);
                    user_mname = result.getString(4);
                    user_lname = result.getString(5) + ", " + result.getString(3);
                    position = result.getString(6);
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
