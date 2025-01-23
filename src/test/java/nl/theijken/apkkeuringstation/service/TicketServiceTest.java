package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.ActionDto;
import nl.theijken.apkkeuringstation.dto.TicketDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.*;
import nl.theijken.apkkeuringstation.repository.CarRepository;
import nl.theijken.apkkeuringstation.repository.ActionRepository;
import nl.theijken.apkkeuringstation.repository.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

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
    ActionRepository actionRepository;

    @Mock
    ActionService actionService;

    @Mock
    CarService carService;

    @Mock
    Ticket ticket;

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
        // Arrange
        List<TicketDto> ticketDtos = new ArrayList<>();
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        ticketDtos.add(ticketDto);
        TicketDto ticketDto2 = new TicketDto();
        ticketDto2.id = 2L;
        ticketDto2.date = LocalDate.now();
        ticketDtos.add(ticketDto2);
        TicketDto ticketDto3 = new TicketDto();
        ticketDto3.id = 3L;
        ticketDto3.date = LocalDate.now();
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        tickets.add(ticket);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setDate(LocalDate.now());
        tickets.add(ticket2);
        Ticket ticket3 = new Ticket();
        ticket3.setId(3L);
        ticket3.setDate(LocalDate.now());
        tickets.add(ticket3);
        when(ticketRepository.findAll()).thenReturn(tickets);
        // Act
        List<TicketDto> results = ticketService.getTickets();
        // Assart
        int counter = 0;
        for ( TicketDto result : results ) {
            counter++;
        }
        assertEquals(3, counter);
    }

    @Test
    void getTicket() {
        // Arrange
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        // Act
        TicketDto result = ticketService.getTicket(1L);
        // Assert
        assertEquals(LocalDate.now(), result.date);
    }

    @Test
    void getTicketNotFound() {
        // Arrange
        String expected = "No ticket found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.getTicket(1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }


    @Test
    void deleteTicket() {
        // Arrange
        when(ticketRepository.existsById(any())).thenReturn(true);
        // Act
        Boolean exists = ticketRepository.existsById(1L);
        Boolean result = ticketService.deleteTicket(1L);
        // Assert
        assertEquals( true, exists);;
        assertEquals( true, result);;
    }

    @Test
    void deleteNoTicket() {
        // Arrange
        when(ticketRepository.existsById(any())).thenReturn(false);
        // Act
        Boolean exists = ticketRepository.existsById(1L);
        Boolean result = ticketService.deleteTicket(1L);
        // Assert
        assertEquals( false, exists);
        assertEquals( false, result);
    }

    @Test
    void updateTicket() {
        // Arrange
        Set<Action> storedActions = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        storedActions.add(action);
        Action action2 = new Action();
        action2.setId(2L);
        action2.setDescription("description");
        action2.setTime(2);
        action2.setHrRate(45);
        action2.setLabour(90);
        action2.setMaterials(100);
        action2.setPrice(190);
        storedActions.add(action2);
        ActionDto actionDto = new ActionDto();
        actionDto.id = 1L;
        actionDto.description = "description";
        actionDto.time = 2;
        actionDto.hrRate = 45;
        actionDto.labour = 90;
        actionDto.materials = 100;
        actionDto.price = 190;
        ActionDto actionDto2 = new ActionDto();
        actionDto2.id = 2L;
        actionDto2.description = "description";
        actionDto2.time = 2;
        actionDto2.hrRate = 45;
        actionDto2.labour = 90;
        actionDto2.materials = 100;
        actionDto2.price = 190;
        TicketDto ticketDto = new TicketDto();
        ticketDto.actions = new HashSet<>();
        ticketDto.actions.add(actionDto);
        ticketDto.actions.add(actionDto2);
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        Ticket storedTicket = new Ticket();
        storedTicket.setId(1L);
        storedTicket.setActions(storedActions);
        storedTicket.setDate(LocalDate.now());

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        Set<Action> actions = new HashSet<>();
        ticket.setActions(actions);
        when(!ticketRepository.existsById(any())).thenReturn(true);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(storedTicket));
        when(ticketRepository.save(any())).thenReturn(ticket);
        // Act
        TicketDto result = ticketService.updateTicket(1L, ticketDto);
        // Assert
        assertEquals( "[]", result.actions.toString());
    }

    @Test
    void updateTicketNotFound() {
        // Arrange
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        String expected = "No ticket found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.updateTicket(1L, ticketDto));
        // Assert
        assertEquals( expected, result.getMessage());
    }

    @Test
    void updateTicketActionNotFound() {
        // Arrange
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        ticketDto.actions = null;
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        when(!ticketRepository.existsById(any())).thenReturn(true);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any())).thenReturn(ticket);
        // Act
        TicketDto result = ticketService.updateTicket(1L, ticketDto);
        // Assert
        assertEquals( "[]", result.actions.toString());
        assertEquals( 1L, result.id);
    }

    @Test
    void assignActionToTicket() {
        // Arrange
        Set<Action> actions = new HashSet<>();
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        actions.add(action);
        Action action2 = new Action();
        action2.setId(2L);
        action2.setDescription("description");
        action2.setTime(2);
        action2.setHrRate(45);
        action2.setLabour(90);
        action2.setMaterials(100);
        action2.setPrice(190);
        Ticket storedTicket = new Ticket();
        storedTicket.setDate(LocalDate.now());
        storedTicket.setId(1L);
        storedTicket.setPrice(190);
        storedTicket.setActions(actions);
        when(ticketRepository.existsById(any())).thenReturn(true);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(storedTicket));
        when(actionRepository.findById(any())).thenReturn(Optional.of(action2));
        when(ticketRepository.save(any())).thenReturn(storedTicket);
        // Act
        TicketDto result = ticketService.assignActionToTicket(1L, 1L);
        // Assert
        assertEquals( "[null]", result.actions.toString());
    }

    @Test
    void assignActionToTicketNoTicketFound() {
        // Arrange
        when(ticketRepository.existsById(any())).thenReturn(false);
        String expected = "No ticket found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.assignActionToTicket(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void assignActionToTicketButTicketAssignedToInvoice() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(1l);
        Ticket storedTicket = new Ticket();
        storedTicket.setId(1L);
        storedTicket.setDate(LocalDate.now());
        storedTicket.setInvoice(invoice);
        when(ticketRepository.existsById(any())).thenReturn(true);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(storedTicket));
        String expected = "Ticket already assigned to invoice";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.assignActionToTicket(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void assignActionToTicketNoActionFound() {
        // Arrange
        Action action = new Action();
        action.setId(1L);
        action.setDescription("description");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        Ticket storedTicket = new Ticket();
        storedTicket.setId(1L);
        storedTicket.setDate(LocalDate.now());
        when(ticketRepository.existsById(any())).thenReturn(true);
        when(actionRepository.existsById(any())).thenReturn(false);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(storedTicket));
        String expected = "No action found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.assignActionToTicket(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void assignActionToTicketButActionIsAlraedyUsed() {
        // Arrange
        Action action = new Action();
        Set<Action> actions = new HashSet<>();
        action.setId(1L);
        action.setDescription("this action");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        actions.add(action);
        Ticket storedTicket = new Ticket();
        storedTicket.setId(1L);
        storedTicket.setDate(LocalDate.now());
        storedTicket.setActions(actions);
        when(ticketRepository.existsById(any())).thenReturn(true);
        when(actionRepository.existsById(any())).thenReturn(true);
        when(actionRepository.findById(any())).thenReturn(Optional.of(action));
        when(ticketRepository.findById(any())).thenReturn(Optional.of(storedTicket));
        String expected = "this action is already used";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.assignActionToTicket(1L, 1L));
        // Assert
        assertEquals(expected, result.getMessage());
    }

    @Test
    void dtoToTicket() {
        // Arrange
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
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        ticket.setCar(car);
        when(carRepository.existsById(any())).thenReturn(true);
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        // Act
        Ticket result = ticketService.dtoToTicket(ticketDto);
        // Assert
        assertEquals( LocalDate.now(), result.getDate());
    }

    @Test
    void dtoToTicketWithAction() {
        // Arrange
        Action action = new Action();
        Set<Action> actions = new HashSet<>();
        action.setId(1L);
        action.setDescription("this action");
        action.setTime(2);
        action.setHrRate(45);
        action.setLabour(90);
        action.setMaterials(100);
        action.setPrice(190);
        actions.add(action);
        Action action2 = new Action();
        action2.setId(1L);
        action2.setDescription("this action");
        action2.setTime(2);
        action2.setHrRate(45);
        action2.setLabour(90);
        action2.setMaterials(100);
        action2.setPrice(190);
        actions.add(action2);
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
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        ticket.setCar(car);
        ticket.setActions(actions);
        when(carRepository.existsById(any())).thenReturn(true);
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        // Act
        Ticket result = ticketService.dtoToTicket(ticketDto);
        // Assert
        assertEquals( "[]", result.getActions().toString());
    }

    @Test
    void ticketWitoutCar() {
        // Arrange
        TicketDto ticketDto = new TicketDto();
        ticketDto.actions = new HashSet<>();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        when(carRepository.existsById(any())).thenReturn(false);
        String expected = "No car found";
        // Act
        RecordNotFoundException result = assertThrows(RecordNotFoundException.class, () -> ticketService.dtoToTicket(ticketDto));
        // Assert
        assertEquals(expected, result.getMessage());
    }


    @Test
    void ticketToDto() {
        // Arrange
        TicketDto ticketDto = new TicketDto();
        ticketDto.id = 1L;
        ticketDto.date = LocalDate.now();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDate(LocalDate.now());
        // Act
        TicketDto result = ticketService.ticketToDto(ticket);
        // Assert
        assertEquals( 1L, result.id);
        assertEquals( LocalDate.now(), result.date);
    }
}