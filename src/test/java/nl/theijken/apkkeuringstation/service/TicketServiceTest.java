package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.ActionDto;
import nl.theijken.apkkeuringstation.dto.TicketDto;
import nl.theijken.apkkeuringstation.model.Action;
import nl.theijken.apkkeuringstation.model.Car;
import nl.theijken.apkkeuringstation.model.Customer;
import nl.theijken.apkkeuringstation.model.Ticket;
import nl.theijken.apkkeuringstation.repository.ActionRepository;
import nl.theijken.apkkeuringstation.repository.CarPartRepository;
import nl.theijken.apkkeuringstation.repository.CarRepository;
import nl.theijken.apkkeuringstation.repository.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    ActionService actionService;

    @Mock
    CarService carService;

    @Mock
    Ticket ticket;

    @Mock
    Action action;

    @Mock
    Car car;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createTicket() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setDate(LocalDate.now());
        ticket.setId(1L);
        ticket.setPrice(190);
        TicketDto ticketDto = new TicketDto();
        ticketDto.carLicensePlate = "AB-CD-99";
        ticketDto.id = 1L;
        ticketDto.date = ticket.getDate();
        ticketDto.price = 190;
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        ActionDto actionDto = new ActionDto();
        actionDto.description = "description";
        actionDto.time = 2;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        ticketDto.actions = new HashSet<>();
        ticketDto.actions.add(actionDto);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Wim");
        customer.setLastName("Theijken");
        Car car = new Car();
        car.setLicensePlate("AB-CD-99");
        car.setBrand("Toyota");
        car.setType("Aygo");
        car.setColor("Red");
        car.setCustomer(customer);
        when(ticketRepository.save(any())).thenReturn(ticket);
        when(carRepository.existsById(any())).thenReturn(true);
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        // Act
        TicketDto result = ticketService.createTicket(ticketDto);
        // Assart
        assertEquals( LocalDate.now(), result.date);
    }

    @Test
    void getTickets() {
    }

    @Test
    void getTicket() {
    }

    @Test
    void deleteTicket() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void assignActionToTicket() {
    }

    @Test
    void dtoToTicket() {
    }

    @Test
    void ticketToDto() {
    }
}