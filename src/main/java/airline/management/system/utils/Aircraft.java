package airline.management.system.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class Aircraft {

    private final String tailNumber;
    private final String model;
    private final Integer totalSeat;
}
