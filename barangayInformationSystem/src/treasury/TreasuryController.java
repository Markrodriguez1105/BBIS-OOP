
package treasury;
import main.main;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;



public class TreasuryController implements Initializable {

    @FXML
    private TableColumn<Cedula, String> full_name;
    @FXML
    private TableColumn<Cedula, String> age;
    @FXML
    private TableColumn<Cedula, String> gender;
    @FXML
    private TableColumn<Cedula, String> residensy_status;
    @FXML
    private TableColumn<Cedula, String> income;
    @FXML
    private TableColumn<Cedula, String> date_issued;
    @FXML
    private TableColumn<Cedula, String> purpose;
    @FXML
    private TableColumn<Cedula, String> communityTax;
    @FXML
    private TableColumn<Cedula, String> addcommTax;
    @FXML
    private TableColumn<Cedula, String> total;
    @FXML
    private Button getAdd;
    @FXML
    private Button getPrint;
    @FXML
    private TableView<Cedula> tableviewHistory;
    @FXML
    private Button getRefresh;
    @FXML
    private TextField searchbar;
    
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        income.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPersonalIncome())));
        communityTax.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCommTax())));
        addcommTax.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAddComTax())));
    
        userShowData();
    }
    
    //Left-Nav Controller for buttons
    @FXML
    private void dashboardClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/dashboard.fxml", "Dashboard");
    }

    @FXML
    private void barangayInfoClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayInformation/barangayInformation.fxml", "Barangay Information");
    }

    @FXML
    private void barangayOfficialClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/barangayOfficial/barangayOfficial.fxml", "Barangay Official");
    }

    @FXML
    private void residentRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/residentRecord/residentRecord.fxml", "Resident Record");
    }

    @FXML
    private void businessRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/businessRecord/businessRecord.fxml", "Business Record");
    }

    @FXML
    private void householdRecordClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/householdRecord/householdRecord.fxml", "Household Record");
    }

    @FXML
    private void requestedDocsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/requestedDocuments/requestedDocuments.fxml", "Requested Documents");
    }

    @FXML
    private void treasuryClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/treasury/treasury.fxml", "Treasury");
    }

    @FXML
    private void reportsClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/reports/reports.fxml", "Reports");
    }

    @FXML
    private void logOutClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/LogIn/LogIn.fxml", "Log In");       
    }
    @FXML
    private void handleGetAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CedulaForm.fxml"));
            Parent root = loader.load();

            CedulaForm cedulaFormController = loader.getController();
            cedulaFormController.setTreasuryController(this);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
           
    }
    
    public void updateTableView() {
        userShowData(); // Refresh the table view data
    }

    public ObservableList<Cedula> getCedulaData() {
        return cedulaData;
    }

    public void setCedulaData(ObservableList<Cedula> cedulaData) {
        this.cedulaData = cedulaData;
    }

    public TableView<Cedula> getTableviewHistory() {
        return tableviewHistory;
    }

    public void setTableviewHistory(TableView<Cedula> tableviewHistory) {
        this.tableviewHistory = tableviewHistory;
    }

    public TextField getSearchbar() {
        return searchbar;
    }

    public void setSearchbar(TextField searchbar) {
        this.searchbar = searchbar;
    }
    
    public ObservableList<Cedula> cedulaData;

    public void userShowData() {

        cedulaData = cedulaInfoList();

        full_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        residensy_status.setCellValueFactory(new PropertyValueFactory<>("residencyStatus"));
        income.setCellValueFactory(new PropertyValueFactory<>("formattedPersonalIncome"));
        date_issued.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));
        purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
//        purpose.setCellValueFactory(new PropertyValueFactory<>("purok"));
        communityTax.setCellValueFactory(new PropertyValueFactory<>("formatnum"));
        addcommTax.setCellValueFactory(new PropertyValueFactory<>("formatnum2"));
        total.setCellValueFactory(new PropertyValueFactory<>("formatnum3"));

        // Set the sorted data to the table view
        tableviewHistory.setItems(cedulaData);

            searchbar.textProperty().addListener((Observable, oldValue, newValue) -> {
                searchTable(newValue);
           });
    }
    
    private void searchTable(String text) {

                FilteredList<Cedula> filteredList = new FilteredList<>(
                        cedulaData, Cedula
                        -> Cedula.getlName().toUpperCase().startsWith(text.toUpperCase())
                );
                
                tableviewHistory.setItems(filteredList);
    } 
    
    public static Connection connectDB() {

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/bbis", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ObservableList<Cedula> cedulaInfoList() {

        String baseQuery = "SELECT * FROM cedula ORDER by date_issued ASC";

        ObservableList listData = FXCollections.observableArrayList();

        String selectData = baseQuery;

        try {

            if ( connectDB()== null) {
                return listData; // Return an empty list
            }
            PreparedStatement prepare = connectDB().prepareStatement(selectData);
            ResultSet result = prepare.executeQuery();

            Cedula cedulaInfo;

            while (result.next()) {

                cedulaInfo = new Cedula( result.getString("first_name"), result.getString("middle_name"),
                        result.getString("last_name"),  result.getString("gender"), result.getInt("age"),   
                        result.getInt("income"), result.getInt("barangay_Id"),result.getString("tin_no"),
                        result.getString("address"), result.getString("residency_status"), result.getDate("date_issued"),
                        result.getString("purpose"), result.getInt("community_tax"), result.getInt("addcomm_tax"), result.getInt("total"));
                
                listData.add(cedulaInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return listData;
    }
    
}


        

    
   

