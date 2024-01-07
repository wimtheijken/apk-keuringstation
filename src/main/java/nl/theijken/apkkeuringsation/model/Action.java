package nl.theijken.apkkeuringsation.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private double labour;

    @OneToMany(mappedBy = "action")
    private List<CarPart> carpart;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Action ticket;

}
