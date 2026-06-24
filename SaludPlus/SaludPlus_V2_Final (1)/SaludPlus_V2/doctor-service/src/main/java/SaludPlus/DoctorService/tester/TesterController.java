package SaludPlus.DoctorService.tester;

import SaludPlus.DoctorService.model.Doctor;
import SaludPlus.DoctorService.repository.DoctorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tester")
@Tag(name = "Tester", description = "Endpoints de prueba del microservicio doctor-service - NO usar en produccion")
public class TesterController {

    @Autowired
    private DoctorRepository doctorRepository;


    @Operation(summary = "Test 1 - Guardar doctor", description = "Crea un doctor de prueba y verifica el ID generado")
    @GetMapping("/test-guardar")
    public String testGuardar() {
        try {
            Doctor d = new Doctor();
            d.setRut("TEST-001");
            d.setNombre("Juan");
            d.setApellido("Perez");
            d.setEspecialidad("Cardiologia");
            d.setCorreo("juan@saludplus.cl");

            Doctor guardado = doctorRepository.save(d);

            if (guardado.getId() > 0) {
                return "✅ Test 1 Correcto - Doctor guardado con ID: " + guardado.getId();
            } else {
                return "❌ Test 1 Fallado — El ID no se genero correctamente";
            }
        } catch (Exception e) {
            return "❌ TEST 1 ERROR — " + e.getMessage();
        }
    }


    @Operation(summary = "Test 2 - Listar doctores", description = "Obtiene todos los doctores y verifica que la lista no sea nula")
    @GetMapping("/test-listar")
    public String testListar() {
        try {
            List<Doctor> lista = doctorRepository.findAll();
            return "✅ Test 2 Correcto — Total doctores en BD: " + lista.size();
        } catch (Exception e) {
            return "❌ Test 2 Fallado — " + e.getMessage();
        }
    }


    @Operation(summary = "Test 3 - Buscar doctor por RUT", description = "Busca un doctor por su RUT y verifica que exista")
    @GetMapping("/test-buscar/{rut}")
    public String testBuscar(@PathVariable String rut) {
        try {
            Doctor encontrado = doctorRepository.findByRut(rut).orElse(null);
            if (encontrado != null) {
                return "✅ Test 3 Correcto — Doctor encontrado: " + encontrado.getNombre() + " " + encontrado.getApellido();
            } else {
                return "❌ Test 3 Fallado — No se encontro un doctor con RUT: " + rut;
            }
        } catch (Exception e) {
            return "❌ TEST 3 ERROR — " + e.getMessage();
        }
    }


    @Operation(summary = "Test 4 - Eliminar doctor por ID", description = "Elimina un doctor por ID y verifica que ya no exista")
    @GetMapping("/test-eliminar/{id}")
    public String testEliminar(@PathVariable int id) {
        try {
            if (!doctorRepository.existsById(id)) {
                return "❌ TEST 4 FALLADO — No existe un doctor con ID: " + id;
            }
            doctorRepository.deleteById(id);
            boolean aun_existe = doctorRepository.existsById(id);
            if (!aun_existe) {
                return "✅ Test 4 Correcto — Doctor con ID " + id + " eliminado correctamente";
            } else {
                return "❌ Test 4 Fallado — El doctor sigue existiendo despues de eliminarlo";
            }
        } catch (Exception e) {
            return "❌ TEST 4 ERROR — " + e.getMessage();
        }
    }


    @Operation(summary = "Test 5 - Carga masiva", description = "Inserta 5 doctores de prueba de una sola vez")
    @GetMapping("/test-carga-masiva")
    public String testCargaMasiva() {
        try {
            List<Doctor> lista = new ArrayList<>();
            String[] especialidades = {"Cardiologia", "Pediatria", "Traumatologia", "Dermatologia", "Neurologia"};
            for (int i = 1; i <= 5; i++) {
                Doctor d = new Doctor();
                d.setRut("MASIVO-00" + i);
                d.setNombre("Doctor" + i);
                d.setApellido("Apellido" + i);
                d.setEspecialidad(especialidades[i - 1]);
                d.setCorreo("masivo" + i + "@saludplus.cl");
                lista.add(d);
            }
            List<Doctor> guardados = doctorRepository.saveAll(lista);
            return "✅ Test 5 Correcto — " + guardados.size() + " doctores insertados en carga masiva. " +
                   "Total en BD: " + doctorRepository.findAll().size();
        } catch (Exception e) {
            return "❌ Test 5 Fallado — " + e.getMessage();
        }
    }


    @Operation(summary = "Test Completo - Corre todos los tests", description = "Ejecuta los 5 tests en orden y muestra el resultado de cada uno")
    @GetMapping("/test-completo")
    public List<String> testCompleto() {
        List<String> resultados = new ArrayList<>();

        resultados.add(testGuardar());
        resultados.add(testListar());
        resultados.add(testBuscar("TEST-001"));

        Doctor d = doctorRepository.findByRut("TEST-001").orElse(null);
        if (d != null) {
            resultados.add(testEliminar(d.getId()));
        } else {
            resultados.add("⚠️ Test 4 omitido — No habia doctor TEST-001 para eliminar");
        }

        resultados.add(testCargaMasiva());

        return resultados;
    }
}
