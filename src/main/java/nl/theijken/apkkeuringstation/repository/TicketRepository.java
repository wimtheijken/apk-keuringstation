package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
