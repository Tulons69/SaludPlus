CREATE TABLE IF NOT EXISTS usuario (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20),
    edad   INT
);

INSERT INTO usuario (id, nombre, edad) VALUES (3, 'Marco', 25);

SELECT * FROM usuario_backup;

CREATE TABLE IF NOT EXISTS usuario_backup (
    id        INT,
    nombre    VARCHAR(20),
    edad      INT,
    backup_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER despues_insert_usuario
AFTER INSERT ON usuario
FOR EACH ROW
BEGIN
    INSERT INTO usuario_backup (id, nombre, edad)
    VALUES (NEW.id, NEW.nombre, NEW.edad);
END//
DELIMITER ;
