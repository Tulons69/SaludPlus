package SaludPlus.DoctorService.controller;

import SaludPlus.DoctorService.service.BackupService;
import org.flywaydb.core.Flyway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/db")
public class DatabaseAdminController {

    private final Flyway flyway;
    private final BackupService backupService;

    public DatabaseAdminController(Flyway flyway, BackupService backupService) {
        this.flyway = flyway;
        this.backupService = backupService;
    }

    @PostMapping("/repair")
    public String repairDatabase() {
        flyway.repair();
        return "Historial de Flyway reparado en doctor-service.";
    }

    @PostMapping("/backup")
    public String backupManual() {
        return backupService.createBackup();
    }

    @GetMapping("/info")
    public String migrationsInfo() {
        var infos = flyway.info().all();
        StringBuilder sb = new StringBuilder("=== Migraciones doctor-service ===\n");
        for (var info : infos) {
            sb.append(String.format("Version: %-6s | Estado: %-10s | Descripcion: %s%n",
                info.getVersion(),
                info.getState().getDisplayName(),
                info.getDescription()));
        }
        return sb.toString();
    }
}
