package nl.theijken.apkkeuringsstation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double hrRate;

    private double time;

    private double labour;

    private double materials;

    private double price;

    @ManyToMany
    private Set<CarPart> carParts = new HashSet<>();

    @ManyToMany(mappedBy = "actions")
    @JsonBackReference
    private Set<Ticket> tickets = new HashSet<>();

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

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getLabour() { return labour; }

    public void setLabour(double labour) { this.labour = labour; }

    public double getMaterials() { return materials; }

    public void setMaterials(double materials) { this.materials = materials; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public Set<CarPart> getCarParts() {
        return carParts;
    }

    public void setCarParts(Set<CarPart> carParts) {
        this.carParts = carParts;
    }

    public Set<Ticket> getTickets() { return tickets; }

    public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }
}
