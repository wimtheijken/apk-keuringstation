package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, String> {
}
