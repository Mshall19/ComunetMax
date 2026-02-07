CREATE TABLE IF NOT EXISTS planes (
                                      id BIGINT PRIMARY KEY,
                                      activo BOOLEAN,
                                      descripcion VARCHAR(255),
    nombre VARCHAR(255),
    precio DOUBLE PRECISION,
    tecnologia VARCHAR(255),
    tipo VARCHAR(255),
    velocidad_mb INTEGER
    );