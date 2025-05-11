package airline.management.system.service;

import airline.management.system.utils.Aircraft;
import airline.management.system.utils.Passenger;

import java.util.List;

public interface AirlineService {

    Passenger addPassenger(String name, String email);
    Aircraft addAircraft(String tailNumber, String model, int totalSeat);
    void cancelBooking(String bookingNumber);
    List<Passenger> getAllPassenger();
    List<Aircraft> getAllAircrafts();
}
