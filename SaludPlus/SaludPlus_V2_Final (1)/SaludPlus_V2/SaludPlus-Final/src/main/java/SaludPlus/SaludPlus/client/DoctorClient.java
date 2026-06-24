package SaludPlus.SaludPlus.client;

import SaludPlus.SaludPlus.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "doctor-service", url = "http://localhost:8081")
public interface DoctorClient {

    @GetMapping("/api/v1/doctores")
    List<DoctorDTO> listarDoctores();

    @GetMapping("/api/v1/doctores/{id}")
    DoctorDTO buscarDoctorPorId(@PathVariable("id") int id);
}
