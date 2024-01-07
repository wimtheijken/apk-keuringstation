package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.repository.CarPartRepository;
import nl.theijken.apkkeuringsation.model.CarPart;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarPartService {

    private final CarPartRepository carPartRepos;

    public CarPartService(CarPartRepository repos) {
        this.carPartRepos = repos;
    }

/*
    public OrderService(CarPartRepository repos) {
        this.carPartRepos = repos;
    }
    public int putCarPart(CarPartDto newOrderDto) {
        CarPart o = new CarPart(newOrderDto.productname, newCarPartDto.unitprice, newCarPartDto.quantity);

        carPartRepos.save(o);

        return o.getCarPartid();
    }
    public CarPartDto getOrder(int orderid) {
        Optional<CarPart> oo = carPartRepos.findById(carPartid);
        if (oo.isPresent()) {
            CarPart o = oo.get();
            CarPartDto odto = new CarPartDto();
            odto.orderid = o.getCarPartid();
            odto.productname = o.getProductname();
            odto.unitprice = o.getUnitprice();
            odto.quantity = o.getQuantity();

            return odto;
        }
        return null;
    }

    public double getAmount(int carPartid) {
        Optional<CarPart> oo = carPartRepos.findById(orderid);
        if (oo.isPresent()) {
            CarPart o = oo.get();
            return o.calculateAmount();
        }
        return -1;
    }*/
}
