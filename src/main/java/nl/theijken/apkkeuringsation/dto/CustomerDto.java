package nl.theijken.apkkeuringsation.dto;

import nl.theijken.apkkeuringsation.model.Car;

import java.time.LocalDate;
import java.util.Set;


public class CustomerDto {

    public Long id;

    public String firstName;

    public String lastName;

    public LocalDate dob;

    public Set<Car> cars;
}
