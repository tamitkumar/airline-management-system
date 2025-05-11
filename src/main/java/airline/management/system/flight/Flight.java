package airline.management.system.flight;

import airline.management.system.seat.Seat;
import airline.management.system.utils.Aircraft;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status;
    private Aircraft aircraft;
    private List<Seat> seats;
}
