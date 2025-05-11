package airline.management.system.entity;

import airline.management.system.seat.SeatStatus;
import airline.management.system.seat.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seat", schema = "airline")
public class SeatEntity {
    @Id
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SeatType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SeatStatus status;

    @ManyToOne
    @JoinColumn(name = "flight_number", nullable = false)
    private FlightEntity flight;

    public void reserve() {
        this.status = SeatStatus.RESERVED;
    }

    public void release() {
        this.status = SeatStatus.AVAILABLE;
    }

    public synchronized boolean isBooked() {
        return this.status == SeatStatus.OCCUPIED;
    }
}
