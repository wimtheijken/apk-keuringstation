package nl.theijken.apkkeuringsation.dto;

import java.time.LocalDate;
import java.util.Set;

public class CarDto {

    public String licensePlate;

    public String brand;

    public String type;

    public String color;

    public LocalDate age;

    public String customerFullName;

    public Set<Long> tickets;
}
