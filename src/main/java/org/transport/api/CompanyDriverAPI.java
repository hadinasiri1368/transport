package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.*;
import org.transport.model.*;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CompanyDriverAPI {
    @Autowired
    private GenericService<CompanyDriver> service;

    @PostMapping(path = "/api/companyDriver/add")
    public Long addCompanyDriver(@RequestBody CompanyDriverDto companyDriverDto, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        CompanyDriver companyDriver = new CompanyDriver();
        Person person = new Person();
        Driver driver = new Driver();
        companyDriver.setId(companyDriverDto.getId());
        person.setId(companyDriverDto.getCompanyId());
        companyDriver.setCompany(person);
        driver.setId(companyDriverDto.getDriverId());
        companyDriver.setDriver(driver);
        companyDriver.setRequestStatusId(companyDriverDto.getRequestStatusId());
        service.insert(companyDriver, userId);
        return companyDriver.getId();
    }

    @PutMapping(path = "/api/companyDriver/edit")
    public Long editCompanyDriver(@RequestBody CompanyDriverDto companyDriverDto, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        CompanyDriver companyDriver = new CompanyDriver();
        Person person = new Person();
        Driver driver = new Driver();
        companyDriver.setId(companyDriverDto.getId());
        person.setId(companyDriverDto.getCompanyId());
        companyDriver.setCompany(person);
        driver.setId(companyDriverDto.getDriverId());
        companyDriver.setDriver(driver);
        companyDriver.setRequestStatusId(companyDriverDto.getRequestStatusId());
        service.update(companyDriver, userId, CompanyDriver.class);
        return companyDriver.getId();
    }

    @DeleteMapping(path = "/api/companyDriver/remove/{id}")
    public Long removeCompanyDriver(@PathVariable Long id) {
        service.delete(id, CompanyDriver.class);
        return id;
    }

    @GetMapping(path = "/api/companyDriver/{id}")
    public CompanyDriver getCompanyDriver(@PathVariable Long id) {
        return service.findOne(CompanyDriver.class, id);
    }

    @GetMapping(path = "/api/companyDriver")
    public List<CompanyDriver> listCompanyDriver() {
        return service.findAll(CompanyDriver.class);
    }

}
