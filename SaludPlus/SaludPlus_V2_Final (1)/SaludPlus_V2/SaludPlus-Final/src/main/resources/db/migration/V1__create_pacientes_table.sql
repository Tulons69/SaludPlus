-- V1__create_pacientes_table.sql
-- Migracion inicial: creacion de la tabla pacientes

CREATE TABLE IF NOT EXISTS pacientes (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    rut      VARCHAR(12)  NOT NULL,
    nombre   VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo   VARCHAR(150),
    prevision VARCHAR(50)
);
