package io.upschool.service;

import io.upschool.dto.cardDto.CardRequest;
import io.upschool.dto.cardDto.CardResponse;
import io.upschool.entity.Card;
import io.upschool.exceptions.CardException;
import io.upschool.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;

    @Test
    public void shouldReturnCard_whenCreateCardCalled() {
        // given
        Card card = Card.builder().cardNumber("1234567890123456").cvv("123").expirationDate("12/2023").build();
        CardRequest request = CardRequest.builder().cardNumber("1234567890123456").cvv("123").expirationDate("12/2023").build();

        when(cardRepository.save(any(Card.class))).thenReturn(card);

        // when
        Card serviceCard = cardService.createCard(request);

        assertNotNull(serviceCard);
        assertEquals(serviceCard.getCardNumber(), request.getCardNumber());
        assertEquals(serviceCard.getCvv(), request.getCvv());
        assertEquals(serviceCard.getExpirationDate(), request.getExpirationDate());
        // then
        verify(cardRepository, times(1)).save(any(Card.class));
    }


    @Test
    public void shouldThrowCardException_whenCardNumberNotValid() {
        // given
        CardRequest request = CardRequest.builder().cardNumber("123456789012345").cvv("123").expirationDate("12/2023").build();

        // when-then
        assertThrows(CardException.class, () -> cardService.createCard(request));
    }

    @Test
    public void shouldThrowCardException_whenCvvNotValid() {
        // given
        CardRequest request = CardRequest.builder().cardNumber("1234567890123456").cvv("123b").expirationDate("12/2023").build();
        // when-then
        assertThrows(CardException.class, () -> cardService.createCard(request));
    }

    @Test
    public void shouldReturnCardResponse_whenGetCardResponseCalled(){
        // given
        Card card = Card.builder().cardNumber("123456******3456").cvv("123").expirationDate("12/2023").build();
        CardResponse response = CardResponse.builder().cardNumber("123456******3456").build();

        // when
        cardService.getCardResponse(card);

        assertEquals(card.getCardNumber(), response.getCardNumber());
    }
}