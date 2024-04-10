package nl.theijken.apkkeuringsation.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Ticket;

import java.util.List;
import java.util.Set;

public class ActionDto {

    public Long id;

    public String description;

    public double hrRate;

    public double labour;

    public Set<CarPart> carParts;

    public Ticket ticket;
}
