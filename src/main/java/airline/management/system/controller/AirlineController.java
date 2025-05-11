package airline.management.system.controller;

import airline.management.system.entity.BookingEntity;
import airline.management.system.entity.FlightEntity;
import airline.management.system.entity.SeatEntity;
import airline.management.system.service.AirlineService;
import airline.management.system.service.FlightService;
import airline.management.system.utils.Aircraft;
import airline.management.system.utils.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/airline/v1")
public class AirlineController {

    private final AirlineService airlineService;
    private final FlightService flightService;

    public AirlineController(AirlineService airlineService, FlightService flightService) {
        this.airlineService = airlineService;
        this.flightService = flightService;
    }

    @PostMapping("/addPassenger")
    public ResponseEntity<Passenger> addPassenger(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok(airlineService.addPassenger(name, email));
    }

    @GetMapping("/getPassenger")
    public ResponseEntity<List<Passenger>> getPassenger() {
        return ResponseEntity.ok(airlineService.getAllPassenger());
    }

    @PostMapping("/addAircraft")
    public ResponseEntity<Aircraft> addAircraft(@RequestParam String tailNumber, @RequestParam String model, @RequestParam int totalSeat) {
        return ResponseEntity.ok(airlineService.addAircraft(tailNumber, model, totalSeat));
    }

    @GetMapping("/getAircraft")
    public ResponseEntity<List<Aircraft>> getAircraft() {
        return ResponseEntity.ok(airlineService.getAllAircrafts());
    }

    @PostMapping("/addFlight")
    public ResponseEntity<String> addFlight(@RequestParam String source, @RequestParam String destination, @RequestParam LocalDateTime departureTime, @RequestParam LocalDateTime arrivalTime,  @RequestParam String aircraftNumber) {
        flightService.addFlight(source, destination, departureTime, arrivalTime, aircraftNumber);
        return ResponseEntity.ok("Added Flight successfully...");
    }

    @PostMapping("/searchFlight")
    public ResponseEntity<List<FlightEntity>> searchFlight(@RequestParam String source, @RequestParam String destination, @RequestParam LocalDate date) {
        return ResponseEntity.ok(flightService.searchFlight(source, destination, date));
    }

    @PostMapping("/bookFlight")
    public ResponseEntity<BookingEntity> bookFlight(@RequestParam String flightNumber, @RequestParam String passengerId, @RequestBody SeatEntity seat, @RequestParam double price) {
        return ResponseEntity.ok(flightService.bookFlight(flightNumber, passengerId, seat, price));
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam String bookingNumber) {
        airlineService.cancelBooking(bookingNumber);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}
