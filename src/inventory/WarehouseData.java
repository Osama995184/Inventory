/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class WarehouseData {

    private int id;
    private String name;
    private String remark;
    private String date;

    // Constructor
    public WarehouseData(int id, String name, String remark, String date) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.date = date;
    }

    // Default Constructor
    public WarehouseData() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
