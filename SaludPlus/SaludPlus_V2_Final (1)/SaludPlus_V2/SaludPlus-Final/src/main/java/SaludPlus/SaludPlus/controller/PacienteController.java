package SaludPlus.SaludPlus.controller;

import SaludPlus.SaludPlus.client.DoctorClient;
import SaludPlus.SaludPlus.dto.DoctorDTO;
import SaludPlus.SaludPlus.model.Paciente;
import SaludPlus.SaludPlus.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin(origins = "*")  
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DoctorClient doctorClient;


    @PostMapping
    public Paciente registrarPaciente(@RequestBody Paciente paciente) {
        return pacienteService.registrarPaciente(paciente);
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    @GetMapping("/{rut}")
    public Paciente buscarPaciente(@PathVariable String rut) {
        return pacienteService.buscarPorRut(rut);
    }

    @DeleteMapping("/{id}")
    public String eliminarPaciente(@PathVariable int id) {
        return pacienteService.eliminarPaciente(id);
    }

    @GetMapping("/doctores")
    public List<DoctorDTO> consultarDoctores() {

        return doctorClient.listarDoctores();
    }

    @GetMapping("/doctores/{id}")
    public DoctorDTO consultarDoctorPorId(@PathVariable int id) {
        return doctorClient.buscarDoctorPorId(id);
    }
}
