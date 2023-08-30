package io.upschool.repository;

import io.upschool.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketNumber(String ticketNumber);
    List<Ticket> findByPassengerIdentityNumber(String identityNumber);
}
