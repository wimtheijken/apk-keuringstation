package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
