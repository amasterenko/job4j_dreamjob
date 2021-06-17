CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created TIMESTAMP
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    city_id INTEGER,
    FOREIGN KEY (city_id) REFERENCES
    city (id)
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT,
    UNIQUE (email)
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO city (name) VALUES ('Moscow'),
                               ('St.-Petersburg'),
                               ('Nizhniy Novgorod'),
                               ('Kazan'),
                               ('Samara');