package nl.theijken.apkkeuringsation.dto;

import jakarta.persistence.*;
import nl.theijken.apkkeuringsation.model.Action;

public class CarPartDto {

    public Long id;

    public String name;

    public double price;

    public Action action;
}
