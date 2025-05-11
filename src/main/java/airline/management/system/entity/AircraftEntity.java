package airline.management.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@Entity
@Table(name = "aircraft", schema = "airline")
@NoArgsConstructor
@AllArgsConstructor
public class AircraftEntity {
    @Id
    @Column(name = "tail_number")
    private String tailNumber;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "total_seat", nullable = false)
    private Integer totalSeat;
}
