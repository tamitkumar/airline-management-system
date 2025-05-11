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
    private String id;
    private String name;
    private String email;
}
