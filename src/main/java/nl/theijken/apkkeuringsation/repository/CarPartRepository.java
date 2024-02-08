package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository extends JpaRepository<CarPart, String> {
}
