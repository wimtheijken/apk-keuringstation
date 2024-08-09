package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.*;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.*;
import nl.theijken.apkkeuringsation.repository.RoleRepository;
import nl.theijken.apkkeuringsation.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder, RoleService roleService) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.encoder = encoder;
        this.roleService = roleService;
    }

//     POST
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User savedUser = userRepos.save(user);
        return userToDto(savedUser);
    }

    // GET ALL
    public List<UserDto> getUsers() {
        List<User> users = userRepos.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users) {
            UserDto userDto = userToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    // GET ONE
    public UserDto getUser(String username) {
        Optional<User> user = userRepos.findById(String.valueOf(username));
        if( user.isPresent() ){
            return userToDto(user.get());
        } else {
            throw new RecordNotFoundException("No user found");
        }
    }

    // DELETE
    public boolean deleteUser(String username) {
        if(userRepos.existsById(String.valueOf(username))) {
            userRepos.deleteById(String.valueOf(username));
            return true;
        }
        return false;
    }

    // PUT
    public UserDto updateUser(String username, UserDto userDto) {
        if(!userRepos.existsById(String.valueOf(username))) {
            throw new RecordNotFoundException("No user found");
        }
        User storedUser = userRepos.findById(String.valueOf(username)).orElse(null);
        assert storedUser != null;
        storedUser.setUsername(userDto.username);
        storedUser.setPassword(encoder.encode(userDto.password));
        return userToDto(userRepos.save(storedUser));
    }

    // PUT ROLE -> USER
    public UserDto assignRoleToUser(String username, String rolename) {
        if(!userRepos.existsById(String.valueOf(username))) {
            throw new RecordNotFoundException("No user found");
        }
        User storedUser = userRepos.findById(String.valueOf(username)).orElse(null);
        if(!roleRepos.existsById(String.valueOf(rolename))) {
            throw new RecordNotFoundException("No role found");
        }
        Role role = roleRepos.findById(String.valueOf(rolename)).orElse(null);
        assert storedUser != null;
        if (storedUser.getRoles() == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            storedUser.setRoles(roles);
        } else {
            Set<Role> roles = storedUser.getRoles();
            roles.add(role);
            storedUser.setRoles(roles);
        }
        return userToDto(userRepos.save(storedUser));
    }

    // DTO -> MODEL
    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username);
        user.setPassword(encoder.encode(userDto.password));
        List<User> users = userRepos.findAll();
        for (User user2 : users){
            if(Objects.equals(user2.getUsername(), userDto.username)) {
                throw new RecordNotFoundException("Username is already in use");
            }
        }
        return user;
    }

    // MODEL -> DTO
    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.username= user.getUsername();
        userDto.password = user.getPassword();
        userDto.roles = roleService.rolesToDtos(user.getRoles());
        return userDto;
    }
}
