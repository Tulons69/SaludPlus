package SaludPlus.SaludPlus.controller;

import SaludPlus.SaludPlus.dto.PacienteDTO;
import SaludPlus.SaludPlus.service.CargaMasivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class CargaController {

    @Autowired
    private CargaMasivaService service;

    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<PacienteDTO> pacientes) {
        try {
            if (pacientes == null || pacientes.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista está vacía");
            }

            long inicio = System.currentTimeMillis();
            int total = service.procesarCarga(pacientes);
            long fin = System.currentTimeMillis();

            return ResponseEntity.ok("Éxito: " + total + " pacientes cargados en " + (fin - inicio) + "ms");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga: " + e.getMessage());
        }
    }
}
