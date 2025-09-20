/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class recordItemMovement {

    /**
     * دالة عامة لتسجيل حركة صنف في الجدول item_movements
     *
     * @param movementType نوع الحركة (دخول، خروج، تحويل...)
     * @param quantity الكمية
     * @param invoiceId رقم الفاتورة (يمكن أن يكون null)
     * @param remark ملاحظات
     * @param productId رقم المنتج
     * @param productName اسم المنتج
     * @param warehouse اسم المخزن
     */
    public static void recordItemMovement(String movementType, double quantity, Integer invoiceId,
            String remark, int productId, String productName, String warehouse) {
        String sql = "INSERT INTO item_movements (movementType, quantity, invoiceId, date, remark, productId, productName, warehouse) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, movementType);
            ps.setDouble(2, quantity);

            if (invoiceId != null) {
                ps.setInt(3, invoiceId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, remark);
            ps.setInt(5, productId);
            ps.setString(6, productName);
            ps.setString(7, warehouse);

            ps.executeUpdate();

            System.out.println("تم تسجيل حركة الصنف بنجاح: " + movementType);

        } catch (SQLException e) {
            System.err.println("خطأ أثناء تسجيل حركة الصنف: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
