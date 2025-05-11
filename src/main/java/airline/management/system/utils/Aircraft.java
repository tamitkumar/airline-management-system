package airline.management.system.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class Aircraft {
    private String tailNumber;
    private String model;
    private Integer totalSeat;
}
