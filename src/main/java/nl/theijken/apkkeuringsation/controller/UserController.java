package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.dto.UserDto;
import nl.theijken.apkkeuringsation.service.UserService;
import nl.theijken.apkkeuringsation.model.Role;
import nl.theijken.apkkeuringsation.model.User;
import nl.theijken.apkkeuringsation.repository.RoleRepository;
import nl.theijken.apkkeuringsation.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            userDto = service.createUser(userDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + userDto.username).toUriString());

            return ResponseEntity.created(uri).body(userDto);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(service.updateUser(username, userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getUser(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        boolean check = service.deleteUser(username);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No user found");
        }
    }
}
