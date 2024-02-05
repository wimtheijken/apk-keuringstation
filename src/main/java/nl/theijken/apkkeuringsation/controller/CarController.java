package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarDto carDto, BindingResult br) {

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
            carDto = service.createCar(carDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + carDto.licensePlate).toUriString());

            return ResponseEntity.created(uri).body(carDto);
        }
    }

    @PutMapping("{licensePlate}/{customerId}")
    public ResponseEntity<Object> assignCustomerToCar(@PathVariable("licensePlate") Long id, @PathVariable("customerId") Long customerId) {
        service.assignCustomerToCar(id, customerId);
        return ResponseEntity.noContent().build();
    }
}
