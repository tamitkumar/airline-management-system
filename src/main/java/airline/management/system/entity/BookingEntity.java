package airline.management.system.entity;

import airline.management.system.booking.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@Entity
@Table(name = "booking", schema = "airline")
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {
    @Id
    @Column(name = "booking_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_number", nullable = false)
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    private PassengerEntity passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_number", nullable = false)
    private SeatEntity seat;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    public void cancel() {
        status = BookingStatus.CANCELLED;
        seat.release();
    }
}
