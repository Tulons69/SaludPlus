-- V2__create_doctores_backup_and_trigger.sql
-- Tabla backup y trigger automatico para doctor-service

CREATE TABLE IF NOT EXISTS doctores_backup (
    id           INT,
    rut          VARCHAR(12),
    nombre       VARCHAR(100),
    apellido     VARCHAR(100),
    especialidad VARCHAR(100),
    correo       VARCHAR(150),
    backup_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger: cada INSERT en doctores replica automaticamente en doctores_backup
CREATE TRIGGER despues_insert_doctores
AFTER INSERT ON doctores
FOR EACH ROW
BEGIN
    INSERT INTO doctores_backup (id, rut, nombre, apellido, especialidad, correo)
    VALUES (NEW.id, NEW.rut, NEW.nombre, NEW.apellido, NEW.especialidad, NEW.correo);
END;
