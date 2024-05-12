package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DriverDto;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.service.GenericService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class DriverAPI {
    @Autowired
    private GenericService<Driver> service;

    @PostMapping(path = "/api/driver/add")
    public Long addDriver(@RequestBody DriverDto driverDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
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

    @PutMapping(path = "/api/driver/edit")
    public Long editDriver(@RequestBody DriverDto driverDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        Driver driver = new Driver();
        driver.setId(driverDto.getId());
        driver.setTrackingCode(driverDto.getTrackingCode());
        Person person = new Person();
        person.setId(driverDto.getPersonId());
        driver.setPerson(person);
        driver.setDriverLicenseTypeId(driverDto.getDriverLicenseTypeId());
        driver.setDriverLicenseIssueDate(driverDto.getDriverLicenseIssueDate());
        driver.setDriverLicenseValidDuration(driverDto.getDriverLicenseValidDuration());
        service.update(driver, userId, Driver.class);
        return driver.getId();
    }

    @DeleteMapping(path = "/api/driver/remove/{id}")
    public Long removeDriver(@PathVariable Long id) {
        service.delete(id, Driver.class);
        return id;
    }

    @GetMapping(path = "/api/driver/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return service.findOne(Driver.class, id);
    }

    @GetMapping(path = "/api/driver")
    public Page<Driver> listDriver(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(Driver.class, page, size);
    }
}
