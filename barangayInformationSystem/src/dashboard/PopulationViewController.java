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
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
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

    @FXML
    private ComboBox<String> filter;
    @FXML
    private LineChart<String, Number> perYear;
    @FXML
    private ComboBox<String> perYearFilter;
    @FXML
    private BarChart<String, Number> perZone;
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
        Database database = new Database();
        // TODO
        //Dashboard summary
        population.setText(DashboardController.populationCount);
        household.setText(DashboardController.householdCount);
        businesses.setText(DashboardController.businessesCount);
        pendingCases.setText(DashboardController.pendingCasesCount);
        voters.setText(DashboardController.votersCount);

        //Filter Year
        filter.setValue(setComboBox(database.executeQuery("""
                                                          SELECT MAX(YEAR(`date_registered`))
                                                          FROM `resident`;""")).get(0));
        filter.getItems().addAll(setComboBox(database.executeQuery("""
                                                                   SELECT YEAR(`date_registered`) AS `year`
                                                                   FROM `resident`
                                                                   GROUP BY 1
                                                                   ORDER BY 1 DESC;""")));

        //Combo box per year
        perYearFilter.setValue("Population");
        perYearFilter.getItems().add("Population");
        perYearFilter.getItems().add("Gender");
        perYearFilter.getItems().add("Category");
        perYearFilter.getItems().add("Zone Population");

        //Filter zone
        filterZone.setValue("All Zone");
        filterZone.getItems().add("All Zone");
        filterZone.getItems().addAll(setComboBox(database.executeQuery("""
                                                                       SELECT CONCAT('Zone ', `zone`)
                                                                       FROM `resident`
                                                                       GROUP BY 1
                                                                       ORDER BY 1;""")));

        //Population Graph
        genderPopulationGraph.setCategoryGap(5);

        //Gender
        showFilter();

        //Line graph
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
    private void filterSelect(ActionEvent event) {
        showFilter();
    }

    @FXML
    private void perYearClick(ActionEvent event) {
        showYearlyChanges();
    }

    @FXML
    private void filterZoneSelect(ActionEvent event) {
        showFilter();
    }

    void showFilter() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();

            genderPopulationGraph.getData().clear();
            perZone.getData().clear();
            //perZone
            ResultSet perZoneResult = database.executeQuery("SELECT '" + filter.getValue() + "' AS `label`, CONCAT('Zone ',`zone`), COUNT(*) AS `count`\n"
                    + "FROM `resident`\n"
                    + "WHERE YEAR(`date_registered`) <= " + filter.getValue() + "\n"
                    + "GROUP BY 2 \n"
                    + "ORDER BY 2;");
            for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(perZoneResult).values()) {
                perZone.getData().add(bar);
            }

            if (filterZone.getValue().equalsIgnoreCase("All Zone")) {
                //Gender
                ResultSet gender = database.executeQuery("SELECT '" + filter.getValue() + "' AS `label` , `gender`, COUNT(`gender`),\n"
                        + "CASE\n"
                        + "WHEN `gender` = 'Male' THEN 1\n"
                        + "WHEN `gender` = 'Female' THEN 2\n"
                        + "ELSE 3 END AS `sort`\n"
                        + "FROM `resident`\n"
                        + "WHERE YEAR(`date_registered`) <= " + filter.getValue() + "\n"
                        + "GROUP BY 2 \n"
                        + "ORDER BY `sort`;");
                for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(gender).values()) {
                    genderPopulationGraph.getData().add(bar);
                }

                //Piechart
                ResultSet catResult = database.executeQuery(String.format("""
                                                        SELECT
                                                        CASE
                                                        WHEN `age`<= 13 THEN 'Child'
                                                        WHEN `age`> 13 AND `age` <= 18 THEN 'Teen' 
                                                        WHEN `age`> 18 AND `age` <= 60 THEN 'Adult' 
                                                        ELSE 'Senior' END AS `category`, COUNT(*) AS `count`, 
                                                        CASE
                                                        WHEN `age`<= 13 THEN 1
                                                        WHEN `age`> 13 AND `age` <= 18 THEN 2 
                                                        WHEN `age`> 18 AND `age` <= 60 THEN 3
                                                        ELSE 4 END AS `sort`
                                                        FROM `resident`
                                                        WHERE YEAR(`date_registered`) <= %s
                                                        GROUP BY 1
                                                        ORDER BY 3;""", filter.getValue()));
                categoryPopulationGraph.setData(graph.pieGraphGenerator(catResult));
            }
            else {
                //Gender
                ResultSet gender = database.executeQuery("SELECT '" + filter.getValue() + "' AS `label` , `gender`, COUNT(`gender`)\n"
                        + "FROM `resident`\n"
                        + "WHERE YEAR(`date_registered`) <= " + filter.getValue() + " AND CONCAT('Zone ', `zone`) = '" + filterZone.getValue() + "'\n"
                        + "GROUP BY 2;");
                for (XYChart.Series<String, Number> bar : graph.barGraphGenerator(gender).values()) {
                    genderPopulationGraph.getData().add(bar);
                }

                //Piechart
                ResultSet catResult = database.executeQuery(String.format("""
                                                        SELECT
                                                        CASE
                                                        WHEN `age`<= 13 THEN 'Child'
                                                        WHEN `age`> 13 AND `age` <= 18 THEN 'Teen' 
                                                        WHEN `age`> 18 AND `age` <= 60 THEN 'Adult' 
                                                        ELSE 'Senior' END AS `category`, COUNT(*) AS `count`, 
                                                        CASE
                                                        WHEN `age`<= 13 THEN 1
                                                        WHEN `age`> 13 AND `age` <= 18 THEN 2 
                                                        WHEN `age`> 18 AND `age` <= 60 THEN 3
                                                        ELSE 4 END AS `sort`
                                                        FROM `resident`
                                                        WHERE YEAR(`date_registered`) <= %s AND CONCAT('Zone ', `zone`) = '%s'
                                                        GROUP BY 1
                                                        ORDER BY 3;""", filter.getValue(), filterZone.getValue()));
                categoryPopulationGraph.setData(graph.pieGraphGenerator(catResult));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void showYearlyChanges() {
        try {
            Database database = new Database();
            graphMethods graph = new graphMethods();

            perYear.getData().clear();

            if (perYearFilter.getValue().equalsIgnoreCase("Gender")) {
                ResultSet result = database.executeQuery("""
                                                           SELECT `gender`, YEAR(`date_registered`),
                                                           SUM(COUNT(*)) OVER (PARTITION BY `gender` ORDER BY YEAR(`date_registered`)) AS `count`
                                                           FROM `resident`
                                                           GROUP BY 1, 2
                                                           ORDER BY 2, 1;""");
                for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(result).values()) {
                    perYear.getData().add(line);
                }
            }
            else if (perYearFilter.getValue().equalsIgnoreCase("Population")) {
                ResultSet result = database.executeQuery("""
                                                           SELECT 'Population' AS `label`, YEAR(`date_registered`),
                                                           SUM(COUNT(*)) OVER (ORDER BY YEAR(`date_registered`)) AS `count`
                                                           FROM `resident`
                                                           GROUP BY 2
                                                           ORDER BY 2;""");
                for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(result).values()) {
                    perYear.getData().add(line);
                }
            }
            else if (perYearFilter.getValue().equalsIgnoreCase("Zone Population")) {
                ResultSet result = database.executeQuery("""
                                                           SELECT CONCAT('Zone ',`zone`), YEAR(`date_registered`),
                                                           SUM(COUNT(*)) OVER (PARTITION BY `zone` ORDER BY YEAR(`date_registered`)) AS `count`
                                                           FROM `resident`
                                                           GROUP BY 1 , 2
                                                           ORDER BY 1;""");
                for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(result).values()) {
                    perYear.getData().add(line);
                }
            }
            else {
                ResultSet result = database.executeQuery("""
                                                           SELECT CASE WHEN `age` <= 13 THEN 'Child' WHEN `age` > 13 AND `age` <= 18 THEN 'Teen' WHEN `age` > 18 AND `age` <= 60 THEN 'Adult' ELSE 'Senior' END AS `category`,
                                                           YEAR(`date_registered`),
                                                           SUM(COUNT(*)) OVER(PARTITION BY (CASE WHEN `age` <= 13 THEN 'Child' WHEN `age` > 13 AND `age` <= 18 THEN 'Teen' WHEN `age` > 18 AND `age` <= 60 THEN 'Adult' ELSE 'Senior'
                                                           END) ORDER BY YEAR(`date_registered`)) AS count,
                                                           CASE WHEN `age` <= 13 THEN 1 WHEN `age` > 13 AND `age` <= 18 THEN 2 WHEN `age` > 18 AND `age` <= 60 THEN 3 ELSE 4
                                                           END AS `sort`
                                                           FROM `resident`
                                                           GROUP BY 1, 2
                                                           ORDER BY `sort`;""");
                for (XYChart.Series<String, Number> line : graph.lineGraphGenerator(result).values()) {
                    perYear.getData().add(line);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
