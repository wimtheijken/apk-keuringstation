package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService service;
    private final ActionService actionService;

    public ActionController(ActionService service, ActionService actionService) {
        this.service = service;
        this.actionService = actionService;
    }

    @PostMapping
    public ResponseEntity<Object> createAction(@Valid @RequestBody ActionDto actionDto, BindingResult br) {

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
            actionDto = service.createAction(actionDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + actionDto.id).toUriString());

            return ResponseEntity.created(uri).body(actionDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActionDto> updateAction(@PathVariable("id") Long id, @RequestBody ActionDto actionDto) {
        return ResponseEntity.ok(service.updateAction(id, actionDto));
    }

    //Dit is een andere manier om het te doen, met twee Pathvariables, maar het kan uiteraard ook anders.
    @PutMapping("/{id}/{carPartId}")
    public ResponseEntity<Object> assignCarPartToAction(@PathVariable("id") Long id, @PathVariable("carPartId") Long carPartId, @RequestBody ActionDto actionDto) {
        service.assignCarPartToAction(id, carPartId, actionDto);
        return ResponseEntity.ok(service.updateAction(id, actionDto));
    }

    @GetMapping
    public ResponseEntity<List<ActionDto>> getAllActions() {
        return ResponseEntity.ok(service.getActions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionDto> getAction(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getAction(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAction(@PathVariable("id") Long id) {
        boolean check = service.deleteAction(id);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No action found");
        }
    }
}
