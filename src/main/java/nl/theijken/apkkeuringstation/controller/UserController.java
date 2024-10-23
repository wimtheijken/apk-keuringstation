package nl.theijken.apkkeuringstation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringstation.dto.UserDto;
import nl.theijken.apkkeuringstation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PutMapping("/{username}/role/{rolename}")
    public ResponseEntity<Object> assignRoleToUser(@PathVariable("username") String username, @PathVariable("rolename") String rolename) {
        return ResponseEntity.ok(service.assignRoleToUser(username, rolename));
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
