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
public class ClientSale {

    private final SimpleStringProperty date;
    private final SimpleDoubleProperty totalPrice;
    private final SimpleIntegerProperty quantity;
    private final SimpleStringProperty modelSize;
    private final SimpleStringProperty modelColor;
    private final SimpleStringProperty modelName;
    private final SimpleIntegerProperty modelId;
    private final SimpleIntegerProperty invoiceId;

    public ClientSale(String date, double totalPrice, int quantity, String modelSize, String modelColor,
            String modelName, int modelId, int invoiceId) {
        this.date = new SimpleStringProperty(date);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.modelSize = new SimpleStringProperty(modelSize);
        this.modelColor = new SimpleStringProperty(modelColor);
        this.modelName = new SimpleStringProperty(modelName);
        this.modelId = new SimpleIntegerProperty(modelId);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
    }

    // Getters for TableView
    public String getDate() {
        return date.get();
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public String getModelSize() {
        return modelSize.get();
    }

    public String getModelColor() {
        return modelColor.get();
    }

    public String getModelName() {
        return modelName.get();
    }

    public int getModelId() {
        return modelId.get();
    }

    public int getInvoiceId() {
        return invoiceId.get();
    }
}
