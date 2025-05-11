package airline.management.system.service;

import airline.management.system.entity.BookingEntity;
import airline.management.system.entity.FlightEntity;
import airline.management.system.entity.SeatEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    void addFlight(String source, String destination, LocalDateTime departure, LocalDateTime arrival, String aircraftNumber);
    List<FlightEntity> searchFlight(String source, String destination, LocalDate date);
    BookingEntity bookFlight(String flightNumber, String passengerId, SeatEntity seat, double price);
}
