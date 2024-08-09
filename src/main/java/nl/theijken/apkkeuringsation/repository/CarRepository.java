package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
