/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import assets.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.main;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class PendingCasesViewController implements Initializable {
    
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
    private BarChart<String, Number> reportCasesGraph;
    @FXML
    private BarChart<String, Number> statusCasesGraph;
    @FXML
    private LineChart<String, Number> perYear;
    @FXML
    private ComboBox<String> filterYear;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Dashboard summary
        population.setText(DashboardController.populationCount);
        household.setText(DashboardController.householdCount);
        businesses.setText(DashboardController.businessesCount);
        pendingCases.setText(DashboardController.pendingCasesCount);
        voters.setText(DashboardController.votersCount);

        //Initialized Combobox
        filterYear.getItems().add("Yearly Reports");
        filterYear.getItems().add("Reported Cases");
        filterYear.setValue(filterYear.getItems().getFirst());

        //Show grpah
        showCasesGraph();
        showYearlyGraph();
        
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
    
    @FXML
    private void filterYear(ActionEvent event) {
        showYearlyGraph();
    }
    
    void showCasesGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();
            reportCasesGraph.getData().clear();
            statusCasesGraph.getData().clear();
            
            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery("""
                                                                                                   SELECT "Reported Cases" AS label, `report_type`, COUNT(*)
                                                                                                   FROM `report`
                                                                                                   GROUP BY `report_type`
                                                                                                   ORDER BY `report_type`;""")).values()) {
                reportCasesGraph.getData().add(bar);
            }
            
            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery("""
                                                                                                   SELECT "Reported Cases" AS label, `status`, COUNT(*)
                                                                                                   FROM `report`
                                                                                                   GROUP BY `status`;""")).values()) {
                statusCasesGraph.getData().add(bar);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    void showYearlyGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();
            perYear.getData().clear();
            
            switch (filterYear.getValue()) {
                case "Reported Cases":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                   SELECT `report_type`, YEAR(`date_recorded`), COUNT(*)
                                                                                                   FROM `report`
                                                                                                   GROUP BY 2, 1
                                                                                                   ORDER BY 2;""")).values()) {
                        perYear.getData().add(line);
                    }
                    break;
                case "Yearly Reports":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                   SELECT 'Yearly Reports' AS label, YEAR(`date_recorded`), COUNT(*)
                                                                                                   FROM `report`
                                                                                                   GROUP BY 2
                                                                                                   ORDER BY 2;""")).values()) {
                        perYear.getData().add(line);
                    }
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
