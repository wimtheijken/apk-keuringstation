package nl.theijken.apkkeuringsation.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    private String licensePlate;

    private String brand;

    private String type;

    private LocalDate age;

    @ManyToOne
    private Customer customer;

}
