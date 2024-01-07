package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.TicketDto;
import nl.theijken.apkkeuringsation.model.Ticket;
import nl.theijken.apkkeuringsation.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepos;

    public TicketService(TicketRepository repos) {
        this.ticketRepos = repos;
    }
}
