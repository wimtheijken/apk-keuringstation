package nl.theijken.apkkeuringsation.dto;
import jakarta.persistence.OneToOne;
import nl.theijken.apkkeuringsation.model.Ticket;

import java.time.LocalDate;

public class InvoiceDto {

    public Long invoiceNumber;

    public LocalDate date;

    public double vat;

    public double price;

    public double total;
    public Ticket ticket;

}
