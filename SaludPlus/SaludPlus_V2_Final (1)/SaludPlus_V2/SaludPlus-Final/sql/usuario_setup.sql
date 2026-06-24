-- ============================================================
-- SCRIPT COMPLETO PARA MICROSERVICIO: usuario
-- Según lineamientos del documento de Migración de BD
-- ============================================================

/* CREACION DE TABLA ORIGINAL */
CREATE TABLE IF NOT EXISTS usuario (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20),
    edad   INT
);

/* INSERCION DE DATOS - Hacerlo con Postman método POST */
INSERT INTO usuario (id, nombre, edad) VALUES (3, 'Marco', 25);

/* CONSULTA DE DATOS - Hacerlo con Postman método GET */
SELECT * FROM usuario_backup;

/* CREAR TABLA BACKUP PARA EL MICROSERVICIO */
CREATE TABLE IF NOT EXISTS usuario_backup (
    id        INT,
    nombre    VARCHAR(20),
    edad      INT,
    backup_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* CREAR TRIGGER PARA EL MICROSERVICIO */
/* Con esto automáticamente se guardarán los datos en el backup */
DELIMITER //
CREATE TRIGGER despues_insert_usuario
AFTER INSERT ON usuario
FOR EACH ROW
BEGIN
    INSERT INTO usuario_backup (id, nombre, edad)
    VALUES (NEW.id, NEW.nombre, NEW.edad);
END//
DELIMITER ;
