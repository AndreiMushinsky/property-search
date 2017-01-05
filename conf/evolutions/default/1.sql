# Properties schema

# --- !Ups

CREATE TABLE Property (
    id  SERIAL PRIMARY KEY,
    latitude double precision,
    longitude double precision,
    price double precision,
    price_currency varchar(10),
    keywords varchar(255)
);

# --- !Downs

DROP TABLE Property;