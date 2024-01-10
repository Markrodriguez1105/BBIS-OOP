
package residentRecord.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import residentrecord.Resident;
import residentrecord.Connect;

/**
 *
 * @author Graham Russell
 */
public class ResidentRecordViewController implements Initializable {

    @FXML
    private Button addResidentBtn;
    @FXML
    private Button updateInfoBtn;
    @FXML
    private Button deleteResidentBtn;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Resident> ResidentInfoTableview;
    @FXML
    private TableColumn<Resident, String> tableColumnName;
    @FXML
    private TableColumn<Resident, String> tableColumnGender;
    @FXML
    private TableColumn<Resident, String> tableColumnAge;
    @FXML
    private TableColumn<Resident, String> tableColumnBirthdate;
    @FXML
    private TableColumn<Resident, String> tableColumnContactNo;
    
    ResidentFormController residentForm;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;


    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userShowData();

    }    

    @FXML
    private void addResident(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ResidentForm.fxml"));
            Parent root = loader.load();

             residentForm = loader.getController();
             residentForm.setResidentForm(this);

            Stage stage = new Stage();
            stage.setTitle("Add Resident");
            stage.setScene(new Scene(root));

            stage.show();
            userShowData();

        } catch (IOException e) {
        }
    }

    @FXML
    private void updateResidentInfo(ActionEvent event) {
    }

    @FXML
    private void DeleteResident(ActionEvent event) {
         connect = Connect.connectDB();

        try {

            Resident rD = ResidentInfoTableview.getSelectionModel().getSelectedItem();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE  "
                    + rD.getFullName() + " to your database?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM resident_informations WHERE fname = ? AND mname = ? AND lname = ?";

                prepare = connect.prepareStatement(deleteData);
                prepare.setString(1, rD.getfName());
                prepare.setString(2, rD.getmName());
                prepare.setString(3, rD.getlName());

                prepare.executeUpdate();

                userShowData();
                updateTableView();

                successMessage("Successfully deleted the resident");
            } else {
                errorMessage("Cancelled");
            }

        } catch (SQLException e) {
        }
    }

    @FXML
    private void searchResidentRecord(ActionEvent event) {
    }

    public ObservableList<Resident> residentList() {

        ObservableList listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM resident_informations";

        connect = Connect.connectDB();

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            Resident residentDatas;

            while (result.next()) {

                residentDatas = new Resident(result.getString("fname"), result.getString("mname"),
                        result.getString("lname"), result.getString("gender"), result.getInt("age"), result.getDate("birthdate"));
                        

                listData.add(residentDatas);
            }

        } catch (SQLException e) {
        }
        return listData;
    }

    public ObservableList<Resident> residentData;

    public void userShowData() {
        residentData = residentList();

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableColumnBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        ResidentInfoTableview.setItems(residentData);

    }

    public void SelectResident() {

        Resident resident = (Resident) ResidentInfoTableview.getSelectionModel().getSelectedItem();
        int num = ResidentInfoTableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
        }

    }

    public void updateTableView() {
        
        residentData.clear();
        ResidentInfoTableview.getItems().clear();
        ResidentInfoTableview.getItems().addAll(residentList());
        ResidentInfoTableview.refresh();
    }  
    
    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void successMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
