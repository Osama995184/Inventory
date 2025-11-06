package inventory;

public class productData {

    private int id, soldNo, invoicesID;
    private String model, type, suppliers;
    private String color, size, unit;
    private Double wholesalePrice, realPrice;
    private String name;
    private String phone;
    private String date;
    private String wareHouse;
    private Double total;
    private Double mNumberavailable;
    private int modelID;

    public productData(int id, String model, Double mNumberavailable, Double wholesalePrice, Double realPrice, String type, int soldNo, String wareHouse, String color, String size, String unit) {
        this.id = id;
        this.model = model;
        this.wholesalePrice = wholesalePrice;
        this.realPrice = realPrice;
        this.mNumberavailable = mNumberavailable;
        this.type = type;
        this.soldNo = soldNo;
        this.wareHouse = wareHouse;
        this.size = size;
        this.color = color;
        this.unit = unit;
    }

    public productData(int id, String model, Double mNumberavailable, Double wholesalePrice, Double realPrice, String type, int soldNo, String unit) {
        this(id, model, mNumberavailable, wholesalePrice, realPrice, type, soldNo, null, null, null, unit);
    }

    public productData() {
    }

    public productData(int id, String model, Double mNumberavailable, Double wholesalePrice, String type, String suppliers, int invoicesID, String wareHouse) {
        this(id, model, mNumberavailable, wholesalePrice, null, type, 0, wareHouse, null, null, null);
    }

    public productData(int id, String model, Double mNumberavailable, Double realPrice, String type, String color, String size, String unit) {
        this(id, model, mNumberavailable, null, realPrice, type, 0, null, color, size, unit);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the mNumberavailable
     */
    public Double getMNumberavailable() {
        return mNumberavailable;
    }

    /**
     * @param mNumberavailable the mNumberavailable to set
     */
    public void setmNumberavailable(Double mNumberavailable) {
        this.mNumberavailable = mNumberavailable;
    }

    /**
     * @return the soldNo
     */
    public int getSoldNo() {
        return soldNo;
    }

    /**
     * @param soldNo the soldNo to set
     */
    public void setSoldNo(int soldNo) {
        this.soldNo = soldNo;
    }

    /**
     * @return the invoicesID
     */
    public int getInvoicesID() {
        return invoicesID;
    }

    /**
     * @param invoicesID the invoicesID to set
     */
    public void setInvoicesID(int invoicesID) {
        this.invoicesID = invoicesID;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
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
     * @return the suppliers
     */
    public String getSuppliers() {
        return suppliers;
    }

    /**
     * @param suppliers the suppliers to set
     */
    public void setSuppliers(String suppliers) {
        this.suppliers = suppliers;
    }

    /**
     * @return the wholesalePrice
     */
    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    /**
     * @param wholesalePrice the wholesalePrice to set
     */
    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    /**
     * @return the realPrice
     */
    public Double getRealPrice() {
        return realPrice;
    }

    /**
     * @param realPrice the realPrice to set
     */
    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the modelID
     */
    public int getModelID() {
        return modelID;
    }

    /**
     * @param modelID the modelID to set
     */
    public void setModelID(int modelID) {
        this.modelID = modelID;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    public double getTotal2() {
        return wholesalePrice * mNumberavailable;
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
