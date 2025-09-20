/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class ReceiptVoucher {

    private int id;
    private String date;
    private double cash, vodafone, instaPay, total;
    private String payer, notes;

    public ReceiptVoucher(int id, String date, double cash, double vodafone, double instaPay, String payer, String notes) {
        this.id = id;
        this.date = date;
        this.cash = cash;
        this.vodafone = vodafone;
        this.instaPay = instaPay;
        this.payer = payer;
        this.notes = notes;
    }
    public ReceiptVoucher(int id, String date, double cash, double vodafone, double instaPay, String payer, String notes, double total) {
        this.id = id;
        this.date = date;
        this.cash = cash;
        this.vodafone = vodafone;
        this.instaPay = instaPay;
        this.payer = payer;
        this.notes = notes;
        this.total = total;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getCash() {
        return cash;
    }

    public double getVodafone() {
        return vodafone;
    }

    public double getInstaPay() {
        return instaPay;
    }

    public String getPayer() {
        return payer;
    }

    public String getNotes() {
        return notes;
    }

    public double getTotal() {
        return total;
    }

}
