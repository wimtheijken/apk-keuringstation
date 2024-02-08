package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.UserDto;
import nl.theijken.apkkeuringsation.model.Role;
import nl.theijken.apkkeuringsation.model.User;
import nl.theijken.apkkeuringsation.repository.RoleRepository;
import nl.theijken.apkkeuringsation.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.encoder = encoder;
    }

    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username);
        user.setPassword(encoder.encode(userDto.password));

        Set<Role> userRoles = user.getRoles();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById("ROLE_" + rolename);
            if (or.isPresent()) {
                userRoles.add(or.get());
            }
        }

        userRepos.save(user);

        return userDto;
    }

    public List<UserDto> GetUser() {
        List<User> users = userRepos.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for(User user : users) {
            UserDto userDto = new UserDto();
            userDto.username= user.getUsername();
            userDto.password = user.getPassword();
            userDtos.add(userDto);
        }
        return userDtos;

    }
}
