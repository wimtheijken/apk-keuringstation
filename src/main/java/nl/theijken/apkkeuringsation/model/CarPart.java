package nl.theijken.apkkeuringsation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "carparts")
public class CarPart {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double price;

//    @ManyToOne
//    @MapsId("wallBracketId")
//    @JoinColumn(name = "wall_bracket_id")

    @ManyToOne
//    @MapsId("actionId")
    @JoinColumn(name = "action_id")
    private Action action;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
