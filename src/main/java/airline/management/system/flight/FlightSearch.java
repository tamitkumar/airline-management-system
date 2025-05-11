package airline.management.system.flight;

import airline.management.system.entity.FlightEntity;
import airline.management.system.entity.SeatEntity;
import airline.management.system.exception.AirlineException;
import airline.management.system.repository.FlightRepository;
import airline.management.system.repository.SeatRepository;
import airline.management.system.seat.SeatStatus;
import airline.management.system.seat.SeatType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class FlightSearch {
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    public FlightSearch(FlightRepository flightRepository, SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
    }

    public void addFlight(FlightEntity flight) {
        FlightEntity flightEntity = flightRepository.save(flight);
        seatRepository.saveAll(generateSeatsForFlight(flightEntity));
    }

    public List<FlightEntity> searchFlights(String source, String destination, LocalDate date) {
        return flightRepository.findAll().stream().filter(flight -> flight.getSource().equalsIgnoreCase(source)
                && flight.getDestination().equalsIgnoreCase(destination)
                && flight.getDepartureTime().toLocalDate().equals(date)).collect(Collectors.toList());
    }

    public FlightEntity getFlight(String flightNumber) {
        return flightRepository.findById(flightNumber).orElseThrow(() -> new AirlineException("ERR-502", "Flight not found with Flight Number: " + flightNumber));
    }

    public void saveSeat(SeatEntity seat) {
        seatRepository.save(seat);
    }

    private static List<SeatEntity> generateSeatsForFlight(FlightEntity flightNumber) {
        List<SeatEntity> seats = new ArrayList<>();

        for (int row = 1; row <= 9; row++) {
            seats.add(new SeatEntity(String.format("%02dB", row), SeatType.BUSINESS, SeatStatus.AVAILABLE, flightNumber));
            seats.add(new SeatEntity(String.format("%02dE", row), SeatType.ECONOMY, SeatStatus.AVAILABLE, flightNumber));
            seats.add(new SeatEntity(String.format("%02dPE", row), SeatType.PREMIUM_ECONOMY, SeatStatus.AVAILABLE, flightNumber));
        }

        for (int row = 10; row <= 16; row++) {
            seats.add(new SeatEntity(String.format("%02dE", row), SeatType.ECONOMY, SeatStatus.AVAILABLE, flightNumber));
            seats.add(new SeatEntity(String.format("%02dPE", row), SeatType.PREMIUM_ECONOMY, SeatStatus.AVAILABLE, flightNumber));
            seats.add(new SeatEntity(String.format("%02dFC", row), SeatType.FIRST_CLASS, SeatStatus.AVAILABLE, flightNumber));
        }

        for (int row = 17; row <= 33; row++) {
            seats.add(new SeatEntity(String.format("%02dE", row), SeatType.ECONOMY, SeatStatus.AVAILABLE, flightNumber));
            seats.add(new SeatEntity(String.format("%02dPE", row), SeatType.PREMIUM_ECONOMY, SeatStatus.AVAILABLE, flightNumber));
        }

        return seats;
    }
}
