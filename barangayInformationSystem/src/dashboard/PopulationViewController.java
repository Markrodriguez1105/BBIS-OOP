/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.main;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class PopulationViewController implements Initializable {

    @FXML
    private Label population;
    @FXML
    private Label household;
    @FXML
    private Label businesses;
    @FXML
    private Label pendingCases;
    @FXML
    private Label voters;
    @FXML
    private PieChart categoryPopulationGraph;
    @FXML
    private BarChart<String, Number> genderPopulationGraph;

    Database database = new Database();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Dashboard summary
        population.setText("2,719");
        household.setText("794");
        businesses.setText("125");
        pendingCases.setText("14");
        voters.setText("1,246");

        genderPopulationGraph.setBarGap(0);

        //Population Graph
        ResultSet barResult = database.executeQuery("SELECT `gender`, COUNT(`gender`) FROM `resident` GROUP BY `gender`;");
        try {
            genderPopulationGraph.getData().add(insertBarGraphDate(barResult));
        } catch (SQLException ex) {
            Logger.getLogger(PopulationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Piechart
        ResultSet pieResult = database.executeQuery("SELECT CASE WHEN `age`<= 13 THEN 'Child' WHEN `age`> 13 AND `age` <= 18 THEN 'Teen' WHEN `age`> 18 AND `age` <= 60 THEN 'Adult' ELSE 'Senior' END AS `category`, COUNT(*) AS `count` FROM `resident` GROUP BY `category`;");
        try {
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            while (pieResult.next()) {
                data.add(new PieChart.Data(pieResult.getString(1), pieResult.getInt(2)));
            }
            categoryPopulationGraph.setData(data);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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

    //Graph start here
    @FXML
    private void populationClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/populationView.fxml", "Population View");
    }

    @FXML
    private void householdClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/householdView.fxml", "Household View");
    }

    @FXML
    private void businessClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/businessView.fxml", "Businesses View");
    }

    @FXML
    private void pendingCasesClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/pendingCasesView.fxml", "Pending Cases View");
    }

    @FXML
    private void votersClick(ActionEvent event) throws IOException {
        main main = new main();
        main.changeScene("/dashboard/voterView.fxml", "Voters View");
    }

    public XYChart.Series<String, Number> insertBarGraphDate(ResultSet result) throws SQLException {
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        while (result.next()) {
            data.setName(result.getString(1));
            data.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
        }
        return data;
    }
}
