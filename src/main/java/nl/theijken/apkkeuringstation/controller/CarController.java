package nl.theijken.apkkeuringstation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringstation.dto.CarDto;
import nl.theijken.apkkeuringstation.dto.CarInputDto;
import nl.theijken.apkkeuringstation.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarInputDto carDto, BindingResult br) {

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
            CarDto car1 = service.createCar(carDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + car1.licensePlate).toUriString());

            return ResponseEntity.created(uri).body(car1);
        }
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("licensePlate") String licensePlate, @RequestBody CarInputDto carDto) {
        return ResponseEntity.ok(service.updateCar(licensePlate, carDto));
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars(){
        return ResponseEntity.ok(service.getCars());
    }


    @GetMapping("/{licensePlate}")
    public ResponseEntity<CarDto> getCar(@PathVariable("licensePlate") String licensePlate) {
        return ResponseEntity.ok(service.getCar(licensePlate));
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Object> deleteAction(@PathVariable("licensePlate") String licensePlate) {
        boolean check = service.deleteAction(licensePlate);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No car found");
        }
    }
}