package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DriverDto;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class DriverAPI {
    @Autowired
    private GenericService<Driver> service;

    @PostMapping(path = "/api/driver/add")
    public Long addDriver(@RequestBody DriverDto driverDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Driver driver = new Driver();
        driver.setId(driverDto.getId());
        driver.setTrackingCode(driverDto.getTrackingCode());
        Person person = new Person();
        person.setId(driverDto.getPersonId());
        driver.setPerson(person);
        driver.setDriverLicenseTypeId(driverDto.getDriverLicenseTypeId());
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
