package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CarPartService {

    private final CarPartRepository carPartRepository;

    public CarPartService(CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
    }

    public CarPartDto createCarPart(CarPartDto carPartDto) {
        CarPart carPart = new CarPart();
        carPart.setName(carPartDto.name);
        carPart.setPrice(carPartDto.price);
//        carPart.setAction(carPartDto.action);
        repos.save(carPart);
        carPartDto.id = carPart.getId();
        return carPartDto;
    }

    public List<CarPartDto> getCarParts() {
        List<CarPart> carParts = carPartRepository.findAll();
        List<CarPartDto> carPartDtos = new ArrayList<>();

        for(CarPart carPart : carParts) {
            CarPartDto carPartDto = new CarPartDto();
            carPartDto.id = carPart.getId();
            carPartDto.name = carPart.getName();
            carPartDto.price = carPart.getPrice();
//            carPartDto.actions = repos.carPartDto(carPart.getActions());
            carPartDtos.add(carPartDto);
        }
        return carPartDtos;
    }

    private CarPart dtoToCarPart(CarPartDto carPartDto) {
        CarPart carPart = new CarPart();
        carPart.setName(carPartDto.name);
        carPart.setPrice(carPartDto.price);
        carPart.setActions(carPartDto.actions);
        carPart = carPartRepository.save(carPart); // Save customer first
        carPartDto.id = carPart.getId(); // Then set the ID in DTO

        return carPart;
    }
}
