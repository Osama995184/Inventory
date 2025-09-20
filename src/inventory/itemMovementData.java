/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.time.LocalDate;

public class itemMovementData {

    private int movementId;       // رقم الحركة
    private int productId;          // رقم الصنف
    private String productName;        // اسم الصنف
    private String movementType;  // نوع الحركة (IN/OUT/ADJUST)
    private Double quantity;         // الكمية
    private int invoiceId;        // رقم الفاتورة المرتبطة
    private String date;       // تاريخ الحركة
    private String remark;        // ملاحظات الحركة
    private String warehouse;        // اسم المخون

    public itemMovementData(int movementId, String movementType, Double quantity,
            int invoiceId, String date, String remark, int productId, String warehouse, String productName) {
        this.movementId = movementId;
        this.productId = productId;
        this.movementType = movementType;
        this.quantity = quantity;
        this.invoiceId = invoiceId;
        this.date = date;
        this.remark = remark;
        this.warehouse = warehouse;
        this.productName = productName;
    }

    // ===== Getters and Setters =====
    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }
}
