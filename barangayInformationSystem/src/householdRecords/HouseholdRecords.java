/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package householdRecords;

import javafx.beans.property.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;

public class HouseholdRecords {
    private final IntegerProperty householdNumber = new SimpleIntegerProperty();
    private final StringProperty headOfTheFamily = new SimpleStringProperty();
    private final StringProperty position = new SimpleStringProperty();
    private final IntegerProperty householdSize = new SimpleIntegerProperty();
    private final StringProperty occupation = new SimpleStringProperty();
    private final IntegerProperty purok = new SimpleIntegerProperty();
    private final DoubleProperty monthlyIncome = new SimpleDoubleProperty();

    public HouseholdRecords() {
        // Default constructor
    }

    public HouseholdRecords(int householdNumber, String headOfTheFamily, String position,
                            int householdSize, String occupation, int purok, double monthlyIncome) {
        setHouseholdNumber(householdNumber);
        setHeadOfTheFamily(headOfTheFamily);
        setPosition(position);
        setHouseholdSize(householdSize);
        setOccupation(occupation);
        setPurok(purok);
        setMonthlyIncome(monthlyIncome);
    }

    public IntegerProperty householdNumberProperty() {
        return householdNumber;
    }

    public int getHouseholdNumber() {
        return householdNumber.get();
    }

    public void setHouseholdNumber(int householdNumber) {
        this.householdNumber.set(householdNumber);
    }

    public StringProperty headOfTheFamilyProperty() {
        return headOfTheFamily;
    }

    public String getHeadOfTheFamily() {
        return headOfTheFamily.get();
    }

    public void setHeadOfTheFamily(String headOfTheFamily) {
        this.headOfTheFamily.set(headOfTheFamily);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public IntegerProperty householdSizeProperty() {
        return householdSize;
    }

    public int getHouseholdSize() {
        return householdSize.get();
    }

    public void setHouseholdSize(int householdSize) {
        this.householdSize.set(householdSize);
    }

    public StringProperty occupationProperty() {
        return occupation;
    }

    public String getOccupation() {
        return occupation.get();
    }

    public void setOccupation(String occupation) {
        this.occupation.set(occupation);
    }

    public IntegerProperty purokProperty() {
        return purok;
    }

    public int getPurok() {
        return purok.get();
    }

    public void setPurok(int purok) {
        this.purok.set(purok);
    }

    public DoubleProperty monthlyIncomeProperty() {
        return monthlyIncome;
    }

    public double getMonthlyIncome() {
        return monthlyIncome.get();
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome.set(monthlyIncome);
    }

    Object getHouseholdsize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getHeadOfthefamily() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
