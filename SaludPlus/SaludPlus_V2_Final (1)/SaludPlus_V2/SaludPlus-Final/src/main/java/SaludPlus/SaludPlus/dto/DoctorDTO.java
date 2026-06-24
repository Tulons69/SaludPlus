package SaludPlus.SaludPlus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private int id;
    private String rut;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String correo;
}
