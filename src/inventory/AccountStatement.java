/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class AccountStatement {

    private int invoiceId;
    private int returnItem;
    private String supName, phone, date, remarks;
    private Double cash, deferred, totalRetrun, totalInvoice;

    public AccountStatement(int invoiceId, int returnItem, String supName, String phone, String date, String remarks, Double cash, Double deferred, Double totalRetrun, Double totalInvoice) {
        this.invoiceId = invoiceId;
        this.returnItem = returnItem;
        this.supName = supName;
        this.phone = phone;
        this.date = date;
        this.remarks = remarks;
        this.cash = cash;
        this.deferred = deferred;
        this.totalRetrun = totalRetrun;
        this.totalInvoice = totalInvoice;
    }

    public Double getCash() {
        return cash;
    }

    public String getDate() {
        return date;
    }

    public Double getDeferred() {
        return deferred;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getPhone() {
        return phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getReturnItem() {
        return returnItem;
    }

    public String getSupName() {
        return supName;
    }

    public Double getTotalRetrun() {
        return totalRetrun;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }
}
