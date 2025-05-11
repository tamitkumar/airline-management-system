package airline.management.system.booking;

import airline.management.system.flight.Flight;
import airline.management.system.seat.Seat;
import airline.management.system.utils.Passenger;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Booking {
    private String id;
    private Flight flight;
    private Passenger passenger;
    private Seat seat;
    private double price;
    private BookingStatus status;
}
