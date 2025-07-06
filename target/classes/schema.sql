CREATE TABLE hotel (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    available_rooms INT,
    price_per_night DOUBLE PRECISION
);

CREATE TABLE booking (
    id SERIAL PRIMARY KEY,
    hotel_id BIGINT,
    user_id BIGINT,
    check_in_date DATE,
    check_out_date DATE,
    status VARCHAR(20),
    CONSTRAINT fk_hotel FOREIGN KEY(hotel_id) REFERENCES hotel(id)
);