/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import javafx.beans.property.*;

/**
 *
 * @author Osama
 */
public class Supplier {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address;
    private final StringProperty remarks;

    public Supplier(int id, String name, String phone, String address, String remarks) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.remarks = new SimpleStringProperty(remarks);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getRemarks() {
        return remarks.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty remarksProperty() {
        return remarks;
    }
}
