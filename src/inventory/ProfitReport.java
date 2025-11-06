package inventory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Osama
 */
public class ProfitReport {

    private String type;
    private int totalQuantity;
    private double totalWholesalesPrice;
    private double totalPrice;
    private double gain;

    public ProfitReport(String type, int totalQuantity, double totalWholesalesPrice, double totalPrice, double gain) {
        this.type = type;
        this.totalQuantity = totalQuantity;
        this.totalWholesalesPrice = totalWholesalesPrice;
        this.totalPrice = totalPrice;
        this.gain = gain;
    }

    // Getters
    public String getType() {
        return type;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public double getTotalWholesalesPrice() {
        return totalWholesalesPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getGain() {
        return gain;
    }
}
