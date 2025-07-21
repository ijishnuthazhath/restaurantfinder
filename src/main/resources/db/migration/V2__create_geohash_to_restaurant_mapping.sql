CREATE TABLE restaurant_geohash (
    id BIGINT PRIMARY KEY,
    geohash VARCHAR(20) NOT NULL,
    restaurant_id UUID NOT NULL,
--    PRIMARY KEY (geohash, restaurant_id),
    CONSTRAINT fk_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id)
        ON DELETE CASCADE
);
