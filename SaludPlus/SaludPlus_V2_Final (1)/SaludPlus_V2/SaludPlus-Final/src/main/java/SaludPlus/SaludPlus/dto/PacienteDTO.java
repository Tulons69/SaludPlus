package SaludPlus.SaludPlus.dto;

import lombok.Data;

@Data
public class PacienteDTO {
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String prevision;
}
