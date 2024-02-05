/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reports;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportDetailsController {

    @FXML
    private Label reportTypeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label contactNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label reasonLabel;

    public void setReportDetails(ReportsData report) {
        reportTypeLabel.setText("Report Type: " + report.getReportType());
        nameLabel.setText("Name: " + report.getName());
        contactNumberLabel.setText("Contact Number: " + report.getContactNumber());
        emailLabel.setText("Email: " + report.getEmail());
        reasonLabel.setText("Reason: " + report.getReason());
    }
}