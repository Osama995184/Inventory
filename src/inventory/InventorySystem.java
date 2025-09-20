package inventory;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Osama
 */
public class InventorySystem extends Application {

    private void startAutoBackup() {
        Thread backupThread = new Thread(() -> AutoBackup.main(new String[]{}));
        backupThread.setDaemon(true);
        backupThread.start();
    }

    public void startServices() {
        try {
            new ProcessBuilder("cmd", "/c", "C:\\xampp-copy\\apache_start.bat").start();
            new ProcessBuilder("cmd", "/c", "C:\\xampp-copy\\mysql_start.bat").start();
            System.out.println("✅ Apache and MySQL started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServices() {
        try {
            new ProcessBuilder("cmd", "/c", "C:\\xampp-copy\\apache_stop.bat").start();
            new ProcessBuilder("cmd", "/c", "C:\\xampp-copy\\mysql_stop.bat").start();
//        Process pbApache = new ProcessBuilder("cmd", "/c", "taskkill /F /IM httpd.exe").start();
//        Process pbMySQL = new ProcessBuilder("cmd", "/c", "taskkill /F /IM mysqld.exe").start();
            System.out.println("✅ Apache and MySQL stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Inventory System");
        stage.setMinHeight(450);
        stage.setMinWidth(618);
        stage.setScene(scene);
        stage.show();

//        startServices();
//        startAutoBackup();
    }

    @Override
    public void stop() {
//        stopServices();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
