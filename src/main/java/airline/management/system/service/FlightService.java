package airline.management.system.service;

import airline.management.system.booking.Booking;
import airline.management.system.entity.BookingEntity;
import airline.management.system.entity.FlightEntity;
import airline.management.system.entity.SeatEntity;
import airline.management.system.flight.Flight;
import airline.management.system.seat.Seat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    void addFlight(String source, String destination, LocalDateTime departure, LocalDateTime arrival, String aircraftNumber);
    List<Flight> searchFlight(String source, String destination, LocalDate date);
    Booking bookFlight(String flightNumber, String passengerId, Seat seat, double price);
}
