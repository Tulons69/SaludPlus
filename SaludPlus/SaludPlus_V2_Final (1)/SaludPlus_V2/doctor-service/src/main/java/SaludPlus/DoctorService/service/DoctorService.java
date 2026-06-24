package SaludPlus.DoctorService.service;

import SaludPlus.DoctorService.model.Doctor;
import SaludPlus.DoctorService.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor registrarDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> listarDoctores() {
        return doctorRepository.findAll();
    }

    public Doctor buscarPorId(int id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor buscarPorRut(String rut) {
        return doctorRepository.findByRut(rut).orElse(null);
    }

    public String eliminarDoctor(int id) {
        doctorRepository.deleteById(id);
        return "Doctor eliminado";
    }
}
