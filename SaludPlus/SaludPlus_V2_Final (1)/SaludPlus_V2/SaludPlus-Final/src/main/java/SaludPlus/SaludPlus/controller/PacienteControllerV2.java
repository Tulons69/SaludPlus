package SaludPlus.SaludPlus.controller;

import SaludPlus.SaludPlus.assembler.PacienteModelAssembler;
import SaludPlus.SaludPlus.model.Paciente;
import SaludPlus.SaludPlus.service.PacienteService;
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
@RequestMapping("/api/v2/pacientes")
@CrossOrigin(origins = "*")
@Tag(name = "Pacientes V2", description = "Operaciones CON HATEOAS sobre pacientes")
public class PacienteControllerV2 {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteModelAssembler assembler;

    @Operation(summary = "Listar todos los pacientes con HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> listarPacientes() {
        List<EntityModel<Paciente>> pacientes = pacienteService.listarPacientes()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(pacientes,
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withSelfRel());
    }

    @Operation(summary = "Buscar paciente por RUT con HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping(value = "/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Paciente> buscarPaciente(
            @Parameter(description = "RUT del paciente", example = "12345678-9")
            @PathVariable String rut) {
        return assembler.toModel(pacienteService.buscarPorRut(rut));
    }

    @Operation(summary = "Registrar paciente con HATEOAS")
    @ApiResponse(responseCode = "200", description = "Paciente registrado exitosamente")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        return assembler.toModel(pacienteService.registrarPaciente(paciente));
    }

    @Operation(summary = "Eliminar paciente por ID")
    @ApiResponse(responseCode = "200", description = "Paciente eliminado exitosamente")
    @DeleteMapping("/{id}")
    public String eliminarPaciente(@PathVariable int id) {
        return pacienteService.eliminarPaciente(id);
    }

    @Operation(summary = "Reporte 1 - Pacientes por prevision")
    @GetMapping(value = "/prevision/{prevision}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> getPacientesByPrevision(@PathVariable String prevision) {
        List<EntityModel<Paciente>> lista = pacienteService.listarPacientes().stream()
                .filter(p -> prevision.equalsIgnoreCase(p.getPrevision()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PacienteControllerV2.class).getPacientesByPrevision(prevision)).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes"));
    }

    @Operation(summary = "Reporte 2 - Total pacientes por prevision")
    @GetMapping(value = "/prevision/{prevision}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Long> getTotalByPrevision(@PathVariable String prevision) {
        long total = pacienteService.listarPacientes().stream()
                .filter(p -> prevision.equalsIgnoreCase(p.getPrevision())).count();
        return EntityModel.of(total,
                linkTo(methodOn(PacienteControllerV2.class).getTotalByPrevision(prevision)).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes"));
    }

    @Operation(summary = "Reporte 3 - Pacientes por apellido")
    @GetMapping(value = "/apellido/{apellido}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> getPacientesByApellido(@PathVariable String apellido) {
        List<EntityModel<Paciente>> lista = pacienteService.listarPacientes().stream()
                .filter(p -> apellido.equalsIgnoreCase(p.getApellido()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PacienteControllerV2.class).getPacientesByApellido(apellido)).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes"));
    }

    @Operation(summary = "Reporte 4 - Pacientes por nombre")
    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> getPacientesByNombre(@PathVariable String nombre) {
        List<EntityModel<Paciente>> lista = pacienteService.listarPacientes().stream()
                .filter(p -> nombre.equalsIgnoreCase(p.getNombre()))
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PacienteControllerV2.class).getPacientesByNombre(nombre)).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes"));
    }

    @Operation(summary = "Reporte 5 - Total de pacientes registrados")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Long> getTotalPacientes() {
        long total = pacienteService.listarPacientes().size();
        return EntityModel.of(total,
                linkTo(methodOn(PacienteControllerV2.class).getTotalPacientes()).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).listarPacientes()).withRel("pacientes"));
    }
}
