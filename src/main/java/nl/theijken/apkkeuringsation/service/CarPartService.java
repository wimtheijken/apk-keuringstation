package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        carPartRepository.save(carPart);
        carPartDto.id = carPart.getId();
        return carPartDto;
    }

    public List<CarPartDto> getCarParts() {
        List<CarPart> carParts = carPartRepository.findAll();
        List<CarPartDto> carPartDtos = new ArrayList<>();

        for (CarPart carPart : carParts) {
            CarPartDto carPartDto = carParsToDtos(carPart);
            carPartDtos.add(carPartDto);
        }
            return carPartDtos;

    }

    private CarPartDto carParsToDtos(CarPart carPart) {
        CarPartDto carPartDto = new CarPartDto();
        carPartDto.id = carPart.getId();
        carPartDto.name = carPart.getName();
        carPartDto.price = carPart.getPrice();
//        carPartDto.actions = ActionService.actionsToDtos(carPart.getActions());
        return carPartDto;
    }
//        public CarPart dtoToCarPart(Set<CarPartDto> carPartDto){
////        CarPart carPart = new CarPart();
////        carPart.setName(carPartDto.name);
////        carPart.setPrice(carPartDto.price);
////        carPart.setActions(carPartDto.actions);
////        carPart = carPartRepository.save(carPart); // Save customer first
////        carPartDto.id = carPart.getId(); // Then set the ID in DTO
////
////        return carPart;
//            return null;
//        }

//        public CarPartDto carPartToDto (Set<CarPart> carPart){
////        CarPartDto carPart = new CarPartDto();
////        carPartDto.id = carPart.getId();
////        carPartDto.name = carPart.getName();
////        carPartDto.price = carPart.getPrice();
////        carPartDto.actions = carPart.getActions();
////        return carPart;
//            return null;
//        }

//        public boolean exists(CarPartDto cartPart){
//            return carPartRepository.existsByName(cartPart.name);
//        }
//
//        public CarPart getCarPart(CarPartDto carPart){
//            return carPartRepository.findByName(carPart.name);
//        }
    }