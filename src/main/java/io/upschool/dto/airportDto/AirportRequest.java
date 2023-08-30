package io.upschool.dto.airportDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AirportRequest {
    private String name;
    private String location;
}
