package io.upschool.service;

import io.upschool.entity.Airport;
import io.upschool.entity.Route;
import io.upschool.exceptions.RouteException;
import io.upschool.repository.RouteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.RouteMatcher;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private AirportService airportService;

    @Test
    public void shouldReturnRoutes_whenGetAllRoutesCalled() {
        // given
        Route route = getRoute();
        when(routeRepository.findAll()).thenReturn(List.of(route));

        // when
        routeService.getAllRoutes();

        // then
        verify(routeRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnRoute_whenGetRouteByLocation() {
        // given
        Route route = getRoute();
        String departureCity = "Ankara";
        String arrivalCity = "İzmir";
        when(routeRepository
                .findAllByDepartureAirport_LocationAndArrivalAirport_Location(departureCity, arrivalCity))
                .thenReturn(List.of(route));

        // when
        routeService.getRouteByLocations(departureCity, arrivalCity);

        // then
        verify(routeRepository, times(1))
                .findAllByDepartureAirport_LocationAndArrivalAirport_Location(departureCity, arrivalCity);
    }

    @Test
    public void shouldReturnRoute_whenGetRouteCalled() {
        //given
        Long routeId = 1L;
        Route route = getRoute();
        when(routeRepository.findById(routeId)).thenReturn(Optional.of(route));

        // when
        routeService.getRoute(routeId);

        // then
        verify(routeRepository, times(1)).findById(routeId);

    }

    @Test
    public void shouldThrowRouteException_whenRouteIdNotFound() {
        //given
        Long routeId = 1L;
        when(routeRepository.findById(routeId)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(RouteException.class, () -> routeService.getRoute(routeId));

    }

//    @Test
//    public void shouldThrowRouteException_whenAirportsSame() {
//        // given
//        Long arrivalAirportId = 1L;
//        Long departureAirportId = 1L;
//
//        // then
//        Assertions.assertThrows(RouteException.class, ()-> routeService.createRoute())
//
//    }

    private Route getRoute() {
        Airport arrivalAirport = Airport.builder()
                .name("İzmir")
                .location("Turkey")
                .build();
        Airport departureAirport = Airport.builder()
                .name("Ankara")
                .location("Turkey")
                .build();

        Route route = Route.builder()
                .arrivalAirport(arrivalAirport)
                .departureAirport(departureAirport)
                .build();
        return route;
    }
}