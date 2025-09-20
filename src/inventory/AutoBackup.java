package inventory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AutoBackup {

    private static final String DB_NAME = "cstoredb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String MYSQL_PATH = "C:\\xampp-copy\\mysql\\bin\\mysqldump.exe";

    private static final String BACKUP_DIR = "D:\\Backups";
    private static final int BACKUP_INTERVAL_MINUTES = 5;
    private static final int MAX_BACKUPS = 5;

    public static void main(String[] args) {
        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                createBackup();
            }
        }, 0, BACKUP_INTERVAL_MINUTES * 60 * 1000);

        System.out.println("✅ Backup scheduler started. Press Ctrl+C to stop.");

        // Keep the application running
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createBackup() {
        try {
            File backupDir = new File(BACKUP_DIR);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupFile = BACKUP_DIR + File.separator + "backup_" + timestamp + ".sql";

            ProcessBuilder pb;

            if (DB_PASSWORD.isEmpty()) {
                pb = new ProcessBuilder(
                        MYSQL_PATH,
                        "-u" + DB_USER,
                        DB_NAME
                );
            } else {
                pb = new ProcessBuilder(
                        MYSQL_PATH,
                        "-u" + DB_USER,
                        "-p" + DB_PASSWORD,
                        DB_NAME
                );
            }
            pb.redirectOutput(new File(backupFile));
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✔ Backup created: " + backupFile);
                deleteOldBackups();
            } else {
                System.err.println("❌ Failed to create backup. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteOldBackups() {
        File dir = new File(BACKUP_DIR);
        File[] backups = dir.listFiles((d, name) -> name.endsWith(".sql"));

        if (backups != null && backups.length > MAX_BACKUPS) {
            Arrays.sort(backups, Comparator.comparingLong(File::lastModified).reversed());

            for (int i = MAX_BACKUPS; i < backups.length; i++) {
                if (backups[i].delete()) {
                    System.out.println("🗑 Deleted old backup: " + backups[i].getName());
                }
            }
        }
    }
}
