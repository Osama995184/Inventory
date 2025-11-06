/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Tiger
 */
public class SettingsDAO {

    alert al = new alert();

    public int getPrintType() {
        String sql = "SELECT print_type FROM settings WHERE id = 1";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("print_type"); // 0 = A4 , 1 = Receipt
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء استدعاء نوع الفاتورة" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return 0; // default A4
    }

    public void updateSetting(int value) {

        String sql = "UPDATE settings SET `print_type` = ? WHERE id = 1";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, value);
            ps.executeUpdate();
            al.E_Alert("تم تحديث نوع الفاتورة بنجاح الرجاء تسجيل الخروح حتى يتم تطبيق التحديثات", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحديث نوع الفاتورة" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();        }
    }

}
