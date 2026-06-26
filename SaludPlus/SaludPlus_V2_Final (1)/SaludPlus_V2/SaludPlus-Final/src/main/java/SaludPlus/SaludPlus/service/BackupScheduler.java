package SaludPlus.SaludPlus.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BackupScheduler {

    private final BackupService backupService;

    public BackupScheduler(BackupService backupService) {
        this.backupService = backupService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        System.out.println("[BackupScheduler] Ejecutando backup automatico programado...");
        backupService.createBackup();
    }
}
