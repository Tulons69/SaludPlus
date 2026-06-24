package SaludPlus.SaludPlus.service;

import SaludPlus.SaludPlus.model.Paciente;
import SaludPlus.SaludPlus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut).orElse(null);
    }

    public String eliminarPaciente(int id) {
        pacienteRepository.deleteById(id);
        return "Paciente eliminado";
    }
}
