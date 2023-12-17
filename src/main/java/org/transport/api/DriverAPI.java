package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DriverDto;
import org.transport.dto.DriverLicenseTypeDto;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.service.GenericService;

import java.util.List;

@RestController
public class DriverAPI {
    @Autowired
    private GenericService<Driver> service;

    @PostMapping(path = "/api/driver/add")
    public Long addDriver(@RequestBody DriverDto driverDto,DriverLicenseTypeDto driverLicenseTypeDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Driver driver = new Driver();
        driver.setId(driverDto.getId());
        driver.setTrackingCode(driverDto.getTrackingCode());
        Person person = new Person();
        person.setId(driverDto.getPersonId());
        driver.setPerson(person);
        driverLicenseTypeDto.setId(driverDto.getDriverLicenseTypeId());
        driver.setDriverLicenseTypeId(driverLicenseTypeDto.getId());
        driver.setDriverLicenseIssueDate(driverDto.getDriverLicenseIssueDate());
        driver.setDriverLicenseValidDuration(driverDto.getDriverLicenseValidDuration());
        service.insert(driver, userId);
        return driver.getId();
    }
    @PostMapping(path = "/api/driver/edit")
    public Long editDriver(@RequestBody Driver driver, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(driver, userId);
        return driver.getId();
    }

    @PostMapping(path = "/api/driver/remove/{id}")
    public Long removeDriver(@PathVariable Long id) {
        service.delete(id, Driver.class);
        return id;
    }

    @GetMapping(path = "/api/driver/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return service.findOne(Driver.class, id);
    }

    @GetMapping(path = "/api/driver")
    public List<Driver> listDriver() {
        return service.findAll(Driver.class);
    }
}
