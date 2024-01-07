package nl.theijken.apkkeuringsation.controller;

import nl.theijken.apkkeuringsation.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/carparts")
public class CarPartController {

/*
    private final CarPartService service;

   public CarPartController(CarPartService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<Integer> createCarPart(@RequestBody CarPartDto newCarPartDto) {
        int CarPartid = service.putCarPart(newCarPartDto);
        return new ResponseEntity<>(carPartid, HttpStatus.CREATED);
    }*/
}
