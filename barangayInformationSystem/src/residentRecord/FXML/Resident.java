package residentRecord.FXML;

import java.util.Date;

public class Resident {
    private int FamilyNo;
    private String fName;
    private String mName;
    private String lName;
    private String Gender;
    private int age;
    private int contactNo;
    private Date birthdate; //   mm/dd/yyyy
    private String Religion;  //catholic, protestant, Inglesia ni Kristo, Aglipay, Islam, Other(Specify)
    private String civilStatus; //Single, Legally Married, widowed, divorce/separated, common law?/live in
    private String educAttainment; // I. out of school(elem lvl, elem grad, h/s lvl, h/s lvl, col lvl, col grad, post grad, voc)
                           // II. in school (elem lvl, h/x lvl, col lvl, post grad, voc)
    private String occupation; // write specifically
    private boolean ispregnant; //check if yes
    private boolean isnursingMother; //inaalagaan ang ina???
    private boolean practicefamilyPlanning;  //if yes, write specifically
    private boolean withDisability; //if yes, write the disability

    public Resident(String fName, String mName, String lName, String Gender, int age, Date birthdate) {
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.Gender = Gender;
        this.age = age;
        this.birthdate =birthdate;
    }
    
    
//    getters
    public int getFamilyNo() {
        return FamilyNo;
    }
    public String getfName() {
        return fName;
    }
    public String getmName() {
        return mName;
    }
    public String getlName() {
        return lName;
    }
    public String getGender() {
        return Gender;
    }
    public int getAge() {
        return age;
    }
    public int getContactNo() {
        return contactNo;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public String getReligion() {
        return Religion;
    }
    public String getCivilStatus() {
        return civilStatus;
    }
    public String getEducAttainment() {
        return educAttainment;
    }
    public String getOccupation() {
        return occupation;
    }
    public boolean isIspregnant() {
        return ispregnant;
    }
    public boolean isIsnursingMother() {
        return isnursingMother;
    }
    public boolean isPracticefamilyPlanning() {
        return practicefamilyPlanning;
    }
    public boolean isWithDisability() {
        return withDisability;
    }
    public  String getFullName(){
        return lName.toUpperCase() + ", " + fName.toUpperCase() + " " +  mName.toUpperCase();
    }
    
}
