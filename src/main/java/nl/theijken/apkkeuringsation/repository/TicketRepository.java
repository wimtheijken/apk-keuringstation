package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
