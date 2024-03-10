/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businessRecord;
import java.util.Date;
/**
 *
 * @author renmaee
 */
public class Record {
     private int id;
    private String Name;
    private String Address;
    private String Type;
    private int Income;
    private int Cost;

    public Record(int id, String Name, String Address, String Type, int Income, int Cost) {
        this.id = id;
        this.Name = Name;
        this.Address = Address;
        this.Type = Type;
        this.Income = Income;
        this.Cost = Cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getIncome() {
        return Income;
    }

    public void setIncome(int Income) {
        this.Income = Income;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int Cost) {
        this.Cost = Cost;
    }
    
   

    private int business_id, barangay_id, tin, num_employees, monthly_income;
    private String owner_phone_num;
    String business_name;
    String business_type;
    private String owner, address, status, vat_status;
    private Date date_establishment, date_registered;
    private String owner_email;
    private int recordId;
    private String first_name;
    private String middle_name;
    private String last_name;

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }
    private String active_status;

    public int getRecordId() {
        return recordId;
    }

    public Record(int business_id, int barangay_id, String business_name, String business_type, String first_name, String middle_name, String last_name, int monthly_income, Date date_establishment, int tin, int num_employees, Date date_registered, String owner_phone_num, String address, String active_status, String vat_status, String owner_email) {
        this.business_id = business_id;
        this.barangay_id = barangay_id;
        this.business_name = business_name;
        this.business_type = business_type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.owner_phone_num = owner_phone_num;
        this.address = address;
        this.monthly_income = monthly_income;
        this.date_establishment = date_establishment;
        this.tin = tin;
        this.vat_status = vat_status;
        this.num_employees = num_employees;
        this.date_registered = date_registered;
        this.owner_email = owner_email;
        this.active_status = active_status;
    }

    public Record(String address) {
        this.address = address;
    }

    public void setOwner_Email(String email_ID) {
        this.owner_email = email_ID;
    }

    public String getOwner_Email() {
        return owner_email;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getBarangay_id() {
        return barangay_id;
    }

    public void setBarangay_id(int barangay_id) {
        this.barangay_id = barangay_id;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public int getNum_employees() {
        return num_employees;
    }

    public void setNum_employees(int num_employees) {
        this.num_employees = num_employees;
    }

    public int getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(int monthly_income) {
        this.monthly_income = monthly_income;
    }

    public String getOwner_phone_num() {
        return owner_phone_num;
    }

    public void setOwner_phone_num(String owner_phone_num) {
        this.owner_phone_num = owner_phone_num;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVat_status() {
        return vat_status;
    }

    public void setVat_status(String vat_status) {
        this.vat_status = vat_status;
    }

    public Date getDate_establishment() {
        return date_establishment;
    }

    public void setDate_establishment(Date date_establishment) {
        this.date_establishment = date_establishment;
    }

    public Date getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(Date date_registered) {
        this.date_registered = date_registered;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

}
