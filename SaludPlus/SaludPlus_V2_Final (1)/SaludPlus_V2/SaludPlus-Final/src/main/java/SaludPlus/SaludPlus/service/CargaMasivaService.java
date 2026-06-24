package SaludPlus.SaludPlus.service;

import SaludPlus.SaludPlus.dto.PacienteDTO;
import SaludPlus.SaludPlus.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargaMasivaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int procesarCarga(List<PacienteDTO> lista) {
        int batchSize = 50;

        for (int i = 0; i < lista.size(); i++) {
            PacienteDTO dto = lista.get(i);

            Paciente paciente = new Paciente();
            paciente.setRut(dto.getRut());
            paciente.setNombre(dto.getNombre());
            paciente.setApellido(dto.getApellido());
            paciente.setCorreo(dto.getCorreo());
            paciente.setPrevision(dto.getPrevision());

            entityManager.persist(paciente);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        return lista.size();
    }
}
