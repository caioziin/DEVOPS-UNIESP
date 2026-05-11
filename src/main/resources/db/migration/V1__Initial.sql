CREATE TABLE IF NOT EXISTS users (
    id         BIGSERIAL    PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS products (
    id         BIGSERIAL      PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    price      NUMERIC(10,2)  NOT NULL CHECK (price > 0),
    created_at TIMESTAMP      NOT NULL DEFAULT NOW()
);