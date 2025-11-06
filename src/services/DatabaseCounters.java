/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tiger
 */
public class DatabaseCounters {

    // Generic method to get row count from any table
    private int getTableCount(String tableName) {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM " + tableName;
        try (Connection connect = database.getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error counting table: " + tableName);
            e.printStackTrace();
        }
        return count;
    }

    public Boolean isAvailable(String tableName) {
//        return getTableCount(tableName) <= 10;
        return true;
    }
}
