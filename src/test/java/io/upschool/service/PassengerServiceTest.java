package io.upschool.service;

import io.upschool.dto.passengerDto.PassengerRequest;
import io.upschool.dto.passengerDto.PassengerResponse;
import io.upschool.entity.Passenger;
import io.upschool.exceptions.PassengerException;
import io.upschool.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {
    @InjectMocks
    PassengerService passengerService;
    @Mock
    PassengerRepository passengerRepository;

    @Test
    public void shouldThrowPassengerException_whenIdentityNumberContainCharacter(){
        // given
        PassengerRequest request = PassengerRequest.builder().identityNumber("12345678901k").build();
        // when-then
        assertThrows(PassengerException.class, ()->passengerService.createPassenger(request));
    }
    @Test
    public void shouldReturnExistingPassenger_whenPassengerExisted(){
        // given
        PassengerRequest request = PassengerRequest.builder().identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();
        String identityNumber = request.getIdentityNumber();
        String emailAddress = request.getEmailAddress();
        when(passengerRepository.existsByIdentityNumberAndEmailAddress(identityNumber, emailAddress)).thenReturn(Boolean.TRUE);

        // when
        passengerService.createPassenger(request);

        //then
        verify(passengerRepository, times(1)).existsByIdentityNumberAndEmailAddress(identityNumber, emailAddress);
    }

    @Test
    public void shouldReturnNewPassenger_whenPassengerNotExisted(){
        // given
        PassengerRequest request = PassengerRequest.builder().identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();
        Passenger passenger = Passenger.builder().identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        // when
        passengerService.createPassenger(request);

        // then
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    public void shouldReturnPassenger_whenGetPassengerCalled(){
        PassengerRequest request = PassengerRequest.builder().identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();
        Passenger passenger = Passenger.builder().identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();

        passengerService.createPassenger(request);

        assertEquals(passenger.getName(), request.getName());
        assertEquals(passenger.getSurname(), request.getSurname());
        assertEquals(passenger.getPhoneNumber(), request.getPhoneNumber());
        assertEquals(passenger.getEmailAddress(), request.getEmailAddress());
        assertEquals(passenger.getIdentityNumber(), request.getIdentityNumber());

    }

    @Test
    public void shouldReturnPassengerResponse_whenGetPassengerResponseCalled(){
        // given
        Passenger passenger = Passenger.builder()
                .name("Zeynep")
                .surname("Özdemir")
                .emailAddress("zeynepozdemir@gmail.com")
                .phoneNumber("05426321023")
                .identityNumber("12345678901").emailAddress("zeynepozdemir@gmail.com").build();
        PassengerResponse response = PassengerResponse.builder()
                .id(1L)
                .nameSurname("Zeynep Özdemir")
                .phoneNumber("05426321023")
                .emailAddress("zeynepozdemir@gmail.com")
                .build();

        // when
        passengerService.getPassengerResponse(passenger);

        // then
        assertEquals(passenger.getName() + " " + passenger.getSurname(), response.getNameSurname());
        assertEquals(passenger.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(passenger.getEmailAddress(), response.getEmailAddress());
        assertEquals(passenger.getPhoneNumber(), response.getPhoneNumber());

    }


}