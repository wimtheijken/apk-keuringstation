package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
