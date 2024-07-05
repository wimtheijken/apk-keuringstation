package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Customer;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CarService carService;

    public CustomerService(CustomerRepository customerRepository, CarService carService) {
        this.customerRepository = customerRepository;
        this.carService = carService;
    }

    // POST
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = dtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerToDto(savedCustomer);
    }

    // GET ALL
    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDto customerDto = customerToDto(customer);
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    // GET ONE
    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if( customer.isPresent() ){
            return customerToDto(customer.get());
        } else {
            throw new RecordNotFoundException("No customer found");
        }
    }

    // DELETE
    public boolean deleteCustomer(Long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // PUT
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        if(!customerRepository.existsById(id)) {
            throw new RecordNotFoundException("No customer found");
        }
        Customer storedCustomer = customerRepository.findById(id).orElse(null);
        storedCustomer.setId(customerDto.getId());
        storedCustomer.setFirstName(customerDto.firstName);
        storedCustomer.setLastName(customerDto.lastName);
        storedCustomer.setDob(customerDto.dob);
        if (customerDto.cars == null) {
            customerDto.cars = new HashSet<>();
        } else {
            Set<Car> cars = carService.dtosToCars(customerDto.cars);
            storedCustomer.setCars(cars);
        }
        return customerToDto(customerRepository.save(storedCustomer));
    }

    // DTO -> MODEL
    private Customer dtoToCustomer(CustomerDto customerDto) {
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
        return customer;
    }

    // MODEL -> DTO
    private CustomerDto customerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.id = customer.getId();
        customerDto.firstName = customer.getFirstName();
        customerDto.lastName = customer.getLastName();
        customerDto.dob = customer.getDob();
        customerDto.cars = carService.carsToDtos(customer.getCars());
        return customerDto;
    }
}