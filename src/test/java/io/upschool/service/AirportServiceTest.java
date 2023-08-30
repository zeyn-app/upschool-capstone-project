package io.upschool.service;

import io.upschool.dto.airportDto.AirportRequest;
import io.upschool.dto.airportDto.AirportResponse;
import io.upschool.entity.Airport;
import io.upschool.exceptions.AirportException;
import io.upschool.repository.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {
    @InjectMocks
    private AirportService airportService;
    @Mock
    private AirportRepository airportRepository;

    @Test
    public void shouldReturnAirports_whenFindAllCalled() {
        // given
        Airport airport = Airport.builder().name("Test Airport").build();
        when(airportRepository.findAll()).thenReturn(List.of(airport));

        // when --> action alınacak kısım
        List<AirportResponse> responses = airportService.getAllAirports();

        // then
        verify(airportRepository, times(1)).findAll();
        Assertions.assertNotNull(responses);
    }


    @Test
    public void shouldReturnAirport_whenFindByIdCalled() {
        // given
        Long airportId = 2L;
        Airport airport = Airport.builder().name("Test Airport").build();
        when(airportRepository.findById(airportId)).thenReturn(Optional.of(airport));

        // when
//        Airport serviceAirport = airportService.getAirport(airportId);
        airportService.getAirport(airportId);

        // then
        verify(airportRepository, times(1)).findById(airportId);
//        Assertions.assertNotNull(serviceAirport);
    }

    @Test
    public void shouldThrowAirportException_whenAirportNotFound() {
        // given
        Long airportId = 99L;
        when(airportRepository.findById(airportId)).thenReturn(Optional.empty());

        // when
//        airportService.getAirport(airportId);

        // then
        Assertions.assertThrows(AirportException.class, ()->airportService.getAirport(airportId));
    }

    @Test
    public void shouldReturnAirports_whenFindAirportByNameCalled(){
        // given
        String airportName = "Turkish Airlines";
        Airport airport = Airport.builder()
                .id(8L)
                .name("Turkish Airlines")
                .location("Turkey")
                .build();
        when(airportRepository.findAirportByNameContainingIgnoreCase(airportName)).thenReturn(List.of(airport));

        // when
        airportService.findAirportByName(airportName);

        // then
        verify(airportRepository, times(1)).findAirportByNameContainingIgnoreCase(airportName);
    }

    /*
    public AirportResponse createAirport(AirportRequest airportRequest) {
        checkIfExist(airportRequest);
        Airport airport = airportRepository.save(getAirport(airportRequest));
        return getAirportResponse(airport);
    }
     */

    @Test
    public void shouldReturnAirport_whenCreateAirportCalled(){
        // given
        AirportRequest airportRequest = AirportRequest.builder().name("Turkish Airlines").location("Turkey").build();
        Airport airport = Airport.builder().name("Turkish Airlines").location("Turkey").build();
        when(airportRepository.save(any(Airport.class))).thenReturn((airport));

        // when
        AirportResponse response = airportService.createAirport(airportRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(airportRequest.getName(), response.getName());
        Assertions.assertEquals(airportRequest.getLocation(), response.getLocation());

        // then
        verify(airportRepository, times(1)).save(any(Airport.class));
    }


/*
  private void checkIfExist(AirportRequest airportRequest) {
        boolean exist = airportRepository.existsByNameAndLocationContainingIgnoreCase(airportRequest.getName(), airportRequest.getLocation());
        if (Boolean.TRUE == (exist)) throw new AirportException(AirportException.AIRPORT_EXIST);
    }
 */
    @Test
    public void shouldThrowAirportException_whenAirportExisted(){
        // given
        String airportName = "Turkish Airlines";
        String airportLocation = "Turkey";
        AirportRequest airportRequest = AirportRequest.builder().name("Turkish Airlines").location("Turkey").build();
        when(airportRepository.existsByNameAndLocationContainingIgnoreCase(airportName, airportLocation)).thenReturn(Boolean.TRUE);

        // when-then
        Assertions.assertThrows(AirportException.class, ()->airportService.createAirport(airportRequest));
    }
}