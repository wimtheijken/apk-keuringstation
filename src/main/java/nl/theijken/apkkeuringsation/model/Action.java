package nl.theijken.apkkeuringsation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private double hrRate;

    private double labour;

    @ManyToMany
    private Set<CarPart> carParts = new HashSet<>();

    @ManyToOne
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

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

    public Set<CarPart> getCarParts() {
        return carParts;
    }

    public void setCarParts(Set<CarPart> carParts) {
        this.carParts = carParts;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
