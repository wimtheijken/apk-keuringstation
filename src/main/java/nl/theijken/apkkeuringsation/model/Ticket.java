package nl.theijken.apkkeuringsation.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;

//    @OneToMany(mappedBy = "ticket")
//    private List<Action> action;

//    @OneToOne(mappedBy = "ticket")
//    private Invoice invoice;

}
