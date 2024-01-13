package residentRecord;

import java.util.Date;
import javafx.util.converter.ShortStringConverter;

public class Resident {

    private String basicInfo;
    private String actualInfo;

    private String fName;
    private String mName;
    private String lName;
    private String suffix;
    private String Gender;
    private int age;
    private Date birthdate; //   mm/dd/yyyy
    private String Religion;  //catholic, protestant, Inglesia ni Kristo, Aglipay, Islam, Other(Specify)
    private String civilStatus; //Single, Legally Married, widowed, divorce/separated, common law?/live in
    private String inOutSchool; // I. out of school(elem lvl, elem grad, h/s lvl, h/s lvl, col lvl, col grad, post grad, voc)
                                // II. in school (elem lvl, h/x lvl, col lvl, post grad, voc)
    private String educAttainment;
    private String occupation; // write specifically
    private String laborForce;
    private String ispregnant; //check if yes
    private String nursingMother; //inaalagaan ang ina???
    private String familyPlanning;  //if yes, write specifically
    private String Disability; //if yes, write the disability
    private int purok;
    private int household;

    public Resident( String fName, String mName, String lName, String suffix, String Gender, int age, Date birthdate, String Religion, String civilStatus, String inOutSchool, String educAttainment, String occupation, String laborForce, String ispregnant, String nursingMother, String familyPlanning, String Disability, int purok, int household) {
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.suffix = suffix;
        this.Gender = Gender;
        this.age = age;
        this.birthdate = birthdate;
        this.Religion = Religion;
        this.civilStatus = civilStatus;
        this.inOutSchool = inOutSchool;
        this.educAttainment = educAttainment;
        this.occupation = occupation;
        this.laborForce = laborForce;
        this.ispregnant = ispregnant;
        this.nursingMother = nursingMother;
        this.familyPlanning = familyPlanning;
        this.Disability = Disability;
        this.purok = purok;
        this.household = household;
    }
    

    public Resident(String basicInfo, String actualInfo) {
        this.basicInfo = basicInfo;
        this.actualInfo = actualInfo;
    }

//    getters

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

    public String isIspregnant() {
        return ispregnant;
    }

    public String isIsnursingMother() {
        return nursingMother;
    }

    public String isPracticefamilyPlanning() {
        return familyPlanning;
    }

    public String isWithDisability() {
        return Disability;
    }

    public String getInOutSchool() {
        return inOutSchool;
    }

    public String getIspregnant() {
        return ispregnant;
    }

    public String getNursingMother() {
        return nursingMother;
    }

    public String getFamilyPlanning() {
        return familyPlanning;
    }

    public String getDisability() {
        return Disability;
    }

    public String getLaborForce() {
        return laborForce;
    }

    public String getFullName() {
        return lName.toUpperCase() + ", " + fName.toUpperCase() + " " + mName.toUpperCase()+ " "+suffix.toUpperCase();
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public String getActualInfo() {
        return actualInfo;
    }

    public int getPurok() {
        return purok;
    }

    public int getHousehold() {
        return household;
    }

    public String getSuffix() {
        return suffix;
    }

}
