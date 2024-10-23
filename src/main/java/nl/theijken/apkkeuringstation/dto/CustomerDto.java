package nl.theijken.apkkeuringstation.dto;

import java.time.LocalDate;
import java.util.Set;

public class CustomerDto {

    public Long id;

    public String firstName;

    public String lastName;

    public LocalDate dob;

    public Set<CarDto> cars;

    public Set<Long> invoices;
}
