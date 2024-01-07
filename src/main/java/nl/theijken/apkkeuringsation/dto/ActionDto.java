package nl.theijken.apkkeuringsation.dto;

import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;

import java.util.List;

public class ActionDto {

    public String description;

    public double labour;

    public List<CarPart> carpart;

    public Action ticket;
}
