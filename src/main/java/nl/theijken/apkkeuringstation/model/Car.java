package nl.theijken.apkkeuringstation.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    private String licensePlate;

    private String brand;

    private String type;

    private String color;

    private LocalDate age;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "car")
    private Set<Ticket> tickets;

    public String getLicensePlate() { return licensePlate; }

    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Ticket> getTickets() { return tickets; }

    public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }
}
