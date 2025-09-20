/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import javafx.scene.control.Alert;

/**
 *
 * @author OSOS
 */
public class alert {

    private Alert alert;

    public void E_Alert(String ms, Alert.AlertType Atype) {
        setAlert(new Alert(Atype));
        getAlert().setTitle("Warning Message");
        getAlert().setHeaderText(null);
        getAlert().setContentText(ms);
        getAlert().showAndWait();
    }

    public void Confirmation_Alert(String ms) {
        setAlert(new Alert(Alert.AlertType.CONFIRMATION));
        getAlert().setTitle("Warning Message");
        getAlert().setHeaderText(null);
        getAlert().setContentText(ms);
        getAlert().showAndWait(); // أو alert.show() فقط
    }

    public void information_Alert(String ms) {
        setAlert(new Alert(Alert.AlertType.INFORMATION));
        getAlert().setTitle("Information");
        getAlert().setHeaderText(null);
        getAlert().setContentText(ms);
        getAlert().showAndWait(); // أو alert.show() فقط
    }

    public void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

     public void Confirmation_AlertOptions(String ms) {
        setAlert(new Alert(Alert.AlertType.CONFIRMATION));
        getAlert().setTitle("Warning Message");
        getAlert().setHeaderText(null);
        getAlert().setContentText(ms);
    }

    
    /**
     * @return the alert
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
