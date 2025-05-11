CREATE TABLE airline.passenger (
                                   id VARCHAR(255) PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   email VARCHAR(255) NOT NULL
);

CREATE TABLE airline.aircraft (
                                  tailNumber VARCHAR(255) PRIMARY KEY,
                                  model VARCHAR(255) NOT NULL,
                                  total_seat int
);

CREATE TABLE airline.flight (
                                flight_number VARCHAR(255) NOT NULL PRIMARY KEY,
                                source VARCHAR(255),
                                destination VARCHAR(255),
                                departure_time TIMESTAMP,
                                arrival_time TIMESTAMP,
                                status VARCHAR(255) NOT NULL,
                                tail_number VARCHAR(255),
                                CONSTRAINT fk_flight_aircraft FOREIGN KEY (tail_number)
                                    REFERENCES airline.aircraft (tail_number)
                                    ON DELETE SET NULL
                                    ON UPDATE CASCADE
);

CREATE TABLE airline.seat (
                              seat_number VARCHAR(255) NOT NULL PRIMARY KEY,
                              type VARCHAR(255) NOT NULL,
                              status VARCHAR(255) NOT NULL,
                              flight_number VARCHAR(255) NOT NULL,
                              CONSTRAINT fk_seat_flight FOREIGN KEY (flight_number)
                                  REFERENCES airline.flight (flight_number)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE
);

CREATE TABLE airline.booking (
                                 booking_id VARCHAR(255) PRIMARY KEY,
                                 flight_number VARCHAR(255) NOT NULL,
                                 passenger_id VARCHAR(255) NOT NULL,
                                 seat_number VARCHAR(255) NOT NULL,
                                 price DOUBLE PRECISION NOT NULL,
                                 status VARCHAR(50) NOT NULL,

                                 CONSTRAINT fk_booking_flight
                                     FOREIGN KEY (flight_number)
                                         REFERENCES airline.flight(flight_number)
                                         ON DELETE RESTRICT,

                                 CONSTRAINT fk_booking_passenger
                                     FOREIGN KEY (passenger_id)
                                         REFERENCES airline.passenger(id)
                                         ON DELETE RESTRICT,

                                 CONSTRAINT fk_booking_seat
                                     FOREIGN KEY (seat_number)
                                         REFERENCES airline.seat(seat_number)
                                         ON DELETE RESTRICT
);