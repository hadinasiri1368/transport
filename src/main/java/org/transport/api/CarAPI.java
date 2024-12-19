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
import org.transport.service.CarService;
import org.transport.service.GenericService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CarAPI {

    private final CarService service;

    public CarAPI(CarService service) {
        this.service = service;
    }

    //    @Operation(summary = "add car", description = "اضافه کردن ماشین")
    @PostMapping(path = "/transport/car/add")
    public Long addCar(@RequestBody Car car, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.insert(car, userId);
        return car.getId();
    }

    @PutMapping(path = "/transport/car/edit")
    public Long editCar(@RequestBody Car car, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.update(car, userId);
        return car.getId();
    }

    @DeleteMapping(path = "/transport/car/remove/{id}")
    public Long removeCar(@PathVariable Long id) throws Exception {
        return (long) service.delete(id);
    }

    @GetMapping(path = "/transport/car/{id}")
    public Car getCar(@PathVariable Long id) throws Exception {
        return service.findOne(Car.class , id);
    }

    @GetMapping(path = "/transport/car")
    public Page<CarDto> listCar(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws Exception {
        String uuid = request.getHeader("X-UUID");
        String token = CommonUtils.getToken(request);
        return service.findAll(size, page, token, uuid);
    }

}
