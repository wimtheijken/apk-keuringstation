package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
