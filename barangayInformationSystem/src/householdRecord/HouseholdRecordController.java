package householdRecord;

import householdRecord.HouseholdRecords;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.main;

public class HouseholdRecordController implements Initializable {

    @FXML
    private TextField tfHouseholdNumber;
    @FXML
    private TextField tfHeadoftheFamily;
    @FXML
    private TextField tfPosition;
    @FXML
    private TextField tfHouseholdSize;
    @FXML
    private TextField tfOccupation;
    @FXML
    private TextField tfPurok;
    @FXML
    private TextField tfMonthlyIncome;
    @FXML
    private Button btnInsert;
    @FXML
    private TableView<HouseholdRecords> tvHouseholdRecords;
    @FXML
    private TableColumn<HouseholdRecords, Integer> colHouseholdNumber;
    @FXML
    private TableColumn<HouseholdRecords, String> colHeadoftheFamily;
    @FXML
    private TableColumn<HouseholdRecords, String> colPosition;
    @FXML
    private TableColumn<HouseholdRecords, Integer> colHHSize;
    @FXML
    private TableColumn<HouseholdRecords, String> colOccupation;
    @FXML
    private TableColumn<HouseholdRecords, Integer> colPurok;
    @FXML
    private TableColumn<HouseholdRecords, Double> colEMI;
    private TextField tfSearch;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField tfSearchField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showHouseholdRecords();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/householdrecords", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
            return null;
        }
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

    public ObservableList<HouseholdRecords> getHouseholdRecordsList() {
        ObservableList<HouseholdRecords> recordsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM household_records";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                HouseholdRecords record = new HouseholdRecords(
                        rs.getInt("householdNumber"),
                        rs.getString("headOfTheFamily"),
                        rs.getString("position"),
                        rs.getInt("householdSize"),
                        rs.getString("occupation"),
                        rs.getInt("purok"),
                        rs.getDouble("monthlyIncome")
                );
                recordsList.add(record);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return recordsList;
    }

    @FXML
    public void showHouseholdRecords() {
        ObservableList<HouseholdRecords> list = getHouseholdRecordsList();

        colHouseholdNumber.setCellValueFactory(new PropertyValueFactory<>("householdNumber"));
        colHeadoftheFamily.setCellValueFactory(new PropertyValueFactory<>("headOfTheFamily"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colHHSize.setCellValueFactory(new PropertyValueFactory<>("householdSize"));
        colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        colPurok.setCellValueFactory(new PropertyValueFactory<>("purok"));
        colEMI.setCellValueFactory(new PropertyValueFactory<>("monthlyIncome"));

        tvHouseholdRecords.setItems(list);

    }

    @FXML
    private void handleInsertButton(ActionEvent event) {
        String query = "INSERT INTO household_records VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(tfHouseholdNumber.getText()));
            preparedStatement.setString(2, tfHeadoftheFamily.getText());
            preparedStatement.setString(3, tfPosition.getText());
            preparedStatement.setInt(4, Integer.parseInt(tfHouseholdSize.getText()));
            preparedStatement.setString(5, tfOccupation.getText());
            preparedStatement.setInt(6, Integer.parseInt(tfPurok.getText()));
            preparedStatement.setDouble(7, Double.parseDouble(tfMonthlyIncome.getText()));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        showHouseholdRecords();
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) {
        String query = "UPDATE household_records SET headOfTheFamily = ?, position = ?, householdSize = ?, "
                + "occupation = ?, purok = ?, monthlyIncome = ? WHERE householdNumber = ?";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, tfHeadoftheFamily.getText());
            preparedStatement.setString(2, tfPosition.getText());
            preparedStatement.setInt(3, Integer.parseInt(tfHouseholdSize.getText()));
            preparedStatement.setString(4, tfOccupation.getText());
            preparedStatement.setInt(5, Integer.parseInt(tfPurok.getText()));
            preparedStatement.setDouble(6, Double.parseDouble(tfMonthlyIncome.getText()));
            preparedStatement.setInt(7, Integer.parseInt(tfHouseholdNumber.getText()));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        showHouseholdRecords();
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        String query = "DELETE FROM household_records WHERE householdNumber = ?";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(tfHouseholdNumber.getText()));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        showHouseholdRecords();
    }

    private void handleMouseAction(MouseEvent event) {
        HouseholdRecords record = tvHouseholdRecords.getSelectionModel().getSelectedItem();

        tfHouseholdNumber.setText(String.valueOf(record.getHouseholdNumber()));
        tfHeadoftheFamily.setText(record.getHeadOfthefamily());
        tfPosition.setText(record.getPosition());
        tfHouseholdSize.setText(String.valueOf(record.getHouseholdsize()));
        tfOccupation.setText(record.getOccupation());
        tfPurok.setText(String.valueOf(record.getPurok()));
        tfMonthlyIncome.setText(String.valueOf(record.getMonthlyIncome()));
    }

    @FXML
    private void textFieldShow(MouseEvent event) {

    }

    @FXML
    private void handleClearButton(ActionEvent event) {
        tfHouseholdNumber.clear();
        tfHeadoftheFamily.clear();
        tfPosition.clear();
        tfHouseholdSize.clear();
        tfOccupation.clear();
        tfPurok.clear();
        tfMonthlyIncome.clear();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = tfSearch.getText();

        if (!searchTerm.isEmpty()) {
            // Perform database search
            try (Connection conn = getConnection()) {
                String query = "SELECT * FROM household_records WHERE headOfTheFamily LIKE ? OR position LIKE ? OR occupation LIKE ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchTerm + "%");
                preparedStatement.setString(2, "%" + searchTerm + "%");
                preparedStatement.setString(3, "%" + searchTerm + "%");
                ResultSet rs = preparedStatement.executeQuery();

                ObservableList<HouseholdRecords> filteredRecords = FXCollections.observableArrayList();
                while (rs.next()) {
                    HouseholdRecords record = new HouseholdRecords(
                            rs.getInt("householdNumber"),
                            rs.getString("headOfTheFamily"),
                            rs.getString("position"),
                            rs.getInt("householdSize"),
                            rs.getString("occupation"),
                            rs.getInt("purok"),
                            rs.getDouble("monthlyIncome")
                    );
                    filteredRecords.add(record);
                }

                tvHouseholdRecords.setItems(filteredRecords);
            } catch (SQLException ex) {
                displayErrorMessage("An error occurred during the search: " + ex.getMessage());
            }
        }
    }

    private void displayErrorMessage(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void handleSearch(MouseEvent event) {
    }
}
