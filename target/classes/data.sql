--INSERT INTO hotel (name, location, available_rooms, price_per_night) VALUES
--('Hotel Paradise', 'Delhi', 10, 2500.0),
--('Ocean Breeze Resort', 'Goa', 5, 4500.0),
--('Mountain View Inn', 'Manali', 8, 3200.0);

--INSERT INTO booking (hotel_id, user_id, check_in_date, check_out_date, status)
--VALUES
--(1, 1001, '2025-07-10', '2025-07-12', 'BOOKED'),
--(2, 1002, '2025-07-15', '2025-07-18', 'BOOKED');
INSERT INTO hotel (name, location, available_rooms, price_per_night)
SELECT 'Hotel Paradise', 'Delhi', 10, 2500.0
WHERE NOT EXISTS (
    SELECT 1 FROM hotel WHERE name = 'Hotel Paradise'
);

INSERT INTO hotel (name, location, available_rooms, price_per_night)
SELECT 'Ocean Breeze Resort', 'Goa', 5, 4500.0
WHERE NOT EXISTS (
    SELECT 1 FROM hotel WHERE name = 'Ocean Breeze Resort'
);

INSERT INTO hotel (name, location, available_rooms, price_per_night)
SELECT 'Mountain View Inn', 'Manali', 8, 3200.0
WHERE NOT EXISTS (
    SELECT 1 FROM hotel WHERE name = 'Mountain View Inn'
);