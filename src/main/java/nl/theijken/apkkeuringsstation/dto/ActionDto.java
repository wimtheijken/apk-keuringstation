package nl.theijken.apkkeuringsstation.dto;

import java.util.Set;

public class ActionDto {

    public Long id;

    public String description;

    public double hrRate;

    public double time;

    public double labour;

    public double materials;

    public double price;

    public Set<CarPartDto> carParts;

    public Set<TicketDto> tickets;
}
