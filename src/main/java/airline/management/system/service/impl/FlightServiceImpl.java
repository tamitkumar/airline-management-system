package airline.management.system.service.impl;

import airline.management.system.booking.BookingManager;
import airline.management.system.entity.*;
import airline.management.system.exception.AirlineException;
import airline.management.system.flight.FlightSearch;
import airline.management.system.flight.FlightStatus;
import airline.management.system.repository.AircraftRepository;
import airline.management.system.repository.PassengerRepository;
import airline.management.system.repository.SeatRepository;
import airline.management.system.seat.SeatStatus;
import airline.management.system.seat.SeatType;
import airline.management.system.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {
    private final AircraftRepository aircraftRepository;
    private final PassengerRepository passengerRepository;
    private final FlightSearch flightSearch;
    private final BookingManager bookingManager;

    public FlightServiceImpl(AircraftRepository aircraftRepository, PassengerRepository passengerRepository, FlightSearch flightSearch, BookingManager bookingManager) {
        this.aircraftRepository = aircraftRepository;
        this.passengerRepository = passengerRepository;
        this.flightSearch = flightSearch;
        this.bookingManager = bookingManager;
    }

    @Override
    public void addFlight(String source, String destination, LocalDateTime departure, LocalDateTime arrival, String aircraftNumber) {
        AircraftEntity aircraftEntity = aircraftRepository.findById(aircraftNumber).orElseThrow(() -> new AirlineException("500", "Aircraft not found with id: " + aircraftNumber));
        FlightEntity flightEntity = FlightEntity.builder().flightNumber(UUID.randomUUID().toString()).source(source).destination(destination)
                .departureTime(departure).arrivalTime(arrival).status(FlightStatus.ON_TIME).aircraft(aircraftEntity).build();
        flightSearch.addFlight(flightEntity);
    }

    @Override
    public List<FlightEntity> searchFlight(String source, String destination, LocalDate date) {
        return flightSearch.searchFlights(source, destination, date);
    }

    @Override
    public BookingEntity bookFlight(String flightNumber, String passengerId, SeatEntity seat, double price) {
        FlightEntity flight = flightSearch.getFlight(flightNumber);
        PassengerEntity passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new AirlineException("ERR-502", "Passenger not found with Passenger Id: " + passengerId));
        return bookingManager.createBooking(flight, passenger, seat, price);
    }
}
