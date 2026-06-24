package SaludPlus.DoctorService.controller;

import SaludPlus.DoctorService.model.Doctor;
import SaludPlus.DoctorService.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctores")
@CrossOrigin(origins = "*")
@Tag(name = "Doctores", description = "Operaciones CRUD sobre doctores (sin HATEOAS)")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Registrar un doctor", description = "Crea un nuevo doctor en db_doctores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public Doctor registrarDoctor(@RequestBody Doctor doctor) {
        return doctorService.registrarDoctor(doctor);
    }

    @Operation(summary = "Listar todos los doctores", description = "Retorna la lista completa de doctores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<Doctor> listarDoctores() {
        return doctorService.listarDoctores();
    }

    @Operation(summary = "Buscar doctor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/{id}")
    public Doctor buscarDoctorPorId(
            @Parameter(description = "ID del doctor", example = "1")
            @PathVariable int id) {
        return doctorService.buscarPorId(id);
    }

    @Operation(summary = "Buscar doctor por RUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/rut/{rut}")
    public Doctor buscarDoctorPorRut(
            @Parameter(description = "RUT del doctor", example = "98765432-1")
            @PathVariable String rut) {
        return doctorService.buscarPorRut(rut);
    }

    @Operation(summary = "Eliminar doctor por ID")
    @ApiResponse(responseCode = "200", description = "Doctor eliminado exitosamente")
    @DeleteMapping("/{id}")
    public String eliminarDoctor(
            @Parameter(description = "ID del doctor", example = "1")
            @PathVariable int id) {
        return doctorService.eliminarDoctor(id);
    }
}
