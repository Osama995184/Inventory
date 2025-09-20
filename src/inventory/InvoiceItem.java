/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class InvoiceItem {

    public double getPrice_beforDis() {
        return price_beforDis;
    }

    private Integer modelID;
    private String model;
    private String wareHouse;
    private String color;
    private String size;
    private String unit;
    private double price_beforDis;
    private Double quantity;
    private double totalP;
    private double totalPriceBeforeDis;
    private double price;
    private Timestamp date = Timestamp.valueOf(LocalDateTime.now());

    public InvoiceItem(Integer modelID, String model, Double quantity, double totalP, double price
            , double price_beforDis, double totalPriceBeforeDis, String wareHouse) {
        this.model = model;
        this.quantity = quantity;
        this.totalP = totalP;
        this.modelID = modelID;
        this.price = price;
        this.price_beforDis = price_beforDis;
        this.totalPriceBeforeDis = totalPriceBeforeDis;
        this.wareHouse = wareHouse;
    }
    public InvoiceItem(Integer modelID, String model, Double quantity, double totalP, double price
            , double price_beforDis, double totalPriceBeforeDis, String wareHouse,String color,String size) {
        this.model = model;
        this.quantity = quantity;
        this.totalP = totalP;
        this.modelID = modelID;
        this.price = price;
        this.price_beforDis = price_beforDis;
        this.totalPriceBeforeDis = totalPriceBeforeDis;
        this.wareHouse = wareHouse;
        this.size = size;
        this.color = color;
//        this.unit = unit;
    }


    public Integer getModelID() {
        return modelID;
    }

    public String getModel() {
        return model;
    }

    public Double getQuantity() {
        return quantity;
    }

    public double getTotalP() {
        return totalP;
    }

    public double getPrice() {
        return price;
    }

    public Timestamp getDate() {
        return date;
    }

    public double getTotalPriceBeforeDis() {
        return totalPriceBeforeDis;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setTotalP(double totalP) {
        this.totalP = totalP;
    }

    public void setTotalPriceBeforeDis(double totalPriceBeforeDis) {
        this.totalPriceBeforeDis = totalPriceBeforeDis;
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