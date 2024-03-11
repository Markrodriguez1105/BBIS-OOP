package HouseholdRecord;

import java.time.LocalDate;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class ResidentInfo {
    private final int residentId;
    private final int householdId;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String suffix;
    private final int age;
    private final LocalDate birthDate;
    private final String gender;
    private final String civilStatus;
    private final String relationToFamilyHead;
    private final LocalDate dateRegistered;
    private int zone;

    // Constructor for initializing all fields
    public ResidentInfo(int residentId, int householdId, String firstName, String middleName, String lastName, String suffix,
                        int age, LocalDate birthDate, String gender, String civilStatus, String relationToFamilyHead,
                        LocalDate dateRegistered, int zone) {
        this.residentId = residentId;
        this.householdId = householdId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.relationToFamilyHead = relationToFamilyHead;
        this.dateRegistered = dateRegistered;
        this.zone = zone;
    }

    // Constructor for initializing from TextField household_id
    public ResidentInfo(int residentId, TextField householdIdField, String firstName, String middleName, String lastName, String suffix,
                        int age, LocalDate birthDate, String gender, String civilStatus, String relationToFamilyHead,
                        LocalDate dateRegistered, int zone) {
        this(residentId, Integer.parseInt(householdIdField.getText()), firstName, middleName, lastName, suffix, age, birthDate,
                gender, civilStatus, relationToFamilyHead, dateRegistered, zone);
    }

    // Constructor for initializing all fields except zone
    public ResidentInfo(int residentId, int householdId, String firstName, String middleName, String lastName, String suffix,
                        int age, LocalDate birthDate, String gender, String civilStatus, String relationToFamilyHead,
                        LocalDate dateRegistered) {
        this(residentId, householdId, firstName, middleName, lastName, suffix, age, birthDate, gender, civilStatus,
                relationToFamilyHead, dateRegistered, 0); // Zone is set to 0 by default
    }

    // Getter methods
    public int getResidentId() {
        return residentId;
    }

    public int getHouseholdId() {
        return householdId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public String getRelationToFamilyHead() {
        return relationToFamilyHead;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public int getZone() {
        return zone;
    }

    // Setter method for zone
    public void setZone(int zone) {
        this.zone = zone;
    }
    public String getFullname(){
        if(suffix != null){
            return lastName.toUpperCase() +", "+firstName.toUpperCase()+" "+ lastName.toUpperCase() +" "+ suffix.toUpperCase();
        }
          return lastName.toUpperCase() +", "+firstName.toUpperCase()+" "+ lastName.toUpperCase();
    }
}
