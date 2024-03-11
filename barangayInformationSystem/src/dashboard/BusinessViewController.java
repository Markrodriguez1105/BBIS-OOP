/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import assets.Database;
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
    private BarChart<String, Number> businessesTally;
    @FXML
    private BarChart<String, Number> businessStatus;
    @FXML
    private LineChart<String, Number> businessesYearly;
    @FXML
    private ComboBox<String> perYear;

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
        filterYear.getItems().addAll(setComboBox(database.executeQuery("""
                                                                        SELECT YEAR(`date_registered`) AS `year`
                                                                        FROM `business`
                                                                        GROUP BY 1
                                                                        ORDER BY 1 DESC;""")));
        filterYear.setValue(filterYear.getItems().getFirst());

        perYear.getItems().add("Business");
//        perYear.getItems().add("Zone Business");
        perYear.setValue(perYear.getItems().getFirst());

        //Graph Call
        showBusinessesGraph();
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
        showBusinessesGraph();
    }

    @FXML
    private void perYear(ActionEvent event) {
        showYearlyGraph();
    }

    void showBusinessesGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();
            businessStatus.getData().clear();
            businessesTally.getData().clear();

            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                                                   SELECT %s AS label, CASE WHEN `active_status` = 'Active' THEN 'Active' ELSE 'Inactive'
                                                                                                   END AS status, COUNT(*)
                                                                                                   FROM `business`
                                                                                                   WHERE YEAR(`date_registered`) <= %s
                                                                                                   GROUP BY 2;""", filterYear.getValue(), filterYear.getValue()))).values()) {
                businessStatus.getData().add(bar);
            }

            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                                                   SELECT %s AS label, `business_type`, COUNT(*)
                                                                                                   FROM `business`
                                                                                                   WHERE YEAR(`date_registered`) <= %s
                                                                                                   GROUP BY `business_type`;""", filterYear.getValue(), filterYear.getValue()))).values()) {
                businessesTally.getData().add(bar);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void showYearlyGraph() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();
            businessesYearly.getData().clear();

            switch (perYear.getValue()) {
                case "Business":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                   SELECT 'Business' AS label, YEAR(`date_registered`), 
                                                                                                   SUM(COUNT(*)) OVER (ORDER BY `date_registered`)
                                                                                                   FROM `business`
                                                                                                   GROUP BY 2;""")).values()) {
                        businessesYearly.getData().add(line);
                    }
                    break;
                case "Zone Business":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                   SELECT CONCAT('Zone ', `zone`), YEAR(`date_registered`),
                                                                                                   SUM(COUNT(*)) OVER (PARTITION BY `zone` ORDER BY YEAR(`date_registered`)) AS `count`
                                                                                                   FROM `business`
                                                                                                   GROUP BY 1, 2
                                                                                                   ORDER BY 1;""")).values()) {
                        businessesYearly.getData().add(line);
                    }
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
