package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CarDto;
import nl.theijken.apkkeuringsation.dto.CarInputDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.Customer;
import nl.theijken.apkkeuringsation.repository.CarRepository;
import nl.theijken.apkkeuringsation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Car car = idtoToCar(carDto);
        Car savedCar = carRepository.save(car);
        return carToDto(savedCar);
    }

    public List<Car> getCars() {
//        List<Car> cars = carRepository.findAll();
        return carRepository.findAll();
    }

    public void assignCustomerToCar(String id, Long customerId) {
        Optional<Car> optionalCar = carRepository.findById(id);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCar.isPresent() && optionalCustomer.isPresent()) {
            Car car = optionalCar.get();
            Customer customer = optionalCustomer.get();
            car.setCustomer(customer);
            carRepository.save(car);
        } else {
            throw new RecordNotFoundException("Car or customer not found");
        }
    }

    private Car idtoToCar(CarInputDto carDto) {
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