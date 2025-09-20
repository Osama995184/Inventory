/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

public class Returns {

    private int return_id;
    private int product_id;
    private int invoice_id;
    private int quantity_returned;
    private String return_reason;
    private String return_date;
    private double price_of_return;
    private String model_name;
    private String model_color;
    private String model_size;
    private String warehouse;
    private String type;
    private String color;
    private String size;
    private String unit;

    public Returns() {
    }
    
    public Returns(int return_id, int product_id, int invoice_id, int quantity_returned, String return_reason, String return_date, double price_of_return, String model_name, String model_color, String model_size) {
        this.return_id = return_id;
        this.product_id = product_id;
        this.invoice_id = invoice_id;
        this.quantity_returned = quantity_returned;
        this.return_reason = return_reason;
        this.return_date = return_date;
        this.price_of_return = price_of_return;
        this.model_name = model_name;
        this.model_color = model_color;
        this.model_size = model_size;
    }

    /**
     * @return the return_id
     */
    public int getReturn_id() {
        return return_id;
    }

    /**
     * @param return_id the return_id to set
     */
    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the invoice_id
     */
    public int getInvoice_id() {
        return invoice_id;
    }

    /**
     * @param invoice_id the invoice_id to set
     */
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     * @return the quantity_returned
     */
    public int getQuantity_returned() {
        return quantity_returned;
    }

    /**
     * @param quantity_returned the quantity_returned to set
     */
    public void setQuantity_returned(int quantity_returned) {
        this.quantity_returned = quantity_returned;
    }

    /**
     * @return the return_reason
     */
    public String getReturn_reason() {
        return return_reason;
    }

    /**
     * @param return_reason the return_reason to set
     */
    public void setReturn_reason(String return_reason) {
        this.return_reason = return_reason;
    }

    /**
     * @return the return_date
     */
    public String getReturn_date() {
        return return_date;
    }

    /**
     * @param return_date the return_date to set
     */
    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    /**
     * @return the price_of_return
     */
    public double getPrice_of_return() {
        return price_of_return;
    }

    /**
     * @param price_of_return the price_of_return to set
     */
    public void setPrice_of_return(double price_of_return) {
        this.price_of_return = price_of_return;
    }

    /**
     * @return the model_name
     */
    public String getModel_name() {
        return model_name;
    }

    /**
     * @param model_name the model_name to set
     */
    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    /**
     * @return the model_color
     */
    public String getModel_color() {
        return model_color;
    }

    /**
     * @param model_color the model_color to set
     */
    public void setModel_color(String model_color) {
        this.model_color = model_color;
    }

    /**
     * @return the model_size
     */
    public String getModel_size() {
        return model_size;
    }

    /**
     * @param model_size the model_size to set
     */
    public void setModel_size(String model_size) {
        this.model_size = model_size;
    }

    /**
     * @return the warehouse
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * @param warehouse the warehouse to set
     */
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
