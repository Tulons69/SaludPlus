package SaludPlus.SaludPlus.controller;

import SaludPlus.SaludPlus.model.Paciente;
import SaludPlus.SaludPlus.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pacientes")
public class PacienteWebController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPaciente(@ModelAttribute Paciente paciente, Model model) {
        Paciente registrado = pacienteService.registrarPaciente(paciente);
        model.addAttribute("pacienteRegistrado", registrado);
        model.addAttribute("paciente", new Paciente());
        return "registro";
    }
}
