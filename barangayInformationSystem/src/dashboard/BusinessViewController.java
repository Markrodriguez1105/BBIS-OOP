/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import main.main;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Hello Mark
 */
public class BusinessViewController implements Initializable {

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
    private ComboBox<String> filterYear;
    @FXML
    private ComboBox<String> perYear;
    @FXML
    private BarChart<String, Number> businessesGraph;
    @FXML
    private LineChart<String, Number> businessesTally;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database database = new Database();
        // TODO        //Dashboard summary
        population.setText(DashboardController.populationCount);
        household.setText(DashboardController.householdCount);
        businesses.setText(DashboardController.businessesCount);
        pendingCases.setText(DashboardController.pendingCasesCount);
        voters.setText(DashboardController.votersCount);

        //Initialization combobox
        filterYear.setValue(setComboBox(database.executeQuery("""
                                                          SELECT MAX(YEAR(`date_registered`))
                                                          FROM `resident`;""")).get(0));
        filterYear.getItems().addAll(setComboBox(database.executeQuery("""
                                                                   SELECT YEAR(`date_registered`) AS `year`
                                                                   FROM `resident`
                                                                   GROUP BY 1
                                                                   ORDER BY 1 DESC;""")));

        perYear.setValue("Business");
        perYear.getItems().add("Business");
        perYear.getItems().add("Zone Business");

        //Graph Call
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

    public ObservableList<String> setComboBox(ResultSet result) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @FXML
    private void filterYear(ActionEvent event) {
    }

    @FXML
    private void perYear(ActionEvent event) {
    }

    void showBusinessesGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();

            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                                                   SELECT
                                                                                                   CASE
                                                                                                   WHEN `status` = 0 THEN 'Inactive'
                                                                                                   ELSE 'Active' END AS status, `zone`, COUNT(*)
                                                                                                   FROM `business`
                                                                                                   WHERE YEAR(`date_registered`) <= %s
                                                                                                   GROUP BY 2, 1
                                                                                                   ORDER BY 2;""", filterYear.getValue()))).values()) {
                businessesGraph.getData().add(bar);
            }
            
            for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery(String.format("""
                                                                                                   SELECT
                                                                                                   CASE
                                                                                                   WHEN `status` = 0 THEN 'Inactive'
                                                                                                   ELSE 'Active' END AS status, `zone`, COUNT(*)
                                                                                                   FROM `business`
                                                                                                   WHERE YEAR(`date_registered`) <= %s
                                                                                                   GROUP BY 2, 1
                                                                                                   ORDER BY 2;""", filterYear.getValue()))).values()) {
                businessesGraph.getData().add(line);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
