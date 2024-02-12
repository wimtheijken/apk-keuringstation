package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.Customer;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CarService carService;


    public CustomerService(CustomerRepository customerRepository, CarService carService) {
        this.customerRepository = customerRepository;
        this.carService = carService;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.firstName);
        customer.setLastName(customerDto.lastName);
        customer.setDob(customerDto.dob);
        // Assuming customerDto.cars is a Set<CarDto>
        if (customerDto.cars == null) {
            customerDto.cars = new HashSet<>();
        } else {
            Set<Car> cars = carService.dtosToCars(customerDto.cars);
            customer.setCars(cars);
        }

        customer = customerRepository.save(customer); // Save customer first
        customerDto.id = customer.getId(); // Then set the ID in DTO

        return customerDto;
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.id = customer.getId();
            customerDto.firstName = customer.getFirstName();
            customerDto.lastName = customer.getLastName();
            customerDto.dob = customer.getDob();
            customerDto.cars = carService.carsToDtos(customer.getCars());
            customerDtos.add(customerDto);
        }

        return customerDtos;
    }

}