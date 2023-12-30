package org.transport.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.*;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.model.Plaque;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CarAPI {
    @Autowired
    private GenericService<Car> service;
//    @Operation(summary = "add car", description = "اضافه کردن ماشین")
    @PostMapping(path = "/api/car/add")
    public Long addCar(@RequestBody CarDto carDto, HttpServletRequest request) {
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
        car.setFuelTypeId(carDto.getFuelTypeId());
        car.setCarGroupId(carDto.getCarGroupId());
        car.setCarCapacityId(carDto.getCarCapacityId());
        plaque.setId(carDto.getId());
        car.setPlaque(plaque);
        car.setFleetTypeId(carDto.getFleetTypeId());
        car.setVIN(carDto.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(car.getPIN());
        car.setPAN(car.getPAN());
        car.setTrackingCode(car.getTrackingCode());
        service.insert(car, userId);
        return car.getId();
    }

    @PostMapping(path = "/api/car/edit")
    public Long editCar(@RequestBody Car car, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(car, userId);
        return car.getId();
    }

    @PostMapping(path = "/api/car/remove/{id}")
    public Long removeCar(@PathVariable Long id) {
        service.delete(id, Car.class);
        return id;
    }

    @GetMapping(path = "/api/car/{id}")
    public Car getCar(@PathVariable Long id) {
        return service.findOne(Car.class, id);
    }

    @GetMapping(path = "/api/car")
    public List<Car> listCar() {
        return service.findAll(Car.class);
    }

}
