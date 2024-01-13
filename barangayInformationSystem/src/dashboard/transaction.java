package dashboard;

public class transaction {
    private String trans_name, name, address, sex, citizeship, dateIssued;
    private int treasuryId, barangayId, tin, commTax, taxAmount, total;

    public transaction(String trans_name, String name, String address, String sex, String citizeship, String dateIssued, int treasuryId, int barangayId, int tin, int commTax, int taxAmount, int total) {
        this.trans_name = trans_name;
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.citizeship = citizeship;
        this.dateIssued = dateIssued;
        this.treasuryId = treasuryId;
        this.barangayId = barangayId;
        this.tin = tin;
        this.commTax = commTax;
        this.taxAmount = taxAmount;
        this.total = total;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizeship() {
        return citizeship;
    }

    public void setCitizeship(String citizeship) {
        this.citizeship = citizeship;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public int getTreasuryId() {
        return treasuryId;
    }

    public void setTreasuryId(int treasuryId) {
        this.treasuryId = treasuryId;
    }

    public int getBarangayId() {
        return barangayId;
    }

    public void setBarangayId(int barangayId) {
        this.barangayId = barangayId;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public int getCommTax() {
        return commTax;
    }

    public void setCommTax(int commTax) {
        this.commTax = commTax;
    }

    public int getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(int taxAmount) {
        this.taxAmount = taxAmount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
