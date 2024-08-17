package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}