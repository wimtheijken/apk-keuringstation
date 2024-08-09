package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.service.CarPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @PutMapping("/{id}")
    public ResponseEntity<CarPartDto> updateCartPart(@PathVariable("id") Long id, @RequestBody CarPartDto carPartDto) {
//        service.updateAction(id, dto);
        return ResponseEntity.ok(service.updateCarPart(id, carPartDto));
    }

    @GetMapping
    public ResponseEntity<List<CarPartDto>> getAllCarParts(){ return ResponseEntity.ok(service.getCarParts()); }

    @GetMapping("/{id}")
    public ResponseEntity<CarPartDto> getCarPart(@PathVariable("id") Long id) { return ResponseEntity.ok( service.getCarPart(id)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCartPart(@PathVariable("id") Long id) {
        boolean check = service.deleteCarPart(id);
        if(check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No carpart found");
        }
    }


}
