package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}