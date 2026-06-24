-- V1__create_doctores_table.sql
-- Migracion inicial: creacion de la tabla doctores

CREATE TABLE IF NOT EXISTS doctores (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    rut         VARCHAR(12)  NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    correo      VARCHAR(150)
);
