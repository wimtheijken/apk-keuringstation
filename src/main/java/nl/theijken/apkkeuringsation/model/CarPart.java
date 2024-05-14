package nl.theijken.apkkeuringsation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "carparts")
public class CarPart {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double price;

    @ManyToMany(mappedBy = "carParts")
    @JsonBackReference
    private Set<Action> actions = new HashSet<>();

//    @ManyToMany(mappedBy = "categories")
//    // Definieert een veel-op-veel relatie met de entiteit Post. "mappedBy" geeft aan dat de eigenaarschap van de relatie wordt beheerd door de "categories" veld in de Post entiteit.
//    @JsonBackReference
//    // Voorkomt recursieve problemen tijdens JSON serialisatie/deserialisatie door te voorkomen dat posts die bij deze categorie horen direct worden opgenomen in de JSON representatie van een categorie. Dit helpt oneindige recursie te voorkomen wanneer er bidirectionele relaties zijn.
//    private List<Post> posts = new ArrayList<>();
//    // Een lijst van posts die tot deze categorie behoren. Dit vertegenwoordigt de andere kant van de veel-op-veel relatie tussen categorieën en posts.


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

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }
}
