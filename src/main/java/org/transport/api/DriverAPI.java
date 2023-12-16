package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transport.common.CommonUtils;
import org.transport.dto.DriverDto;
import org.transport.dto.DriverLicenseTypeDto;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.service.GenericService;

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
}
