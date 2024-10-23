package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
