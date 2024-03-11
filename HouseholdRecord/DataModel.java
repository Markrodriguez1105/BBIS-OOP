package HouseholdRecord;

import java.time.LocalDate;
import java.time.Period;

public class DataModel {

    private int household_id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String suffix;
    private String civil_status;
    private String pos_in_the_fam;
    private int h_member;
    private int zone;
    private int monthly_income;
    private LocalDate date_registered;
    private boolean markedForDeletion; // New property for marking deletion
    private boolean deleted; // New field for table purposes

    public DataModel(int household_id, String first_name, String middle_name, String last_name, String suffix, String civil_status, String pos_in_the_fam, int h_member, int zone, int monthly_income, LocalDate date_registered) {
        this.household_id = household_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.suffix = suffix;
        this.civil_status = civil_status;
        this.pos_in_the_fam = pos_in_the_fam;
        this.h_member = h_member;
        this.zone = zone;
        this.monthly_income = monthly_income;
        this.date_registered = date_registered;
    }

    // Getters and setters for all properties
    public int getHousehold_id() {
        return household_id;
    }

    public void setHousehold_id(int household_id) {
        this.household_id = household_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCivil_status() {
        return civil_status;
    }

    public void setCivil_status(String civil_status) {
        this.civil_status = civil_status;
    }

    public String getPos_in_the_fam() {
        return pos_in_the_fam;
    }

    public void setPos_in_the_fam(String pos_in_the_fam) {
        this.pos_in_the_fam = pos_in_the_fam;
    }

    public int getH_member() {
        return h_member;
    }

    public void setH_member(int h_member) {
        this.h_member = h_member;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(int monthly_income) {
        this.monthly_income = monthly_income;
    }

    public LocalDate getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(LocalDate date_registered) {
        this.date_registered = date_registered;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(date_registered, currentDate);
        return age.getYears();
    }

    public String getFullName() {
        StringBuilder fullNameBuilder = new StringBuilder();
        if (first_name != null && !first_name.isEmpty()) {
            fullNameBuilder.append(first_name).append(" ");
        }
        if (middle_name != null && !middle_name.isEmpty()) {
            fullNameBuilder.append(middle_name).append(" ");
        }
        if (last_name != null && !last_name.isEmpty()) {
            fullNameBuilder.append(last_name).append(" ");
        }
        if (suffix != null && !suffix.isEmpty()) {
            fullNameBuilder.append(suffix).append(" ");
        }
        return fullNameBuilder.toString().trim(); 
    }
}
