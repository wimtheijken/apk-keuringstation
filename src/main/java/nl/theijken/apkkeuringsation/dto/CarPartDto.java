package nl.theijken.apkkeuringsation.dto;

import jakarta.persistence.*;
import nl.theijken.apkkeuringsation.model.Action;

import java.util.Set;

public class CarPartDto {

    public Long id;

    public String name;

    public double price;

    public Set<Action> actions;
}
