package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.RoleDto;
import nl.theijken.apkkeuringsation.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) { this.service = service; }
    @GetMapping()
    public ResponseEntity<List> getAllRoles() {
            return ResponseEntity.ok(service.getAllRoles());
        }
}
