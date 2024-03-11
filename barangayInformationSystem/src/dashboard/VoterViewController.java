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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.main;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class VoterViewController implements Initializable {

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
    private BarChart<String, Number> zoneVoterGraph;
    @FXML
    private BarChart<String, Number> statusVoterGraph;
    @FXML
    private LineChart<String, Number> yearlyChanges;

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

        //ShowGraph
        showVoterGraph();
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
        main.changeScene("/existresidentRecord/existresidentRecord.fxml", "existresident Record");
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

    void showVoterGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();

            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery("""
                                                                                                    SELECT 'Zone Voters' AS label, CONCAT('Zone ',`zone`) AS zone, COUNT(*)
                                                                                                    FROM `existresident`
                                                                                                    WHERE `voter_status` = 'Registered'
                                                                                                    GROUP BY `zone`
                                                                                                    ORDER BY `zone`;""")).values()) {
                zoneVoterGraph.getData().add(bar);
            }

            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery("""
                                                                                                    SELECT 'Status' AS label,
                                                                                                    `voter_status`, COUNT(*)
                                                                                                    FROM `existresident`
                                                                                                    GROUP BY `voter_status`
                                                                                                    ORDER BY `voter_status` DESC;""")).values()) {
                statusVoterGraph.getData().add(bar);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void showYearlyGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();

            for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                   SELECT "Voters" AS label, YEAR(`date_registered`),
                                                                                                   SUM(COUNT(*)) OVER (ORDER BY YEAR(`date_registered`)) AS count
                                                                                                   FROM `existresident`
                                                                                                   WHERE `voter_status` = 'Registered'
                                                                                                   GROUP BY 2
                                                                                                   ORDER BY 2;""")).values()) {
                yearlyChanges.getData().add(line);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
