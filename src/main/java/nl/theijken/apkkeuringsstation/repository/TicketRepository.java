package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
