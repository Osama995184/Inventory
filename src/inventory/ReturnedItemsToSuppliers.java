/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class ReturnedItemsToSuppliers {

    private int id, item_id, mNumberavailable, mNumberReturn, invoicesID;
    private String model, type, suppliers;
    private String color, size, unit;
    private String wareHouse;
    private String date;
    private Double wholesalePrice;

    public ReturnedItemsToSuppliers(int id, int item_id, String model, int mNumberReturn
            , Double wholesalePrice, String type, String suppliers, String wareHouse
            , String date, String color, String size, String unit, int invoicesID) {
        this.id = id;
        this.item_id = item_id;
        this.model = model;
        this.mNumberReturn = mNumberReturn;
        this.wholesalePrice = wholesalePrice;
        this.type = type;
        this.suppliers = suppliers;
        this.wareHouse = wareHouse;
        this.date = date;
        this.size = size;
        this.color = color;
        this.unit = unit;
        this.invoicesID = invoicesID;
    }

    public int getId() {
        return id;
    }

    public int getMNumberavailable() {
        return mNumberavailable;
    }

    public String getModel() {
        return model;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public String getType() {
        return type;
    }

    public int getInvoicesID() {
        return invoicesID;
    }

    public String getSuppliers() {
        return suppliers;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getMNumberReturn() {
        return mNumberReturn;
    }

    /**
     * @return the wareHouse
     */
    public String getWareHouse() {
        return wareHouse;
    }

    /**
     * @param wareHouse the wareHouse to set
     */
    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}