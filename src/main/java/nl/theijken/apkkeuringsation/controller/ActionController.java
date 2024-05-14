package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService service;

    public ActionController(ActionService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Object> createCAction(@Valid @RequestBody ActionDto actionDto, BindingResult br) {

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

//    @PutMapping("/action/{carparts}")
//    public ResponseEntity<ActionDto> updateAction(@PathVariable("CarPart") CarPart carParts, @RequestBody ActionDto actionDto) {
//        service.updateAction(carParts, ActionDto);
//        return ResponseEntity.ok(actionDto);
//    }
}
