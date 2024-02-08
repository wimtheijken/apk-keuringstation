package nl.theijken.apkkeuringsation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private double hrRate;

    private double labour;
//    @OneToMany(mappedBy = "wallBracket")
//    @JsonIgnore
//    List<TelevisionWallBracket> televisionWallBrackets;
    @OneToMany(mappedBy = "action")
    @JsonIgnore
    private List<CarPart> carParts = new ArrayList<>();

    @ManyToOne
    private Ticket ticket;

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

    public List<CarPart> getCarParts() {
        return carParts;
    }

    public void setCarParts(List<CarPart> carParts) {
        this.carParts = carParts;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
