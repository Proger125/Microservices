CREATE TABLE IF NOT EXISTS resources
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR ( 100 ) UNIQUE NOT NULL,
    resource BYTEA NOT NULL
);