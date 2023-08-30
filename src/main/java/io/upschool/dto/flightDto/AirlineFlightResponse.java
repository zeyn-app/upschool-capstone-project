package io.upschool.dto.flightDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AirlineFlightResponse {
    private Long id;
    @JsonProperty(value = "Airline")
    private String companyName;
    @JsonProperty(value = "Date")
    private LocalDateTime departureDateTime;
    @JsonProperty(value = "Departure Airport")
    private String departureAirportName;
    @JsonProperty(value = "Arrival Airport")
    private String arrivalAirportName;
    private Double price;
}
