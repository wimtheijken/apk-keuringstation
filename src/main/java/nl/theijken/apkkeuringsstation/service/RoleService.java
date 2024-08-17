package nl.theijken.apkkeuringsstation.service;

import nl.theijken.apkkeuringsstation.dto.RoleDto;
import nl.theijken.apkkeuringsstation.model.Role;
import nl.theijken.apkkeuringsstation.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository repos;

    public RoleService(RoleRepository repos) {
        this.repos = repos;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = repos.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles) {
            RoleDto roleDto = roleToDto(role);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    public Set<RoleDto> rolesToDtos(Set<Role> roles) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            roleDtos.add(roleToDto(role));
        }
        return roleDtos;
    }

    private RoleDto roleToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.rolename = role.getRolename();
        return roleDto;
    }
}
