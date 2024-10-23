package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
