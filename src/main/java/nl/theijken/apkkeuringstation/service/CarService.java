package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.CarDto;
import nl.theijken.apkkeuringstation.dto.CarInputDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.*;
import nl.theijken.apkkeuringstation.repository.CarRepository;
import nl.theijken.apkkeuringstation.repository.CustomerRepository;
import nl.theijken.apkkeuringstation.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public CarService(
            CarRepository carRepository,
            CustomerRepository customerRepository,
            TicketRepository ticketRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    public CarDto createCar(CarInputDto carDto) {
        Car car = inputDtoToCar(carDto);
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
        assert storedCar != null;
        storedCar.setLicensePlate(carDto.licensePlate);
        storedCar.setBrand(carDto.brand);
        storedCar.setType(carDto.type);
        storedCar.setColor(carDto.color);
        storedCar.setAge(carDto.age);
        if(carDto.customerId != null){
            Optional<Customer> customer = customerRepository.findById(carDto.customerId);
            customer.ifPresent(storedCar::setCustomer);
        }
        return carToDto(carRepository.save(storedCar));
    }

    //PUT Ticket -> Car
//    public CarDto assignTicketToCar(String licensePlate, Long ticketId) {
//        if(!carRepository.existsById(String.valueOf(licensePlate))) {
//            throw new RecordNotFoundException("No car found");
//        }
//        Car storedCar = carRepository.findById(String.valueOf(licensePlate)).orElse(null);
//        if(!ticketRepository.existsById(ticketId)) {
//            throw new RecordNotFoundException("No ticket found");
//        }
//        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
//        assert storedCar != null;
//        if (storedCar.getTickets() == null) {
//            Set<Ticket> tickets = new HashSet<>();
//            tickets.add(ticket);
//            storedCar.setTickets(tickets);
//        } else {
//            Set<Ticket> tickets = storedCar.getTickets();
//            tickets.add(ticket);
//            storedCar.setTickets(tickets);
//        }
//        assert ticket != null;
//        ticket.setCar(storedCar);
//        ticketRepository.save(ticket);
//        return carToDto(carRepository.save(storedCar));
//    }

    private Car inputDtoToCar(CarInputDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.licensePlate);
        car.setBrand(carDto.brand);
        car.setType(carDto.type);
        car.setColor(carDto.color);
        car.setAge(carDto.age);
        List<Car> cars = carRepository.findAll();
        for (Car car2 : cars){
            if(Objects.equals(car2.getLicensePlate(), carDto.licensePlate)) {
                throw new RecordNotFoundException("Car is already in use");
            }
        }
        if(carDto.customerId != null){
            Optional<Customer> customer = customerRepository.findById(carDto.customerId);
            customer.ifPresent(car::setCustomer);
        }
        if (carDto.tickets == null) {
            carDto.tickets= new HashSet<>();
        } else {
            Set<Ticket> tickets = new HashSet<>();
            car.setTickets(tickets);
        }
        return car;
    }

    public Car dtoToCar(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.licensePlate);
        car.setBrand(carDto.brand);
        car.setType(carDto.type);
        car.setColor(carDto.color);
        car.setAge(carDto.age);
        if (carDto.tickets == null) {
            carDto.tickets= new HashSet<>();
        } else {
            Set<Ticket> tickets = new HashSet<>();
            car.setTickets(tickets);
        }
        return car;
    }

    public CarDto carToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.licensePlate = car.getLicensePlate();
        carDto.brand = car.getBrand();
        carDto.type = car.getType();
        carDto.color = car.getColor();
        carDto.age = car.getAge();
        carDto.customerFullName = car.getCustomer() != null ? car.getCustomer().getFirstName() + " " + car.getCustomer().getLastName() : null;
        carDto.tickets = new HashSet<>();
        if (car.getTickets() != null) {
            for (Ticket ticket : car.getTickets()) {
                carDto.tickets.add(ticket.getId());
            }
        }
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
        Set<Car> cars = new HashSet<>();
        for (CarDto carDto : carDtos) {
            cars.add(dtoToCar(carDto));
        }
        return cars;
    }
}