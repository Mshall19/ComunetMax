CREATE TABLE IF NOT EXISTS usuarios (
                                        id BIGINT PRIMARY KEY,
                                        activo BOOLEAN,
                                        apellido VARCHAR(255),
    email VARCHAR(255),
    nombre VARCHAR(255),
    password VARCHAR(255),
    plan_id BIGINT,
    rol VARCHAR(255)
    );