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
import main.main;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class HouseholdViewController implements Initializable {

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
    private BarChart<String, Number> zoneHouseholdGraph;
    @FXML
    private BarChart<String, Number> incomeHouseholdGraph;


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
        
        //Household Graph
        XYChart.Series<String, Number> householdGraph1 = new XYChart.Series<>();
        householdGraph1.getData().add(new XYChart.Data<>("Zone 1", 45));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 2", 40));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 3", 35));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 4", 75));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 5", 15));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 6", 75));
        householdGraph1.getData().add(new XYChart.Data<>("Zone 7", 36));
        zoneHouseholdGraph.getData().addAll(householdGraph1);
        
        XYChart.Series<String, Number> householdGraph2 = new XYChart.Series<>();
        householdGraph2.getData().add(new XYChart.Data<>("0-₱10,000", 32));
        householdGraph2.getData().add(new XYChart.Data<>("₱10,001-₱30,000", 40));
        householdGraph2.getData().add(new XYChart.Data<>("₱30,001-₱50,000", 35));
        householdGraph2.getData().add(new XYChart.Data<>("₱50,001-more", 75));
        incomeHouseholdGraph.getData().addAll(householdGraph2);
        
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
    
}
