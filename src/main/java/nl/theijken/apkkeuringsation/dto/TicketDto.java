package nl.theijken.apkkeuringsation.dto;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public class TicketDto {

    public  Long id;

    public  LocalDate date;

    public double price;

    public  List<Action> actions;

    public Invoice invoice;
}
