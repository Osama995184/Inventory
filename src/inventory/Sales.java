package inventory;

public class Sales {

    private int sales_id;
    private int invoice_id;
    private int model_id;
    private int quantity;
    private double total_price;
    private String date;
    private String em_username;
    private String model_name;
    private String type;
    private String warehouse;
    private String color;
    private String size;
    private String unit;

    public Sales(int sales_id, int invoice_id, int model_id, int quantity, double total_price, String date, String em_username, String model_name, String type, String warehouse, String color, String size, String unit) {
        this.sales_id = sales_id;
        this.invoice_id = invoice_id;
        this.model_id = model_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.date = date;
        this.em_username = em_username;
        this.model_name = model_name;
        this.type = type;
        this.warehouse = warehouse;
        this.size = size;
        this.color = color;
        this.unit = unit;
    }

    public Sales(int sales_id, int invoice_id, int model_id, int quantity, double total_price, String date, String em_username, String model_name, String type, String warehouse) {
        this(sales_id, invoice_id, model_id, quantity, total_price, date, em_username, model_name, type, warehouse, null, null, null);
    }

    public int getSales_id() {
        return sales_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getDate() {
        return date;
    }

    public String getEm_username() {
        return em_username;
    }

    public String getModel_name() {
        return model_name;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public String getType() {
        return type;
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
