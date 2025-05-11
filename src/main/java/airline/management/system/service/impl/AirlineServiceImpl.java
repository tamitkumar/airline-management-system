package airline.management.system.service.impl;

import airline.management.system.booking.BookingManager;
import airline.management.system.entity.AircraftEntity;
import airline.management.system.entity.PassengerEntity;
import airline.management.system.repository.AircraftRepository;
import airline.management.system.repository.PassengerRepository;
import airline.management.system.service.AirlineService;
import airline.management.system.utils.Aircraft;
import airline.management.system.utils.Passenger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirlineServiceImpl implements AirlineService {
    private final Map<String, Aircraft> aircrafts;
    private final Map<String, Passenger> passengers;
    private final BookingManager bookingManager;
    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;
    public AirlineServiceImpl(PassengerRepository passengerRepository, AircraftRepository aircraftRepository, BookingManager bookingManager) {
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
        this.aircrafts = new HashMap<>();
        this.passengers = new HashMap<>();
        this.bookingManager = bookingManager;
    }

    @Override
    public Passenger addPassenger(String name, String email) {
        PassengerEntity passengerEntity = passengerRepository.save(PassengerEntity.builder().id(UUID.randomUUID().toString())
                .name(name).email(email).build());
        Passenger passenger = Passenger.builder().id(passengerEntity.getId()).name(passengerEntity.getName()).email(passengerEntity.getEmail()).build();
        passengers.put(passenger.getId(), passenger);
        return passenger;
    }

    @Override
    public List<Passenger> getAllPassenger() {
        List<Passenger> passengers = new ArrayList<>();
        List<PassengerEntity> passengerDbs = passengerRepository.findAll();
        passengerDbs.forEach(pas ->
                passengers.add(Passenger.builder().id(pas.getId()).name(pas.getName()).email(pas.getEmail()).build())
        );
        return passengers;
    }

    @Override
    public Aircraft addAircraft(String tailNumber, String model, int totalSeat) {
        AircraftEntity aircraftEntity = aircraftRepository.save(AircraftEntity.builder().tailNumber(tailNumber).totalSeat(totalSeat).model(model).build());
        Aircraft aircraft = Aircraft.builder().tailNumber(aircraftEntity.getTailNumber()).model(aircraftEntity.getModel()).totalSeat(aircraftEntity.getTotalSeat()).build();
        aircrafts.put(tailNumber, aircraft);
        return aircraft;
    }

    @Override
    public List<Aircraft> getAllAircrafts() {
        List<Aircraft> aircraft = new ArrayList<>();
        List<AircraftEntity> aircraftDbs = aircraftRepository.findAll();
        aircraftDbs.forEach(air ->
                aircraft.add(Aircraft.builder().tailNumber(air.getTailNumber()).model(air.getModel()).totalSeat(air.getTotalSeat()).build())
        );
        return aircraft;
    }

    @Override
    public void cancelBooking(String bookingNumber) {
        bookingManager.cancelBooking(bookingNumber);
    }


}
