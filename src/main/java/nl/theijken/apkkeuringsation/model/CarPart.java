package nl.theijken.apkkeuringsation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carparts")
public class CarPart {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double price;

    @ManyToOne
    private Action action;
}
