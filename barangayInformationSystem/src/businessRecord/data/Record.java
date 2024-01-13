/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businessrecord.data;

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
    
    
}