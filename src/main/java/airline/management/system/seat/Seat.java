package airline.management.system.seat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Seat {
    private String seatNumber;
    private SeatType type;
}
