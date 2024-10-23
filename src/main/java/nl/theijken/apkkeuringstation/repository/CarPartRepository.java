package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository extends JpaRepository<CarPart, String> {
}
