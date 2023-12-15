package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transport.common.CommonUtils;
import org.transport.dto.CarDto;
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
    public Long addCar(@RequestBody CarDto carDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Car car = new Car();
        Plaque plaque=new Plaque();
        Person person=new Person();
        Driver driver=new Driver();
        car.setId(carDto.getId());
        person.setId(carDto.getOwnerId());
        car.setPerson(person);
        driver.setId(carDto.getDriverId());
        car.setDriver(driver);
        car.setVIN(carDto.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(car.getPIN());
        car.setPAN(car.getPAN());
        car.setTrackingCode(car.getTrackingCode());
        plaque.setId(carDto.getId());
        car.setPlaque(plaque);
        Long fuelTypeId = CommonUtils.getFuelTypeId(CommonUtils.getToken(request),carDto.getFuelTypeId());
        Long carGroupId = CommonUtils.getCarGroupId(CommonUtils.getToken(request),carDto.getCarGroupId());
        Long carCapacityId = CommonUtils.getCarCapacityId(CommonUtils.getToken(request),carDto.getCarCapacityId());
        Long fleetTypeId = CommonUtils.getFleetTypeId(CommonUtils.getToken(request),carDto.getFleetTypeId());
        carDto.setFuelTypeId(fuelTypeId);
        carDto.setCarGroupId(carGroupId);
        carDto.setCarCapacityId(carCapacityId);
        carDto.setFleetTypeId(fleetTypeId);

        service.insert(car, userId);
        return car.getId();
    }
}
