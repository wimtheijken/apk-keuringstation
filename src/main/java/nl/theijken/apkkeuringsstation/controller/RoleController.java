package nl.theijken.apkkeuringsstation.controller;

import nl.theijken.apkkeuringsstation.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
