package nl.theijken.apkkeuringstation.dto;

import java.time.LocalDate;

public class InvoiceDto {

    public Long invoiceNumber;

    public LocalDate date;

    public double vatPercentage;

    public double price;

    public double vat;

    public double total;

    public Long ticketId;

    public TicketDto ticket;

    public CustomerDto customer;
}
