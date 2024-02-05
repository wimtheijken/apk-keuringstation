package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.repository.CarRepository;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public CarService(
            CarRepository carRepository,
            CustomerRepository customerRepository, CustomerService customerService) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    public CarDto createCar(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.licensePlate);
        car.setBrand(carDto.brand);
        car.setType(carDto.type);
        car.setColor(carDto.color);
        car.setAge(carDto.age);
        car.setCustomer(carDto.customer);
        carRepository.save(car);

        return carDto;
    }

    public List<CarDto> GetCar() {
        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();

        for(Car car : cars) {
            CarDto carDto = new CarDto();
            carDto.licensePlate = car.getLicensePlate();
            carDto.brand = car.getBrand();
            carDto.type = car.getType();
            carDto.color = car.getColor();
            carDto.age = car.getAge();
            carDto.customer = car.getCustomer();
            carDtos.add(carDto);
        }
        return carDtos;
    }

    public void assignCustomerToCar(Long id, Long customerId) {
        var optionalCar = carRepository.findById(id);
        var optionalCustomer = customerRepository.findById(String.valueOf(customerId));

        if(optionalCar.isPresent() && optionalCustomer.isPresent()) {
            var car = optionalCar.get();
            var customer = optionalCustomer.get();

            car.setCustomer(customer);
            carRepository.save(car);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
