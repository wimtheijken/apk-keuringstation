package nl.theijken.apkkeuringsation.dto;

import java.time.LocalDate;
import java.util.Set;

public class CarInputDto {

    public String licensePlate;

    public String brand;

    public String type;

    public String color;

    public LocalDate age;

    public Long customerId;

    public Set<TicketDto> tickets;
}