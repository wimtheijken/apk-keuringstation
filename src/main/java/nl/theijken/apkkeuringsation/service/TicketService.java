package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.TicketDto;
import nl.theijken.apkkeuringsation.model.Ticket;
import nl.theijken.apkkeuringsation.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repos;

    public TicketService(TicketRepository repos) {
        this.repos = repos;
    }

    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setDate(ticketDto.date);
        ticket.setPrice(ticketDto.price);
        ticket.setActions(ticketDto.actions);
        ticket.setInvoice(ticketDto.invoice);
        repos.save(ticket);
        ticketDto.id = ticket.getId();

        return ticketDto;
    }

    public List<TicketDto> GetTicket() {
        List<Ticket> tickets = repos.findAll();
        List<TicketDto> ticketDtos = new ArrayList<>();

        for(Ticket ticket : tickets) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.id = ticket.getId();
            ticketDto.date = ticket.getDate();
            ticketDto.price = ticket.getPrice();
            ticketDto.actions = ticket.getActions();
            ticketDto.invoice = ticket.getInvoice();
            ticketDtos.add(ticketDto);
        }
        return ticketDtos;
    }
}
