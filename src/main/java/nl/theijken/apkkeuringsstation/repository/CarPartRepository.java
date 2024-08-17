package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository extends JpaRepository<CarPart, String> {
}
