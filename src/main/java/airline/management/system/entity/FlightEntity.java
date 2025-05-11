package airline.management.system.entity;

import airline.management.system.flight.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "flight", schema = "airline")
@NoArgsConstructor
@AllArgsConstructor
public class FlightEntity {
    @Id
    @Column(name = "flight_number")
    private String flightNumber;
    @Column(name = "source")
    private String source;
    @Column(name = "destination")
    private String destination;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @ManyToOne
    @JoinColumn(name = "tail_number")
    private AircraftEntity aircraft;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();
}
