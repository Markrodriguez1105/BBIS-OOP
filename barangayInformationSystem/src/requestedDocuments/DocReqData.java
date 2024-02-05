/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestedDocuments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * FXML Report class
 *
 * @author Hello Jovel
 */
class DocReqData {
    private final StringProperty docType;
    private final StringProperty name;
    private final StringProperty phoneNumber;
    private final StringProperty date;
    private final StringProperty reason;

    public DocReqData(String docType, String name, String phoneNumber, String date, String reason) {
        this.docType = new SimpleStringProperty(docType);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.date = new SimpleStringProperty(date);
        this.reason = new SimpleStringProperty(reason);
    }

    public String getDocType() {
        return docType.get();
    }

    public StringProperty DocTypeProperty() {
        return docType;
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }
}