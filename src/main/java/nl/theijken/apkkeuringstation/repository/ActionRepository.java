package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, String> {
}
