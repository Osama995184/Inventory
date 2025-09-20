package inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:cstoredb.db";  
            conn = DriverManager.getConnection(url);
            System.out.println("✅ Connected to SQLite database");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
        return conn;
    }
}
