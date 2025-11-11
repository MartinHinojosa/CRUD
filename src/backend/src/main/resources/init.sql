-- Script de inicialización de la base de datos
-- Este script crea las tablas y un usuario de prueba

-- Crear la base de datos (ejecutar manualmente en pgAdmin si no existe)
-- CREATE DATABASE taller_db;

-- Las tablas se crearán automáticamente por JPA con ddl-auto=update
-- Pero aquí están las definiciones SQL por referencia:

-- 1. Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'empleado',
    activo BOOLEAN DEFAULT TRUE
);

-- 2. Tabla de vehículos
CREATE TABLE IF NOT EXISTS vehiculos (
    id_vehiculo SERIAL PRIMARY KEY,
    placa VARCHAR(20) UNIQUE NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    año INT,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE SET NULL
);

-- 3. Tabla de mantenimientos
CREATE TABLE IF NOT EXISTS mantenimientos (
    id_mantenimiento SERIAL PRIMARY KEY,
    id_vehiculo INT REFERENCES vehiculos(id_vehiculo) ON DELETE CASCADE,
    tipo_servicio VARCHAR(100) NOT NULL,
    fecha_servicio DATE NOT NULL,
    costo NUMERIC(10,2),
    notas TEXT,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE SET NULL
);

-- Insertar usuario de prueba
-- La contraseña es "password123" hasheada con BCrypt
-- Para generar un nuevo hash, puedes usar el siguiente código Java:
-- BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
-- String hash = encoder.encode("password123");
INSERT INTO usuarios (nombre, correo, password_hash, rol, activo) 
VALUES (
    'Usuario Admin', 
    'admin@taller.com', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
    'admin', 
    TRUE
)
ON CONFLICT (correo) DO NOTHING;

INSERT INTO usuarios (nombre, correo, password_hash, rol, activo) 
VALUES (
    'Empleado Test', 
    'empleado@taller.com', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
    'empleado', 
    TRUE
)
ON CONFLICT (correo) DO NOTHING;

