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
import javafx.scene.control.ComboBox;
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
    @FXML
    private LineChart<String, Number> perYear;
    @FXML
    private ComboBox<String> perYearFilter;
    @FXML
    private ComboBox<String> filterYear;
    @FXML
    private ComboBox<String> filterZone;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphMethods graph = new graphMethods();
        Database database = new Database();
        // TODO
        //Dashboard summary
        population.setText(DashboardController.populationCount);
        household.setText(DashboardController.householdCount);
        businesses.setText(DashboardController.businessesCount);
        pendingCases.setText(DashboardController.pendingCasesCount);
        voters.setText(DashboardController.votersCount);

        //Combobox Initialization
        //Filter Year
        filterYear.getItems().addAll(setComboBox(database.executeQuery("""
                                                                        SELECT YEAR(`date_registered`) AS `year`
                                                                        FROM `resident`
                                                                        GROUP BY 1
                                                                        ORDER BY 1 DESC;""")));
        filterYear.setValue(filterYear.getItems().getFirst());

        //Filter Zone
        filterZone.getItems().add("All Zone");
        filterZone.getItems().addAll(setComboBox(database.executeQuery("""
                                                                       SELECT CONCAT('Zone ', `zone`)
                                                                       FROM `resident`
                                                                       GROUP BY 1
                                                                       ORDER BY 1;""")));
        filterZone.setValue(filterZone.getItems().getFirst());

        //Yearly Changes
        perYearFilter.getItems().add("Household");
        perYearFilter.getItems().add("Zone Household");
        perYearFilter.getItems().add("Income Rate");
        perYearFilter.setValue(perYearFilter.getItems().getFirst());

        //call graph
        showFilter();
        showYearlyChanges();
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
        showFilter();
    }

    @FXML
    private void filterZone(ActionEvent event) {
        showFilter();
    }

    @FXML
    private void perYearFilter(ActionEvent event) {
        showYearlyChanges();
    }

    void showFilter() {
        try {
            graphMethods graph = new graphMethods();
            Database database = new Database();

            zoneHouseholdGraph.getData().clear();
            incomeHouseholdGraph.getData().clear();

            //Household Graph
            for (XYChart.Series<String, Number> bar
                    : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                    SELECT %s AS `label`,
                                                                    CONCAT('Zone ', `zone`), COUNT(`zone`)
                                                                    FROM `household`
                                                                    WHERE YEAR(`date_registered`) <= %s
                                                                    GROUP BY `zone`
                                                                    ORDER BY `zone`;""", filterYear.getValue(), filterYear.getValue()))).values()) {
                zoneHouseholdGraph.getData().add(bar);
            }

            if (filterZone.getValue().equals("All Zone")) {
                for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                                                        SELECT 'All Zone' AS `label`, CASE
                                                                                                        WHEN `monthly_income` <= 10000 THEN '0-\u20b110,000'
                                                                                                        WHEN `monthly_income` > 10000 AND `monthly_income` <= 30000 THEN '\u20b110,000-\u20b130,000'
                                                                                                        WHEN `monthly_income` > 30000 AND `monthly_income` <= 50000 THEN '\u20b130,000-\u20b150,000'
                                                                                                        ELSE '\u20b150,000-more' END AS `cat`, COUNT(*) AS `count`
                                                                                                        FROM `household`
                                                                                                        WHERE YEAR(`date_registered`) <= %s
                                                                                                        GROUP BY `cat`
                                                                                                        ORDER BY `monthly_income`;""", filterYear.getValue()))).values()) {
                    incomeHouseholdGraph.getData().add(bar);
                }
            } else {
                for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(database.executeQuery(String.format("""
                                                                                                        SELECT %s AS `label`, CASE
                                                                                                        WHEN `monthly_income` <= 10000 THEN '0-\u20b110,000'
                                                                                                        WHEN `monthly_income` > 10000 AND `monthly_income` <= 30000 THEN '\u20b110,000-\u20b130,000'
                                                                                                        WHEN `monthly_income` > 30000 AND `monthly_income` <= 50000 THEN '\u20b130,000-\u20b150,000'
                                                                                                        ELSE '\u20b150,000-more' END AS `cat`, COUNT(*) AS `count`
                                                                                                        FROM `household`
                                                                                                        WHERE YEAR(`date_registered`) <= %s AND CONCAT('Zone ',`zone`) = '%s'
                                                                                                        GROUP BY `cat`
                                                                                                        ORDER BY `monthly_income`;""", filterYear.getValue(), filterYear.getValue(), filterZone.getValue()))).values()) {
                    incomeHouseholdGraph.getData().add(bar);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void showYearlyChanges() {
        perYear.getData().clear();
        try {
            graphMethods graph = new graphMethods();
            Database database = new Database();

            switch (perYearFilter.getValue()) {
                case "Household":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                            SELECT 'Household' AS label, YEAR(`date_registered`) AS year,
                                                                                                            SUM(COUNT(*)) OVER (ORDER BY YEAR(`date_registered`)) AS count
                                                                                                            FROM `household`
                                                                                                            GROUP BY YEAR(`date_registered`);""")).values()) {
                        perYear.getData().add(line);
                    }
                    break;
                case "Zone Household":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                            SELECT CONCAT('Zone ',`zone`) AS zone, YEAR(`date_registered`) AS year,
                                                                                                            SUM(COUNT(*)) OVER (PARTITION BY `zone` ORDER BY YEAR(`date_registered`)) AS count
                                                                                                            FROM `household`
                                                                                                            GROUP BY `zone`, YEAR(`date_registered`);""")).values()) {
                        perYear.getData().add(line);
                    }
                    break;
                case "Income Rate":
                    for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(database.executeQuery("""
                                                                                                              SELECT CASE 
                                                                                                              WHEN `monthly_income` <= 10000 THEN '0-₱10,000'
                                                                                                              WHEN `monthly_income` > 10000 AND `monthly_income` <= 30000 THEN '₱10,000-₱30,000'
                                                                                                              WHEN `monthly_income` > 30000 AND `monthly_income` <= 50000 THEN '₱30,000-₱50,000'
                                                                                                              ELSE '₱50,000-more' END AS cat,
                                                                                                              YEAR(`date_registered`) AS YEAR,
                                                                                                              SUM(COUNT(*)) OVER( PARTITION BY(
                                                                                                              CASE
                                                                                                              WHEN `monthly_income` <= 10000 THEN '0-₱10,000'
                                                                                                              WHEN `monthly_income` > 10000 AND `monthly_income` <= 30000 THEN '₱10,000-₱30,000'
                                                                                                              WHEN `monthly_income` > 30000 AND `monthly_income` <= 50000 THEN '₱30,000-₱50,000'
                                                                                                              ELSE '₱50,000-more' END) ORDER BY YEAR(`date_registered`)) AS count
                                                                                                              FROM `household`
                                                                                                              GROUP BY cat, YEAR(`date_registered`);""")).values()) {
                        perYear.getData().add(line);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
