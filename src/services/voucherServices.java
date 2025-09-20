/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.ReceiptVoucher;
import inventory.database;
import inventory.mainFormController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author OSOS
 */
public class voucherServices {

    alert al = new alert();
    private mainFormController controller;

    public voucherServices(mainFormController controller) {
        this.controller = controller;
    }

    // Load data into Table
    public ObservableList<ReceiptVoucher> loadReceiptVouchersList(ObservableList<ReceiptVoucher> voucherList) {
        voucherList.clear();
        String sql = "SELECT id, date, cash, vodafone, instaPay, payer, notes, (cash + vodafone + instaPay) AS total FROM receipt_voucher";

        try (Connection conn = database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                voucherList.add(new ReceiptVoucher(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getDouble("cash"),
                        rs.getDouble("vodafone"),
                        rs.getDouble("instaPay"),
                        rs.getString("payer"),
                        rs.getString("notes"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            al.showAlert("خطأ", e.getMessage(), Alert.AlertType.ERROR);
        }
        return voucherList;
    }

    // Add new Receipt Voucher
    public void addReceiptVoucher(String payer, double cash, double vodafone, double instaPay, String notes) {
        String sql = "INSERT INTO receipt_voucher (date, cash, vodafone, instaPay, payer, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocalDate.now().toString());
            ps.setDouble(2, cash);
            ps.setDouble(3, vodafone);
            ps.setDouble(4, instaPay);
            ps.setString(5, payer);
            ps.setString(6, notes);
            ps.executeUpdate();

            al.showAlert("نجاح", "تمت إضافة سند القبض بنجاح", Alert.AlertType.INFORMATION);
            controller.loadReceiptVouchers();
            controller.clearReceiptVoucherFields();
        } catch (SQLException e) {
            al.showAlert("خطأ", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Update Receipt Voucher
    public void updateReceiptVoucher(int id, String payer, double cash, double vodafone, double instaPay, String notes) {
        String sql = "UPDATE receipt_voucher SET payer=?, cash=?, vodafone=?, instaPay=?, notes=? WHERE id=?";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payer);
            ps.setDouble(2, cash);
            ps.setDouble(3, vodafone);
            ps.setDouble(4, instaPay);
            ps.setString(5, notes);
            ps.setInt(6, id);
            ps.executeUpdate();

            al.showAlert("نجاح", "تم تعديل سند القبض بنجاح", Alert.AlertType.INFORMATION);
            controller.loadReceiptVouchers();
            controller.clearReceiptVoucherFields();
        } catch (SQLException e) {
            al.showAlert("خطأ", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public ObservableList<ReceiptVoucher> searchReceiptVouchers(LocalDate fromDatePickerReceiptVoucher, LocalDate toDatePickerReceiptVoucher, ObservableList<ReceiptVoucher> voucherList) {
        LocalDate fromDate = fromDatePickerReceiptVoucher;
        LocalDate toDate = toDatePickerReceiptVoucher;

        voucherList.clear();

        // Base query
        StringBuilder sql = new StringBuilder(
                "SELECT id, date, cash, vodafone, instaPay, payer, notes, "
                + "(cash + vodafone + instaPay) AS total "
                + "FROM receipt_voucher WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Filter by date range
        if (fromDate != null && toDate != null) {
            sql.append(" AND date BETWEEN ? AND ? ");
            params.add(fromDate.toString());
            params.add(toDate.toString());
        } else if (fromDate != null) {
            sql.append(" AND date >= ? ");
            params.add(fromDate.toString());
        } else if (toDate != null) {
            sql.append(" AND date <= ? ");
            params.add(toDate.toString());
        }

        sql.append(" ORDER BY date DESC");

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Fill parameters dynamically
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    voucherList.add(new ReceiptVoucher(
                            rs.getInt("id"),
                            rs.getString("date"),
                            rs.getDouble("cash"),
                            rs.getDouble("vodafone"),
                            rs.getDouble("instaPay"),
                            rs.getString("payer"),
                            rs.getString("notes"),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ في البحث", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return voucherList;
    }
}
