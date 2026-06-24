package SaludPlus.DoctorService.assembler;

import SaludPlus.DoctorService.controller.DoctorControllerV2;
import SaludPlus.DoctorService.model.Doctor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DoctorModelAssembler implements RepresentationModelAssembler<Doctor, EntityModel<Doctor>> {

    @Override
    public EntityModel<Doctor> toModel(Doctor doctor) {
        return EntityModel.of(doctor,
                linkTo(methodOn(DoctorControllerV2.class).buscarDoctorPorId(doctor.getId())).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores")
        );
    }
}
