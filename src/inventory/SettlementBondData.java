/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Osama
 */
public class SettlementBondData {

    private final SimpleStringProperty date;
    private final SimpleDoubleProperty totalSettlementAmount;
    private final SimpleDoubleProperty difference;
    private final SimpleDoubleProperty actualBalance;
    private final SimpleDoubleProperty bookBalance;
    private final SimpleStringProperty warehouseName;
    private final SimpleStringProperty itemType;
    private final SimpleStringProperty itemName;
    private final SimpleIntegerProperty itemId;
    private final SimpleIntegerProperty id;

    public SettlementBondData(int id, double totalSettlementAmount, double difference,
            double actualBalance, double bookBalance, String warehouseName,
            String itemType, String itemName, int itemId) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.date = new SimpleStringProperty(today);

        this.totalSettlementAmount = new SimpleDoubleProperty(totalSettlementAmount);
        this.difference = new SimpleDoubleProperty(difference);
        this.actualBalance = new SimpleDoubleProperty(actualBalance);
        this.bookBalance = new SimpleDoubleProperty(bookBalance);
        this.warehouseName = new SimpleStringProperty(warehouseName);
        this.itemType = new SimpleStringProperty(itemType);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemId = new SimpleIntegerProperty(itemId);
        this.id = new SimpleIntegerProperty(id);
    }
    
    public SettlementBondData(double totalSettlementAmount, double difference,
            double actualBalance, double bookBalance, String warehouseName,
            String itemType, String itemName, int itemId) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.date = new SimpleStringProperty(today);

        this.totalSettlementAmount = new SimpleDoubleProperty(totalSettlementAmount);
        this.difference = new SimpleDoubleProperty(difference);
        this.actualBalance = new SimpleDoubleProperty(actualBalance);
        this.bookBalance = new SimpleDoubleProperty(bookBalance);
        this.warehouseName = new SimpleStringProperty(warehouseName);
        this.itemType = new SimpleStringProperty(itemType);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemId = new SimpleIntegerProperty(itemId);
        this.id = null;
    }

    // Getters
    public String getDate() {
        return date.get();
    }

    public double getTotalSettlementAmount() {
        return totalSettlementAmount.get();
    }

    public double getDifference() {
        return difference.get();
    }

    public double getActualBalance() {
        return actualBalance.get();
    }

    public double getBookBalance() {
        return bookBalance.get();
    }

    public String getWarehouseName() {
        return warehouseName.get();
    }

    public String getItemType() {
        return itemType.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public int getItemId() {
        return itemId.get();
    }

    public int getId() {
        return id.get();
    }
}
