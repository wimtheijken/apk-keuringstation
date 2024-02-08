package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Invoice;
import nl.theijken.apkkeuringsation.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    public List<Ticket> findByDateAfter(LocalDate date);
}
