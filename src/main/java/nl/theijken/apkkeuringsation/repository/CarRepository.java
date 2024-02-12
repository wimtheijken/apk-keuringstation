package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {
    public List<Car> findByAgeAfter(LocalDate age);
}
