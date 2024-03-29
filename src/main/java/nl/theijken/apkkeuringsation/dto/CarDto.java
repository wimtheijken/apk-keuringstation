package nl.theijken.apkkeuringsation.dto;

import nl.theijken.apkkeuringsation.model.Customer;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDate;

public class CarDto {

    public String licensePlate;

    public String brand;

    public String type;

    public String color;

    public LocalDate age;

    public String customerFullName;
}
