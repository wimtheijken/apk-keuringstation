package nl.theijken.apkkeuringsstation.service;

import nl.theijken.apkkeuringsstation.dto.*;
import nl.theijken.apkkeuringsstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsstation.model.*;
import nl.theijken.apkkeuringsstation.repository.ActionRepository;
import nl.theijken.apkkeuringsstation.repository.CarRepository;
import nl.theijken.apkkeuringsstation.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ActionRepository actionRepository;
    private final CarRepository carRepository;
    private final ActionService actionService;
    private final CarService carService;

    public TicketService(TicketRepository ticketRepository,
                         ActionRepository actionRepository,
                         ActionService actionService,
                         CarRepository carRepository,
                         CarService carService) {
        this.ticketRepository = ticketRepository;
        this.actionRepository = actionRepository;
        this.carRepository = carRepository;
        this.actionService = actionService;
        this.carService = carService;
    }

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

    // PUT -> Clear actions
    public TicketDto updateTicket(Long id, TicketDto ticketDto) {
        if(!ticketRepository.existsById(id)) {
            throw new RecordNotFoundException("No ticket found");
        }
        Ticket storedTicket = ticketRepository.findById(id).orElse(null);
        if (ticketDto.actions == null) {
            ticketDto.actions = new HashSet<>();
        } else {
            Set<Action> actions = new HashSet<>();
            assert storedTicket != null;
            storedTicket.setActions(actions);
        }
        assert storedTicket != null;
        return ticketToDto(ticketRepository.save(storedTicket));
    }

    //PUT Action -> Ticket
    public TicketDto assignActionToTicket(Long id, Long actionId) {
        if(!ticketRepository.existsById(id)) {
            throw new RecordNotFoundException("No ticket found");
        }
        Ticket storedTicket = ticketRepository.findById(id).orElse(null);
        assert storedTicket != null;
        if(storedTicket.getInvoice() != null ) {
            throw new RecordNotFoundException("Ticket already assigned to invoice");
        }
        if(!actionRepository.existsById(String.valueOf(actionId))) {
            throw new RecordNotFoundException("No action found");
        }
        Action action = actionRepository.findById(String.valueOf(actionId)).orElse(null);
        Set<Action> actions2 = storedTicket.getActions();
        for (Action action2 : actions2){
            assert action != null;
            if(Objects.equals(action2.getId(), action.getId())) {
                throw new RecordNotFoundException( action2.getDescription() + " is already used");
            }
        }
        if (storedTicket.getActions() == null) {
            Set<Action> actions = new HashSet<>();
            actions.add(action);
            assert action != null;
            storedTicket.setPrice(storedTicket.getPrice() + action.getPrice());
            storedTicket.setActions(actions);
        } else {
            Set<Action> actions = storedTicket.getActions();
            actions.add(action);
            assert action != null;
            storedTicket.setPrice(storedTicket.getPrice() + action.getPrice());
            storedTicket.setActions(actions);
        }
        return ticketToDto(ticketRepository.save(storedTicket));
    }

    // DTO -> MODEL
    public Ticket dtoToTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setDate(LocalDate.now());
        if(!carRepository.existsById(ticketDto.carLicensePlate)) {
            throw new RecordNotFoundException("No car found");
        }
        Car car = carRepository.findById(ticketDto.carLicensePlate).orElse(null);
        ticket.setCar(car);
        assert car != null;
        ticket.setCustomerId(car.getCustomer().getId());
        if (ticketDto.actions == null) {
            ticketDto.actions = new HashSet<>();
        } else {
            Set<Action> actions = new HashSet<>();
            ticket.setActions(actions);
            double total = 0.0;
            for (Action action : ticket.getActions()) {
                total = total + action.getPrice();
            }
            ticket.setPrice(total);
        }
        return ticket;
    }

    // MODEL -> DTO
    public TicketDto ticketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = ticket.getId();
        ticketDto.date = ticket.getDate();
        ticketDto.price = ticket.getPrice();
        ticketDto.actions = new HashSet<>();
        for (Action action : ticket.getActions()) {
            ticketDto.actions.add(actionService.actionToDto(action));
        }
        if (ticket.getCar() != null) {
            ticketDto.car = carService.carToDto(ticket.getCar());
        }
        return ticketDto;
    }
}
