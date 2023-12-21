CREATE TABLE IF NOT EXISTS songs
(
    id    SERIAL PRIMARY KEY,
    name VARCHAR ( 100 ) UNIQUE NOT NULL,
    artist VARCHAR ( 100 ) NOT NULL,
    album VARCHAR ( 100 ) NOT NULL,
    length VARCHAR ( 100 ) NOT NULL,
    resource_id SERIAL NOT NULL,
    year VARCHAR ( 4 ) NOT NULL
);