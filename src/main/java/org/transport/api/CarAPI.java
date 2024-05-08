package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.*;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.model.Plaque;
import org.transport.service.AuthenticationServiceProxy;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CarAPI {
    @Autowired
    private GenericService<Car> service;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;

    //    @Operation(summary = "add car", description = "اضافه کردن ماشین")
    @PostMapping(path = "/api/car/add")
    public Long addCar(@RequestBody CarDto carDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request),uuid));
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
        plaque.setId(carDto.getPlaqueId());
        car.setPlaque(plaque);
        car.setFleetTypeId(carDto.getFleetTypeId());
        car.setVIN(carDto.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(carDto.getPIN());
        car.setPAN(carDto.getPAN());
        car.setTrackingCode(carDto.getTrackingCode());
        service.insert(car, userId);
        return car.getId();
    }

    @PutMapping(path = "/api/car/edit")
    public Long editCar(@RequestBody CarDto carDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request),uuid));
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
        plaque.setId(carDto.getPlaqueId());
        car.setPlaque(plaque);
        car.setFleetTypeId(carDto.getFleetTypeId());
        car.setVIN(carDto.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(carDto.getPIN());
        car.setPAN(carDto.getPAN());
        car.setTrackingCode(carDto.getTrackingCode());
        service.update(car, userId, Car.class);
        return car.getId();
    }

    @DeleteMapping(path = "/api/car/remove/{id}")
    public Long removeCar(@PathVariable Long id) throws Exception {
        service.delete(id, Car.class);
        return id;
    }

    @GetMapping(path = "/api/car/{id}")
    public Car getCar(@PathVariable Long id) throws Exception {
        return service.findOne(Car.class, id);
    }

    @GetMapping(path = "/api/car")
    public Page<Car> listCar(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws Exception {
        return service.findAll(Car.class,page,size);
    }

}
