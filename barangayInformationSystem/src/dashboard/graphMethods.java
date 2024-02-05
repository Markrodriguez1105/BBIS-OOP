/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Hello Mark
 */
public class graphMethods {

    //For BarGraph
    public HashMap<String, XYChart.Series<String, Number>> barGraphGenerator(ResultSet result) throws SQLException {
        HashMap<String, XYChart.Series<String, Number>> category = new HashMap<>();
        XYChart.Series<String, Number> gender;
        while (result.next()) {
            if (category.containsKey(result.getString(1))) {
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2) + "-" + result.getInt(3), result.getInt(3)));
            }
            else {
                gender = new XYChart.Series<>();
                gender.setName(result.getString(1));
                category.put(result.getString(1), gender);
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2) + "-" + result.getInt(3), result.getInt(3)));
            }
        }
        return category;
    }

    //For PieGraph
    public ObservableList<PieChart.Data> pieGraphGenerator(ResultSet result) throws SQLException {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        while (result.next()) {
            data.add(new PieChart.Data(result.getString(1) + "-" + result.getString(2), result.getInt(2)));
        }
        return data;
    }

    public HashMap<String, XYChart.Series<String, Number>> genderBarGraphGenerator(ResultSet result) throws SQLException {
        HashMap<String, XYChart.Series<String, Number>> category = new HashMap<>();
        HashMap<String, ArrayList<String>> label = new HashMap<>();
        ArrayList<String> list;
        XYChart.Series<String, Number> gender;
        while (result.next()) {
            if (category.containsKey(result.getString(1))) {
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2), result.getInt(3)));
                label.get(result.getString(1)).add(result.getString(2));
                for (int i = label.size() - 1; i >= 0; i--) {
                    String[] keys = label.keySet().stream().toArray(String[]::new);
                    String key = keys[i];
                    for (ArrayList<String> a : label.values()) {
                        for (String b : a) {
                            if (!label.get(key).contains(b)) {
                                XYChart.Series<String, Number> select1 = category.get(key);
                                select1.getData().add(new XYChart.Data<>(b, 0));
                            }
                        }
                    }
                }
            }
            else {
                gender = new XYChart.Series<>();
                list = new ArrayList<>();
                gender.setName(result.getString(1));
                list.add(result.getString(2));
                category.put(result.getString(1), gender);
                label.put(result.getString(1), list);
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2), result.getInt(3)));
            }
        }
        return category;
    }
    
    //For Line graph
    public HashMap<String, XYChart.Series<String, Number>> lineGraphGenerator(ResultSet result) throws SQLException {
        HashMap<String, XYChart.Series<String, Number>> category = new HashMap<>();
        XYChart.Series<String, Number> gender;
        while (result.next()) {
            if (category.containsKey(result.getString(1))) {
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2), result.getInt(3)));
            }
            else {
                gender = new XYChart.Series<>();
                gender.setName(result.getString(1));
                category.put(result.getString(1), gender);
                XYChart.Series<String, Number> select = category.get(result.getString(1));
                select.getData().add(new XYChart.Data<>(result.getString(2), result.getInt(3)));
            }
        }
        return category;
    }

}
