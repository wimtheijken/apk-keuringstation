package nl.theijken.apkkeuringsation.controller;

import nl.theijken.apkkeuringsation.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
