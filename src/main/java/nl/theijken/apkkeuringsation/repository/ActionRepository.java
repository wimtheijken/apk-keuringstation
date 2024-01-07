package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, String> {
}
