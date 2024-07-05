package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.ActionDto;
import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.dto.CarInputDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Action;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Customer;
import nl.theijken.apkkeuringsation.repository.CarRepository;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public CarService(
            CarRepository carRepository,
            CustomerRepository customerRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    public CarDto createCar(CarInputDto carDto) {
        Car car = dtoToCar(carDto);
        Car savedCar = carRepository.save(car);
        return carToDto(savedCar);
    }

    public List<CarDto> getCars() {
        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();
        for( Car car : cars ) {
            CarDto carDto = carToDto(car);
            carDtos.add(carDto);
        }
        return carDtos;
    }

    // GET ONE
    public CarDto getCar(String licensePlate) {
        Optional<Car> car = carRepository.findById(String.valueOf(licensePlate));
        if( car.isPresent() ){
            return carToDto(car.get());
        } else {
            throw new RecordNotFoundException("No car found");
        }
    }

    // DELETE
    public boolean deleteAction(String licensePlate) {
        if(carRepository.existsById(String.valueOf(licensePlate))) {
            carRepository.deleteById(String.valueOf(licensePlate));
            return true;
        }
        return false;
    }

    // PUT
    public CarDto updateCar(String licensePlate, CarInputDto carDto) {
        if(!carRepository.existsById(String.valueOf(licensePlate))) {
            throw new RecordNotFoundException("No car found");
        }
        Car storedCar = carRepository.findById(String.valueOf(licensePlate)).orElse(null);
        storedCar.setLicensePlate(carDto.licensePlate);
        storedCar.setBrand(carDto.brand);
        storedCar.setType(carDto.type);
        storedCar.setColor(carDto.color);
        storedCar.setAge(carDto.age);
        if(carDto.customerId != null){
            Optional<Customer> customer = customerRepository.findById(carDto.customerId);
            if(customer.isPresent()){
                storedCar.setCustomer(customer.get());
            }
        }
        return carToDto(carRepository.save(storedCar));
    }

    private Car dtoToCar(CarInputDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.licensePlate);
        car.setBrand(carDto.brand);
        car.setType(carDto.type);
        car.setColor(carDto.color);
        car.setAge(carDto.age);
        if(carDto.customerId != null){
            Optional<Customer> customer = customerRepository.findById(carDto.customerId);
            if(customer.isPresent()){
                car.setCustomer(customer.get());
            }
        }
        return car;
    }

    private Car dtoToCar(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.licensePlate);
        car.setBrand(carDto.brand);
        car.setType(carDto.type);
        car.setColor(carDto.color);
        car.setAge(carDto.age);

        return car;
    }

    private CarDto carToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.licensePlate = car.getLicensePlate();
        carDto.brand = car.getBrand();
        carDto.type = car.getType();
        carDto.color = car.getColor();
        carDto.age = car.getAge();
        carDto.customerFullName = car.getCustomer() != null ? car.getCustomer().getFirstName() + " " + car.getCustomer().getLastName() : null;
        return carDto;
    }

    public  Set<CarDto> carsToDtos(Set<Car> cars) {
        Set<CarDto> carDtos = new HashSet<>();
        for (Car car : cars) {

            carDtos.add(carToDto(car));
        }
        return carDtos;
    }

    public Set<Car> dtosToCars(Set<CarDto> carDtos) {
        Set<Car> carDtos1 = new HashSet<>();
        for (CarDto carDto : carDtos) {
            carDtos1.add(dtoToCar(carDto));
        }
        return carDtos1;
    }
}