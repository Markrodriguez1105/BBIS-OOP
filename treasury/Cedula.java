/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasury;

import java.text.NumberFormat;
import java.util.Date;

public class Cedula {
    
    private String fName;
    private String mName;
    private String lName;
    private String Gender;
    private int age;
    private double personalIncome;
    private int barangayID;
    private String tin_id;
    private String address;
    private String residencyStatus;
    private Date dateIssued;
    private String purpose;
    final private int commTax = 5;
    private double addComTax;
    private double total;

    public Cedula(String fName, String mName, String lName, String Gender, int age, int personalIncome, int barangayID, 
             String tin_id, String address, String residencyStatus,
            Date dateIssued, String purpose, int commTax, int addComTax, int total) {
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.Gender = Gender;
        this.age = age;
        this.personalIncome = personalIncome;
        this.barangayID = barangayID;
        this.tin_id = tin_id;
        this.address = address;
        this.residencyStatus = residencyStatus;
        this.dateIssued = dateIssued;
        this.purpose = purpose;
        this.addComTax = addComTax;
        this.total = total;
    }

    public String getTin_id() {
        return tin_id;
    }

    public String getAddress() {
        return address;
    }

    public String getResidencyStatus() {
        return residencyStatus;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public int getCommTax() {
        return commTax;
    }

    public double getAddComTax() {
        return addComTax;
    }

    public double getTotal() {
        return total;
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

    public double getPersonalIncome() {
        return personalIncome;
    }

    public int getBarangayID() {
        return barangayID;
    }
    public String getFullName() {
        return lName.toUpperCase() + ", " + fName.toUpperCase() + " " + mName.toUpperCase();
    }
    public String getPurpose() {
        return purpose;
    }
    public String getFormattedPersonalIncome() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(personalIncome);
    }

    public String getFormatnum() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(commTax);
    }

    public String getFormatnum2() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(addComTax);
    }

    public String getFormatnum3() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(total);
    }

    public double getIncome() {
        return personalIncome;
    }

    public double getTax() {
        return commTax;
    }

    public double getAddTax() {
        return addComTax;
    }

    // Other methods
}



