/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * FXML Report class
 *
 * @author Hello Jovel
 */

public class ReportsData {

    private final StringProperty reportType;
    private final StringProperty name;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final StringProperty reason;

    public ReportsData(String reportType, String name, String phoneNumber, String email, String reason) {
        this.reportType = new SimpleStringProperty(reportType);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.reason = new SimpleStringProperty(reason);
    }

    public String getReportType() {
        return reportType.get();
    }

    public StringProperty reportTypeProperty() {
        return reportType;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getContactNumber() {
        return phoneNumber.get();
    }

    public StringProperty contactNumberProperty() {
        return phoneNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }
}