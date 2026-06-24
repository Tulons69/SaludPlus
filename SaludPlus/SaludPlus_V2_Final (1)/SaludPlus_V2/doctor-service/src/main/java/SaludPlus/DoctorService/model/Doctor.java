package SaludPlus.DoctorService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctores")
@Schema(description = "Entidad que representa un doctor en SaludPlus")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico autogenerado del doctor", example = "1")
    private int id;

    @Schema(description = "RUT del doctor", example = "98765432-1")
    private String rut;

    @Schema(description = "Nombre del doctor", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del doctor", example = "Perez")
    private String apellido;

    @Schema(description = "Especialidad medica del doctor", example = "Cardiologia")
    private String especialidad;

    @Schema(description = "Correo electronico del doctor", example = "juan@saludplus.cl")
    private String correo;
}
