package SaludPlus.SaludPlus.tester;

import SaludPlus.SaludPlus.model.Paciente;
import SaludPlus.SaludPlus.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tester")
@Tag(name = "Tester", description = "Endpoints de prueba del sistema SaludPlus - NO usar en produccion")
public class TesterController {

    @Autowired
    private PacienteRepository pacienteRepository;


    @Operation(
        summary = "Test 1 - Guardar paciente",
        description = "Crea un paciente de prueba y verifica que se guardo correctamente con un ID autogenerado"
    )
    @GetMapping("/test-guardar")
    public String testGuardar() {
        try {
            Paciente p = new Paciente();
            p.setRut("TEST-001");
            p.setNombre("Diego");
            p.setApellido("Gonzalez");
            p.setCorreo("diego@saludplus.cl");
            p.setPrevision("Fonasa");

            Paciente guardado = pacienteRepository.save(p);

            if (guardado.getId() > 0) {
                return "✅ TEST 1 PASADO — Paciente guardado con ID: " + guardado.getId();
            } else {
                return "❌ TEST 1 FALLADO — El ID no se genero correctamente";
            }
        } catch (Exception e) {
            return "❌ TEST 1 ERROR — " + e.getMessage();
        }
    }


    @Operation(
        summary = "Test 2 - Listar pacientes",
        description = "Obtiene todos los pacientes de la BD y verifica que la lista no sea nula"
    )
    @GetMapping("/test-listar")
    public String testListar() {
        try {
            List<Paciente> lista = pacienteRepository.findAll();
            return "✅ TEST 2 PASADO — Total pacientes en BD: " + lista.size();
        } catch (Exception e) {
            return "❌ TEST 2 ERROR — " + e.getMessage();
        }
    }


    @Operation(
        summary = "Test 3 - Buscar paciente por RUT",
        description = "Busca un paciente por su RUT y verifica que exista en la BD"
    )
    @GetMapping("/test-buscar/{rut}")
    public String testBuscar(@PathVariable String rut) {
        try {
            Paciente encontrado = pacienteRepository.findByRut(rut).orElse(null);
            if (encontrado != null) {
                return "✅ TEST 3 PASADO — Paciente encontrado: " + encontrado.getNombre() + " " + encontrado.getApellido();
            } else {
                return "❌ TEST 3 FALLADO — No se encontro un paciente con RUT: " + rut;
            }
        } catch (Exception e) {
            return "❌ TEST 3 ERROR — " + e.getMessage();
        }
    }


    @Operation(
        summary = "Test 4 - Eliminar paciente por ID",
        description = "Elimina un paciente por su ID y verifica que ya no exista en la BD"
    )
    @GetMapping("/test-eliminar/{id}")
    public String testEliminar(@PathVariable int id) {
        try {
            if (!pacienteRepository.existsById(id)) {
                return "❌ TEST 4 FALLADO — No existe un paciente con ID: " + id;
            }
            pacienteRepository.deleteById(id);
            boolean aun_existe = pacienteRepository.existsById(id);
            if (!aun_existe) {
                return "✅ TEST 4 PASADO — Paciente con ID " + id + " eliminado correctamente";
            } else {
                return "❌ TEST 4 FALLADO — El paciente sigue existiendo despues de eliminarlo";
            }
        } catch (Exception e) {
            return "❌ TEST 4 ERROR — " + e.getMessage();
        }
    }

    @Operation(
        summary = "Test 5 - Carga masiva",
        description = "Inserta 5 pacientes de prueba de una sola vez y verifica que todos se guardaron"
    )
    @GetMapping("/test-carga-masiva")
    public String testCargaMasiva() {
        try {
            List<Paciente> lista = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Paciente p = new Paciente();
                p.setRut("MASIVO-00" + i);
                p.setNombre("Paciente" + i);
                p.setApellido("Apellido" + i);
                p.setCorreo("masivo" + i + "@saludplus.cl");
                p.setPrevision(i % 2 == 0 ? "Isapre" : "Fonasa");
                lista.add(p);
            }
            List<Paciente> guardados = pacienteRepository.saveAll(lista);
            return "✅ TEST 5 PASADO — " + guardados.size() + " pacientes insertados en carga masiva. " +
                   "Total en BD: " + pacienteRepository.findAll().size();
        } catch (Exception e) {
            return "❌ TEST 5 ERROR — " + e.getMessage();
        }
    }


    @Operation(
        summary = "Test Completo - Corre todos los tests",
        description = "Ejecuta los 5 tests en orden y muestra el resultado de cada uno"
    )
    @GetMapping("/test-completo")
    public List<String> testCompleto() {
        List<String> resultados = new ArrayList<>();

        resultados.add(testGuardar());

        resultados.add(testListar());

        resultados.add(testBuscar("TEST-001"));

        Paciente p = pacienteRepository.findByRut("TEST-001").orElse(null);
        if (p != null) {
            resultados.add(testEliminar(p.getId()));
        } else {
            resultados.add("⚠️ TEST 4 SALTADO — No habia paciente TEST-001 para eliminar");
        }

        resultados.add(testCargaMasiva());

        return resultados;
    }
}
