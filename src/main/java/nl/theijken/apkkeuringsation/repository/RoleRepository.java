package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}