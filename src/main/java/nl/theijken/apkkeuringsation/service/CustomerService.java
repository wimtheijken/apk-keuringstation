package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.model.Customer;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomerService {

    private final CustomerRepository repos;

    public CustomerService(CustomerRepository repos) {
        this.repos = repos;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.firstName);
        customer.setLastName(customerDto.lastName);
//        customer.setDob(customerDto.dob);
        repos.save(customer);
        customerDto.id = customer.getId();

        return customerDto;
    }

    public List<CustomerDto> GetTeacher() {
        List<Customer> customers = repos.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for(Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();

            customerDto.id = customer.getId();

            customerDto.firstName = customer.getFirstName();

            customerDto.lastName = customer.getLastName();

//            customerDto.dob = customer.getDob();

            customerDtos.add(customerDto);

        }

        return customerDtos;

    }


}
