package airline.management.system.service.impl;

import airline.management.system.booking.Booking;
import airline.management.system.booking.BookingManager;
import airline.management.system.entity.*;
import airline.management.system.exception.AirlineException;
import airline.management.system.flight.Flight;
import airline.management.system.flight.FlightSearch;
import airline.management.system.flight.FlightStatus;
import airline.management.system.repository.AircraftRepository;
import airline.management.system.repository.PassengerRepository;
import airline.management.system.repository.SeatRepository;
import airline.management.system.seat.Seat;
import airline.management.system.seat.SeatStatus;
import airline.management.system.seat.SeatType;
import airline.management.system.service.FlightService;
import airline.management.system.utils.Aircraft;
import airline.management.system.utils.Passenger;
import org.springframework.beans.BeanUtils;
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
    public List<Flight> searchFlight(String source, String destination, LocalDate date) {
        List<FlightEntity> flightEntity = flightSearch.searchFlights(source, destination, date);
        List<Flight> flights = new ArrayList<>();
        for (FlightEntity entity : flightEntity) {
            List<Seat> seats = new ArrayList<>();
            Flight flight = Flight.builder().build();
            Aircraft aircraft = Aircraft.builder().build();
            for (SeatEntity s : entity.getSeats()) {
                Seat seat = Seat.builder().build();
                BeanUtils.copyProperties(s, seat);
                seats.add(seat);
            }
            BeanUtils.copyProperties(entity.getAircraft(), aircraft);
            BeanUtils.copyProperties(entity, flight);
            flight.setAircraft(aircraft);
            flight.setSeats(seats);
            flights.add(flight);
        }
        return flights;
    }

    @Override
    public Booking bookFlight(String flightNumber, String passengerId, Seat seat, double price) {
        FlightEntity flight = flightSearch.getFlight(flightNumber);
        List<SeatEntity> seats = flight.getSeats();

        Optional<SeatEntity> requestedSeat = seats.stream()
                .filter(s -> s.getSeatNumber().equalsIgnoreCase(seat.getSeatNumber()))
                .findFirst();

        if (requestedSeat.isPresent()) {
            if (requestedSeat.get().getStatus() == SeatStatus.RESERVED) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved.");
            } else {
                flightSearch.saveSeat(requestedSeat.get());
                PassengerEntity passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new AirlineException("ERR-502", "Passenger not found with Passenger Id: " + passengerId));
                BookingEntity bookingEntity = bookingManager.createBooking(flight, passenger, requestedSeat.get(), price);
                Flight flt = Flight.builder().build();
                Aircraft air = Aircraft.builder().build();
                BeanUtils.copyProperties(bookingEntity.getFlight(), flt);
                BeanUtils.copyProperties(bookingEntity.getFlight().getAircraft(), air);
                flt.setAircraft(air);
                Passenger psngr = Passenger.builder().build();
                BeanUtils.copyProperties(bookingEntity.getPassenger(), psngr);
                Seat st = Seat.builder().build();
                BeanUtils.copyProperties(bookingEntity.getSeat(), st);
                Booking book = Booking.builder().build();
                BeanUtils.copyProperties(bookingEntity, book);
                book.setFlight(flt);
                book.setPassenger(psngr);
                book.setSeat(st);
                return book;
            }
        } else {
            throw new RuntimeException("Seat " + seat.getSeatNumber() + " not found.");
        }
    }
}
