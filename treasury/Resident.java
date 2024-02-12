package treasury;

import java.util.Date;

public class Resident {

    private String basicInfo;
    private String actualInfo;

    private int residentID;
    private String fName;
    private String mName;
    private String lName;
    private String suffix;
    private String Gender;
    private int age;
    private Date birthdate; 
    private String Religion;  
    private String civilStatus; 
    private String educationalStatus; 
    private String educationAttainment;
    private String occupation;
    private int personalIncome;
    private String Disability; 
    private int purok;
    private long phoneNo;
    private String email;
    private int barangayID;
    private String voterStatus;
    private String nationality;
    private String bloodType;
    private int livingDuration;
    private String birthplace;
    private int householdNo;
    private String relationWithFamHead;
    private int height;
    private int weight;
    private String motherName;
    private long motherPhoneNo;
    private String fatherName;
    private long fatherPhoneNo;
    private String inOutBarangay;

    public Resident(int residentID, String fName, String mName, String lName, String suffix, String Gender,
            int age, Date birthdate, String Religion, String civilStatus, String educationalStatus, String educationAttainment,
            String occupation, int personalIncome, String Disability, int purok,
            long phoneNo, String email, int barangayID, String voterStatus, String nationality, String bloodType,
            int livingDuration, String birthplace, int householdNo, String relationWithFamHead, int height,
            int weight, String motherName, long motherPhoneNo, String fatherName, long fatherPhoneNo, String inoutbarangay) {

        this.residentID = residentID;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.suffix = suffix;
        this.Gender = Gender;
        this.age = age;
        this.birthdate = birthdate;
        this.Religion = Religion;
        this.civilStatus = civilStatus;
        this.educationAttainment = educationAttainment;
        this.educationalStatus = educationalStatus;
        this.occupation = occupation;
        this.personalIncome = personalIncome;
        this.Disability = Disability;
        this.purok = purok;
        this.phoneNo = phoneNo;
        this.email = email;
        this.barangayID = barangayID;
        this.voterStatus = voterStatus;
        this.nationality = nationality;
        this.bloodType = bloodType;
        this.livingDuration = livingDuration;
        this.birthplace = birthplace;
        this.householdNo = householdNo;
        this.relationWithFamHead = relationWithFamHead;
        this.height = height;
        this.weight = weight;
        this.motherName = motherName;
        this.motherPhoneNo = motherPhoneNo;
        this.fatherName = fatherName;
        this.fatherPhoneNo = fatherPhoneNo;
        this.inOutBarangay = inoutbarangay;
    }

    public Resident(String fname, String mname, String lname, int householdNo) {
        this.fName = fname;
        this.mName = mname;
        this.lName = lname;
        this.householdNo = householdNo;
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

    public String getOccupation() {
        return occupation;
    }

    public String isWithDisability() {
        return Disability;
    }

    public String getDisability() {
        return Disability;
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

    public String getSuffix() {
        return suffix;
    }

    public int getResidentID() {
        return residentID;
    }

    public int getPersonalIncome() {
        return personalIncome;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public int getBarangayID() {
        return barangayID;
    }

    public String getVoterStatus() {
        return voterStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getLivingDuration() {
        return livingDuration;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public int getHouseholdNo() {
        return householdNo;
    }

    public String getRelationWithFamHead() {
        return relationWithFamHead;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getMotherName() {
        return motherName;
    }

    public long getMotherPhoneNo() {
        return motherPhoneNo;
    }

    public String getFatherName() {
        return fatherName;
    }

    public long getFatherPhoneNo() {
        return fatherPhoneNo;
    }

    public String getInOutBarangay() {
        return inOutBarangay;
    }

    public String getEducationalStatus() {
        return educationalStatus;
    }

    public String getEducationAttainment() {
        return educationAttainment;
    }

    public String getFullName() {
        return lName.toUpperCase() + ", " + fName.toUpperCase() + " " + mName.toUpperCase() + " " + suffix.toUpperCase();
    }

    public String getHouseholdFullName() {
        if (suffix != null && suffix.isEmpty()) {
            return lName.toUpperCase() + ", " + fName.toUpperCase() + " " + mName.toUpperCase() + " " + suffix.toUpperCase();
        }
        return lName.toUpperCase() + ", " + fName.toUpperCase() + " " + mName.toUpperCase();
    }
}