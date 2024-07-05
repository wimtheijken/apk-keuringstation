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

    public Set<CarPartDto> carParts;

    public Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHrRate() {
        return hrRate;
    }

    public void setHrRate(double hrRate) {
        this.hrRate = hrRate;
    }

    public double getLabour() {
        return labour;
    }

    public void setLabour(double labour) {
        this.labour = labour;
    }

    public Set<CarPartDto> getCarParts() {
        return carParts;
    }

    public void setCarParts(Set<CarPartDto> carParts) {
        this.carParts = carParts;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
