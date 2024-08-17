package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
