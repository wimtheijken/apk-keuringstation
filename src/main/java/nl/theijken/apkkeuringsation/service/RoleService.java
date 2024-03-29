package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.RoleDto;
import nl.theijken.apkkeuringsation.model.Role;
import nl.theijken.apkkeuringsation.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repos;
    public RoleService(RoleRepository repos) {
        this.repos= repos;
    }

    public List<RoleDto> GetAllRoles() {
        List<Role> roles = repos.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.rolename = role.getRolename();
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }
}
