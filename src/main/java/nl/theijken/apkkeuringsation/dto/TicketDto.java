package nl.theijken.apkkeuringsation.dto;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.Invoice;

import java.util.List;

public class TicketDto {

    //    private localDate age;

    private List<Action> action;

    private Invoice invoice;
}
