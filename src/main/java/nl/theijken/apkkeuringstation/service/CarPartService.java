package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.CarPartDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.Action;
import nl.theijken.apkkeuringstation.model.CarPart;
import nl.theijken.apkkeuringstation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarPartService {

    private final CarPartRepository carPartRepository;

    public CarPartService(CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
    }

    public CarPartDto createCarPart(CarPartDto carPartDto) {
        CarPart carPart = dtoToCarPart(carPartDto);
        CarPart savedCarPart = carPartRepository.save(carPart);
        return carPartToDto(savedCarPart);
    }

    public List<CarPartDto> getCarParts() {
        List<CarPart> carParts = carPartRepository.findAll();
        List<CarPartDto> carPartDtos = new ArrayList<>();

        for (CarPart carPart : carParts) {
            CarPartDto carPartDto = carPartToDto(carPart);
            carPartDtos.add(carPartDto);
        }
        return carPartDtos;
    }

    // GET ONE
    public CarPartDto getCarPart(Long id) {
        Optional<CarPart> carPart = carPartRepository.findById(String.valueOf(id));
        if( carPart.isPresent() ){
            return carPartToDto(carPart.get());
        } else {
            throw new RecordNotFoundException("No action found");
        }
    }

    // DELETE
    public boolean deleteCarPart(Long id) {
        if(carPartRepository.existsById(String.valueOf(id))) {
            carPartRepository.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }

    // PUT
    public CarPartDto updateCarPart(Long id, CarPartDto carPartDto) {
        if (!carPartRepository.existsById(String.valueOf(id))) {
            throw new RecordNotFoundException("No carpart found");
        }
        CarPart storedCarPart = carPartRepository.findById(String.valueOf(id)).orElse(null);
        assert storedCarPart != null;
        storedCarPart.setId(carPartDto.id);
        storedCarPart.setName(carPartDto.name);
        storedCarPart.setPrice(carPartDto.price);
        if (carPartDto.actions == null) {
            carPartDto.actions = new HashSet<>();
        } else {
            Set<Action> actions = new HashSet<>();
            storedCarPart.setActions(actions);
        }
        return carPartToDto(carPartRepository.save(storedCarPart));
    }

    // DTO -> MODEL
    private CarPart dtoToCarPart(CarPartDto carPartDto) {
        CarPart carPart = new CarPart();
        carPart.setName(carPartDto.name);
        carPart.setPrice(carPartDto.price);
        if (carPartDto.actions == null) {
            carPartDto.actions = new HashSet<>();
        } else {
            Set<Action> actions = new HashSet<>();
            carPart.setActions(actions);
        }
        return carPart;
    }

    // MODEL -> DTO
    public CarPartDto carPartToDto(CarPart carPart) {
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.id = carPart.getId();
        carPartDto.name = carPart.getName();
        carPartDto.price = carPart.getPrice();
        return carPartDto;
    }
}