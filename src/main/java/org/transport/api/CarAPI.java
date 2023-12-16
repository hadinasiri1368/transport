package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transport.common.CommonUtils;
import org.transport.dto.*;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.model.Plaque;
import org.transport.service.GenericService;

@RestController
public class CarAPI {
    @Autowired
    private GenericService<Car> service;

    @PostMapping(path = "/api/car/add")
    public Long addCar(@RequestBody CarDto carDto, FleetTypeDto fleetTypeDto, FuelTypeDto fuelTypeDto,
                       CarGroupDto carGroupDto, CarCapacityDto carCapacityDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Car car = new Car();
        Plaque plaque = new Plaque();
        Person person = new Person();
        Driver driver = new Driver();
        car.setId(carDto.getId());
        person.setId(carDto.getOwnerId());
        car.setPerson(person);
        driver.setId(carDto.getDriverId());
        car.setDriver(driver);
        fuelTypeDto.setId(carDto.getFuelTypeId());
        car.setFuelTypeId(fuelTypeDto.getId());
        carGroupDto.setId(carDto.getCarGroupId());
        car.setCarGroupId(carGroupDto.getId());
        carCapacityDto.setId(carDto.getCarCapacityId());
        car.setCarCapacityId(carCapacityDto.getId());
        plaque.setId(carDto.getId());
        car.setPlaque(plaque);
        fleetTypeDto.setId(carDto.getFleetTypeId());
        car.setFleetTypeId(fleetTypeDto.getId());
        car.setVIN(carDto.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(car.getPIN());
        car.setPAN(car.getPAN());
        car.setTrackingCode(car.getTrackingCode());
        service.insert(car, userId);
        return car.getId();
    }
}
