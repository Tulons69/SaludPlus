-- V2__create_pacientes_backup_and_trigger.sql
-- Creacion de tabla backup y trigger automatico (segun lineamientos del equipo)

-- Tabla backup para el microservicio SaludPlus
CREATE TABLE IF NOT EXISTS pacientes_backup (
    id        INT,
    rut       VARCHAR(12),
    nombre    VARCHAR(100),
    apellido  VARCHAR(100),
    correo    VARCHAR(150),
    prevision VARCHAR(50),
    backup_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger: despues de cada INSERT en pacientes, guarda copia en pacientes_backup
CREATE TRIGGER despues_insert_pacientes
AFTER INSERT ON pacientes
FOR EACH ROW
BEGIN
    INSERT INTO pacientes_backup (id, rut, nombre, apellido, correo, prevision)
    VALUES (NEW.id, NEW.rut, NEW.nombre, NEW.apellido, NEW.correo, NEW.prevision);
END;
