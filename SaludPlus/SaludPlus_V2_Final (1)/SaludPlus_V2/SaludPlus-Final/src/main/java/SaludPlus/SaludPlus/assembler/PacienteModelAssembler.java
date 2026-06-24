package SaludPlus.SaludPlus.assembler;

import SaludPlus.SaludPlus.controller.PacienteControllerV2;
import SaludPlus.SaludPlus.model.Paciente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PacienteModelAssembler implements RepresentationModelAssembler<Paciente, EntityModel<Paciente>> {

    @Override
    public EntityModel<Paciente> toModel(Paciente paciente) {
        return EntityModel.of(paciente,
                linkTo(methodOn(PacienteControllerV2.class).buscarPaciente(paciente.getRut())).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes")
        );
    }
}
