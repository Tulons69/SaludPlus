package SaludPlus.SaludPlus.controller;

import SaludPlus.SaludPlus.service.BackupService;
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
        return "Historial de Flyway reparado. Ya puedes reintentar la migracion.";
    }

    @PostMapping("/backup")
    public String backupManual() {
        return backupService.createBackup();
    }

    @GetMapping("/info")
    public String migrationsInfo() {
        var infos = flyway.info().all();
        StringBuilder sb = new StringBuilder("=== Estado de Migraciones Flyway ===\n");
        for (var info : infos) {
            sb.append(String.format("Version: %-6s | Estado: %-10s | Descripcion: %s%n",
                info.getVersion(),
                info.getState().getDisplayName(),
                info.getDescription()));
        }
        return sb.toString();
    }
}
