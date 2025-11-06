/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class InvoiceModel {

    private int id;
    private String supplierName;
    private String invoiceOutId;
    private String invoiceDate;
    private String remarks;
    private double totalPrice;
    private double cash;
    private double deferred;

    public InvoiceModel() {
    }
    
    public InvoiceModel(int id, String supplierName, String invoiceOutId, String invoiceDate,
            double totalPrice, double cash,
            double deferred, String remarks) {
        this.id = id;
        this.supplierName = supplierName;
        this.invoiceOutId = invoiceOutId;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
        this.cash = cash;
        this.deferred = deferred;
        this.remarks = remarks;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getInvoiceOutId() {
        return invoiceOutId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getCash() {
        return cash;
    }

    public double getDeferred() {
        return deferred;
    }
}
