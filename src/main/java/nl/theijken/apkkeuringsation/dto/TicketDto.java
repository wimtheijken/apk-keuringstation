package nl.theijken.apkkeuringsation.dto;

import java.time.LocalDate;
import java.util.Set;

public class TicketDto {

    public  Long id;

    public  LocalDate date;

    public double price;

    public Set<ActionDto> actions;

    public InvoiceDto invoice;

    public String carLicensePlate;

    public CarDto car;
}
