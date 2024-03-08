/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * FXML Report class
 *
 * @author Hello Jovel
 */
public class ReportsData {

    private final StringProperty reportId;
    private final SimpleStringProperty number;
    private final StringProperty reportType;
    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty lastName;
    private final StringProperty suffix;
    private final StringProperty contactNumber;
    private final StringProperty email;
    private final StringProperty date;
    private final StringProperty reason;
    private final StringProperty status;
    private final StringProperty recordStatus;

    // Constructor with all properties
    public ReportsData(String reportId, String reportType, String firstName, String middleName, String lastName, String suffix, String contactNumber, String email, String date, String reason, String status, String recordStatus) {
        this.reportId = new SimpleStringProperty(reportId);
        this.number = new SimpleStringProperty("");
        this.reportType = new SimpleStringProperty(reportType);
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
        this.suffix = new SimpleStringProperty(suffix);
        this.contactNumber = new SimpleStringProperty(contactNumber);
        this.email = new SimpleStringProperty(email);
        this.date = new SimpleStringProperty(date);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
        this.recordStatus = new SimpleStringProperty(recordStatus);
    }

    // Getter methods
    public String getReportId() {
        return reportId.get();
    }

    public String getNumber() {
        return number.get();
    }

    public String getReportType() {
        return reportType.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getSuffix() {
        return suffix.get();
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getReason() {
        return reason.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getRecordStatus() {
        return recordStatus.get();
    }

    // Property methods
    public StringProperty reportIdProperty() {
        return reportId;
    }

    public void setNumber(String value) {
        number.set(value);
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public StringProperty reportTypeProperty() {
        return reportType;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty suffixProperty() {
        return suffix;
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public ObjectProperty<String> nameProperty() {
        String fullName = String.join(" ", firstName.get(), middleName.get(), lastName.get(), suffix.get()).trim();
        return new SimpleObjectProperty<>(fullName);
    }

    public StringProperty recordStatusProperty() {
        return recordStatus;
    }
}
