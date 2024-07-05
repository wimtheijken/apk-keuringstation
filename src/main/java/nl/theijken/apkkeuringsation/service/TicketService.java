package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.dto.TicketDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Ticket;
import nl.theijken.apkkeuringsation.repository.ActionRepository;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import nl.theijken.apkkeuringsation.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

//    public TicketDto createTicket(TicketDto ticketDto) {
//        Ticket ticket = new Ticket();
//        ticket.setDate(ticketDto.date);
//        ticket.setPrice(ticketDto.price);
//        ticket.setActions(ticketDto.actions);
//        ticket.setInvoice(ticketDto.invoice);
//        ticketRepository.save(ticket);
//        ticketDto.id = ticket.getId();
//
//        return ticketDto;
//    }
//
//    public List<TicketDto> GetTickets() {
//        List<Ticket> tickets = ticketRepository.findAll();
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for(ticket ticket : tickets) {
//            TicketDto ticketDto = new TicketDto();
//            ticketDto.id = ticket.getId();
//            ticketDto.date = ticket.getDate();
//            ticketDto.price = ticket.getPrice();
//            ticketDto.actions = ticket.getActions();
//            ticketDto.invoice = ticket.getInvoice();
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }

    // POST
    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = dtoToTicket(ticketDto);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketToDto(savedTicket);
    }

    // GET ALL
    public List<TicketDto> getTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketDto> ticketDtos = new ArrayList<>();
        for( Ticket ticket : tickets ) {
            TicketDto ticketDto = ticketToDto(ticket);
            ticketDtos.add(ticketDto);
        }
        return ticketDtos;
    }

    // GET ONE
    public TicketDto getTicket(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if( ticket.isPresent() ){
            return ticketToDto(ticket.get());
        } else {
            throw new RecordNotFoundException("No ticket found");
        }
    }

    // DELETE
    public boolean deleteTicket(Long id) {
        if(ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // PUT
    public TicketDto updateTicket(Long id, TicketDto ticketDto) {
        if(!ticketRepository.existsById(id)) {
            throw new RecordNotFoundException("No ticket found");
        }
        Ticket storedTicket = ticketRepository.findById(id).orElse(null);
        storedTicket.setId(ticketDto.id);
        storedTicket.setDate(ticketDto.date);
        storedTicket.setPrice(ticketDto.price);
        storedTicket.setActions(ticketDto.actions);
        storedTicket.setInvoice(ticketDto.invoice);
        return ticketToDto(ticketRepository.save(storedTicket));
    }

    // DTO -> MODEL
    private Ticket dtoToTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setDate(ticketDto.date);
        ticket.setPrice(ticketDto.price);
        ticket.setActions(ticketDto.actions);
        ticket.setInvoice(ticketDto.invoice);
        ticketRepository.save(ticket);
        ticketDto.id = ticket.getId();

        return ticket;
    }

    // MODEL -> DTO
    private TicketDto ticketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = ticket.getId();
        ticketDto.date = ticket.getDate();
        ticketDto.price = ticket.getPrice();
        ticketDto.actions = ticket.getActions();
        ticketDto.invoice = ticket.getInvoice();
        return ticketDto;
    }
}
