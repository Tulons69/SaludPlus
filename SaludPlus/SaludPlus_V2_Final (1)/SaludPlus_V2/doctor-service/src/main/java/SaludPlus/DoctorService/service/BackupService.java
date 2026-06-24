package SaludPlus.DoctorService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Value("${backup.mysql.dump-path}")
    private String dumpPath;

    @Value("${backup.mysql.db-name}")
    private String dbName;

    @Value("${backup.mysql.db-user}")
    private String dbUser;

    @Value("${backup.mysql.db-pass}")
    private String dbPass;

    @Value("${backup.mysql.save-path}")
    private String savePath;

    public String createBackup() {
        String passwordFlag = dbPass.isEmpty() ? "" : "-p" + dbPass;
        String command = String.format(
            "%s -u %s %s --databases %s -r %s",
            dumpPath, dbUser, passwordFlag, dbName, savePath
        );
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                String msg = "Backup creado con exito en: " + savePath;
                System.out.println(msg);
                return msg;
            } else {
                String msg = "Fallo al crear el backup. Codigo: " + exitCode;
                System.err.println(msg);
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al ejecutar backup: " + e.getMessage();
        }
    }
}
