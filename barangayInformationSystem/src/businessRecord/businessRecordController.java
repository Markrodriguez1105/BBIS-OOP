package businessrecord;

import businessrecord.data.Record;

import helpers.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class businessRecordController implements Initializable {

    @FXML
    private TextField Search;

    @FXML
    private TableView<Record> recordsTable;

    @FXML
    private Button getAddView;

    @FXML
    private TableColumn<Record, String> addressCol;

    @FXML
    private TableColumn<Record, String> costCol;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Record, String> idCol;

    @FXML
    private TableColumn<Record, String> incomeCol;

    @FXML
    private TableColumn<Record, String> nameCol;

    @FXML
    private AnchorPane recordView;

    @FXML
    private TableColumn<Record, String> typeCol;

    @FXML
    private Button update;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Record record = null;

    ObservableList<Record> RecordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        loadDate();
    }

    @FXML
    public void Search(ActionEvent event) {
        String searchTerm = Search.getText();

        try {
            RecordList.clear();

            query = "SELECT * FROM `record` WHERE Name LIKE ?";
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    RecordList.add(new Record(
                            resultSet.getInt("Id"),
                            resultSet.getString("Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Type"),
                            resultSet.getInt("Income"),
                            resultSet.getInt("Cost")));
                }   recordsTable.setItems(RecordList);
                // Close the ResultSet, PreparedStatement, and Connection
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        try {
            record = recordsTable.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `record` WHERE id =" + record.getId();
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/businessrecord/addForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(BusinessRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        try {
            RecordList.clear();

            query = "SELECT * FROM `record`";
            connection = DbConnect.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RecordList.add(new Record(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Type"),
                        resultSet.getInt("Income"),
                        resultSet.getInt("Cost")));
            }
            recordsTable.setItems(RecordList);

        } catch (SQLException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void update(ActionEvent event) {
        record = recordsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addForm.fxml"));
        try {
            Parent parent = loader.load();
            AddFormController addFormController = loader.getController();
            addFormController.setUpdate(true);
            addFormController.setTextField(record.getId(), record.getName(),
                    record.getAddress(), record.getType(), record.getIncome(), record.getCost());
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(businessRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDate() {
        connection = DbConnect.getConnection();
       

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("Income"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));
    }

    @FXML
    private void dashboardClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
    }

    @FXML
    private void barangayInfoClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/barangayInformation/barangayInformation.fxml", "Barangay Information");
    }

    @FXML
    private void barangayOfficialClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/barangayOfficial/barangayOfficial.fxml", "Barangay Official");
    }

    @FXML
    private void residentRecordClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/residentRecord/residentRecord.fxml", "Resident Record");
    }

    @FXML
    private void businessRecordClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/businessRecord/businessRecord.fxml", "Business Record");
    }

    @FXML
    private void householdRecordClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/householdRecord/householdRecord.fxml", "Household Record");
    }

    @FXML
    private void requestedDocsClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
    }

    @FXML
    private void treasuryClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/treasury/treasury.fxml", "Treasury");
    }

    @FXML
    private void reportsClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/reports/reports.fxml", "Reports");
    }

    @FXML
    private void logOutClick(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");
    }
}
