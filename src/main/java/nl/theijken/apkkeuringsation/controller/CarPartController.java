package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.service.CarPartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carparts")
public class CarPartController {

    private final CarPartService service;

    public CarPartController(CarPartService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Object> createCarPart(@Valid @RequestBody CarPartDto carPartDto, BindingResult br) {

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
            carPartDto = service.createCarPart(carPartDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + carPartDto.id).toUriString());

            return ResponseEntity.created(uri).body(carPartDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<CarPartDto>> getAllCarParts(){ return ResponseEntity.ok(service.getCarParts()); }

}
