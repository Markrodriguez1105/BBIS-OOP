
package residentRecord.FXML;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TableColumn<Resident, String> tableColumnGender2;
    @FXML
    private TableColumn<Resident, String> tableColumnGender21;
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateResidentInfo(ActionEvent event) {
    }

    @FXML
    private void DeleteResident(ActionEvent event) {
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

            Resident residentData;

            while (result.next()) {

                residentData = new Resident(result.getString("fname"), result.getString("mname"), result.getString("lname"), result.getString("gender"), result.getInt("age"));

                listData.add(residentData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<Resident> residentData;

    public void userShowData() {
        residentData = residentList();

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableColumnGender2.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableColumnGender21.setCellValueFactory(new PropertyValueFactory<>("birhtdate"));

        ResidentInfoTableview.setItems(residentData);

    }

    @FXML
    public void userSelectData() {

        Resident residentData = (Resident) ResidentInfoTableview.getSelectionModel().getSelectedItem();
        int num = ResidentInfoTableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

    }

    public void updateTableView() {
        
        residentData.clear();
        ResidentInfoTableview.getItems().clear();
        ResidentInfoTableview.getItems().addAll(residentList());
        ResidentInfoTableview.refresh();
    }  
}
