package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transport.common.CommonUtils;
import org.transport.dto.CarDto;
import org.transport.model.Car;
import org.transport.model.Person;
import org.transport.model.Plaque;
import org.transport.service.GenericService;

@RestController
public class CarAPI {
    @Autowired
    private GenericService<Car> service;

    @PostMapping(path = "/api/car/add")
    public Long addPerson(@RequestBody CarDto carDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Car car = new Car();
        Plaque plaque=new Plaque();
        Person person=new Person();
        car.setId(carDto.getId());
        person.setId(carDto.getOwnerId());
        car.setPerson(person);
        car.setVIN(car.getVIN());
        car.setChassieNumber(carDto.getChassieNumber());
        car.setEngineNumber(carDto.getEngineNumber());
        car.setPIN(car.getPIN());
        car.setPAN(car.getPAN());
        car.setTrackingCode(car.getTrackingCode());
        plaque.setId(carDto.getId());
        car.setPlaque(plaque);
        Long fuelTypeId = CommonUtils.getFuelTypeId(CommonUtils.getToken(request));
        Long carGroupId = CommonUtils.getCarGroupId(CommonUtils.getToken(request));
        Long carCapacityId = CommonUtils.getCarCapacityId(CommonUtils.getToken(request),1L);
        Long fleetTypeId = CommonUtils.getFleetTypeId(CommonUtils.getToken(request));
        carDto.setFuelTypeId(fuelTypeId);
        carDto.setCarGroupId(carGroupId);
        carDto.setCarCapacityId(carCapacityId);
        carDto.setFleetTypeId(fleetTypeId);

        service.insert(car, userId);
        return car.getId();
    }
}
