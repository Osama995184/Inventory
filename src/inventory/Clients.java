/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Osama
 */
public class Clients {
    private final SimpleStringProperty clientName;
    private final SimpleStringProperty clientPhone;
    private final SimpleIntegerProperty totalQuantity;
    private final SimpleDoubleProperty totalAfterDiscount;

    public Clients(String clientName, String clientPhone, int totalQuantity, double totalAfterDiscount) {
        this.clientName = new SimpleStringProperty(clientName);
        this.clientPhone = new SimpleStringProperty(clientPhone);
        this.totalQuantity = new SimpleIntegerProperty(totalQuantity);
        this.totalAfterDiscount = new SimpleDoubleProperty(totalAfterDiscount);
    }

    public String getClientName() { return clientName.get(); }
    public String getClientPhone() { return clientPhone.get(); }
    public int getTotalQuantity() { return totalQuantity.get(); }
    public double getTotalAfterDiscount() { return totalAfterDiscount.get(); }
}

