package airline.management.system.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class Passenger {
    private final String id;
    private final String name;
    private final String email;
}
