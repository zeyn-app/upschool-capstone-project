package io.upschool.dto.flightDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {
    private Long id;
    private Double price;
    @JsonProperty(value = "Airline")
    private String companyName;
    @JsonProperty(value = "Date")
    private LocalDateTime departureDateTime;
    @JsonProperty(value = "Departure Airport")
    private String departureAirportName;
    @JsonProperty(value = "Arrival Airport")
    private String arrivalAirportName;
    @JsonProperty(value = "capacity")
    private Integer capacity;
}
