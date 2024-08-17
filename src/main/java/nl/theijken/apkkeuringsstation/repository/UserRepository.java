package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
