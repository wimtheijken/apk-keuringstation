package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepos;

    public CarService(CarRepository repos) {
        this.carRepos = repos;
    }
}
