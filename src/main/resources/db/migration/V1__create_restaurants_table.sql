create schema if not exists restaurantfinder;

CREATE TABLE restaurant (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    opening_hours VARCHAR(50),
    image VARCHAR(1000),
    x BIGINT NOT NULL,
    y BIGINT NOT NULL,
    delivery_radius INTEGER NOT NULL
);
