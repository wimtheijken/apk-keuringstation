package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
