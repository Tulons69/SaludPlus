package SaludPlus.DoctorService.controller;

import SaludPlus.DoctorService.assembler.DoctorModelAssembler;
import SaludPlus.DoctorService.model.Doctor;
import SaludPlus.DoctorService.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/doctores")
@CrossOrigin(origins = "*")
@Tag(name = "Doctores V2", description = "Operaciones CON HATEOAS sobre doctores")
public class DoctorControllerV2 {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorModelAssembler assembler;

    @Operation(summary = "Listar todos los doctores con HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Doctor>> listarDoctores() {
        List<EntityModel<Doctor>> doctores = doctorService.listarDoctores()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(doctores,
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withSelfRel());
    }

    @Operation(summary = "Buscar doctor por ID con HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Doctor> buscarDoctorPorId(
            @Parameter(description = "ID del doctor", example = "1")
            @PathVariable int id) {
        return assembler.toModel(doctorService.buscarPorId(id));
    }

    @Operation(summary = "Registrar doctor con HATEOAS")
    @ApiResponse(responseCode = "200", description = "Doctor registrado exitosamente")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Doctor> registrarDoctor(@RequestBody Doctor doctor) {
        return assembler.toModel(doctorService.registrarDoctor(doctor));
    }

    @Operation(summary = "Eliminar doctor por ID")
    @ApiResponse(responseCode = "200", description = "Doctor eliminado exitosamente")
    @DeleteMapping("/{id}")
    public String eliminarDoctor(@PathVariable int id) {
        return doctorService.eliminarDoctor(id);
    }

    @Operation(summary = "Reporte 1 - Doctores por especialidad")
    @GetMapping(value = "/especialidad/{especialidad}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Doctor>> getDoctoresByEspecialidad(@PathVariable String especialidad) {
        List<EntityModel<Doctor>> lista = doctorService.listarDoctores().stream()
                .filter(d -> especialidad.equalsIgnoreCase(d.getEspecialidad()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(DoctorControllerV2.class).getDoctoresByEspecialidad(especialidad)).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores"));
    }

    @Operation(summary = "Reporte 2 - Total doctores por especialidad")
    @GetMapping(value = "/especialidad/{especialidad}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Long> getTotalByEspecialidad(@PathVariable String especialidad) {
        long total = doctorService.listarDoctores().stream()
                .filter(d -> especialidad.equalsIgnoreCase(d.getEspecialidad())).count();
        return EntityModel.of(total,
                linkTo(methodOn(DoctorControllerV2.class).getTotalByEspecialidad(especialidad)).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores"));
    }

    @Operation(summary = "Reporte 3 - Doctores por apellido")
    @GetMapping(value = "/apellido/{apellido}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Doctor>> getDoctoresByApellido(@PathVariable String apellido) {
        List<EntityModel<Doctor>> lista = doctorService.listarDoctores().stream()
                .filter(d -> apellido.equalsIgnoreCase(d.getApellido()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(DoctorControllerV2.class).getDoctoresByApellido(apellido)).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores"));
    }

    @Operation(summary = "Reporte 4 - Doctores por nombre")
    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Doctor>> getDoctoresByNombre(@PathVariable String nombre) {
        List<EntityModel<Doctor>> lista = doctorService.listarDoctores().stream()
                .filter(d -> nombre.equalsIgnoreCase(d.getNombre()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(DoctorControllerV2.class).getDoctoresByNombre(nombre)).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores"));
    }

    @Operation(summary = "Reporte 5 - Total de doctores registrados")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Long> getTotalDoctores() {
        long total = doctorService.listarDoctores().size();
        return EntityModel.of(total,
                linkTo(methodOn(DoctorControllerV2.class).getTotalDoctores()).withSelfRel(),
                linkTo(methodOn(DoctorControllerV2.class).listarDoctores()).withRel("doctores"));
    }
}
