-- Schema and sample data for saberpro_db
CREATE DATABASE IF NOT EXISTS saberpro_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE saberpro_db;

CREATE TABLE alumno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(200) NOT NULL,
    correo VARCHAR(150),
    programa VARCHAR(150),
    semestre INT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NULL
);

CREATE TABLE examen_resultado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alumno_id BIGINT NOT NULL,
    tipo_examen VARCHAR(10) NOT NULL,
    fecha DATE NOT NULL,
    puntaje INT NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (alumno_id) REFERENCES alumno(id) ON DELETE CASCADE
);

CREATE TABLE incentivo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    examen_resultado_id BIGINT NOT NULL,
    tipo_incentivo VARCHAR(200),
    descripcion TEXT,
    porcentaje_beca INT DEFAULT 0,
    exencion_informe BOOLEAN DEFAULT FALSE,
    nota_exigida DECIMAL(3,1),
    vigencia_inicio DATE,
    vigencia_fin DATE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (examen_resultado_id) REFERENCES examen_resultado(id) ON DELETE CASCADE
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

INSERT INTO alumno (identificacion,nombre,correo,programa,semestre) VALUES
('1020304050','Ana Perez','ana.perez@ejemplo.com','Ingenieria de Sistemas',10),
('1098765432','Juan Gomez','juan.gomez@ejemplo.com','Ingenieria de Sistemas',10),
('1010101010','Laura Ruiz','laura.ruiz@ejemplo.com','Ingenieria Industrial',10);
