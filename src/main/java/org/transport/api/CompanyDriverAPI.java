package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.*;
import org.transport.model.*;
import org.transport.service.CompanyDriverService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CompanyDriverAPI {
    @Autowired
    private CompanyDriverService companyDriverService;

    @PostMapping(path = "/transport/companyDriver/add")
    public Long addCompanyDriver(@RequestBody CompanyDriverDto companyDriverDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        CompanyDriver companyDriver = new CompanyDriver();
        Person person = new Person();
        Driver driver = new Driver();
        companyDriver.setId(companyDriverDto.getId());
        person.setId(companyDriverDto.getCompanyId());
        companyDriver.setCompany(person);
        driver.setId(companyDriverDto.getDriverId());
        companyDriver.setDriver(driver);
        companyDriverService.insert(companyDriver, userId, CommonUtils.getToken(request), uuid);
        return companyDriver.getId();
    }

    @PutMapping(path = "/transport/companyDriver/edit")
    public Long editCompanyDriver(@RequestBody CompanyDriverDto companyDriverDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        CompanyDriver companyDriver = new CompanyDriver();
        Person person = new Person();
        Driver driver = new Driver();
        companyDriver.setId(companyDriverDto.getId());
        person.setId(companyDriverDto.getCompanyId());
        companyDriver.setCompany(person);
        driver.setId(companyDriverDto.getDriverId());
        companyDriver.setDriver(driver);
        companyDriverService.update(companyDriver, userId, CommonUtils.getToken(request), uuid);
        return companyDriver.getId();
    }

    @DeleteMapping(path = "/transport/companyDriver/remove/{id}")
    public Long removeCompanyDriver(@PathVariable Long id) {
        return (long) companyDriverService.delete(id);
    }

    @GetMapping(path = "/transport/companyDriver/{id}")
    public CompanyDriver getCompanyDriver(@PathVariable Long id) {
        return companyDriverService.findOne(id);
    }

    @GetMapping(path = "/transport/companyDriver")
    public List<CompanyDriver> listCompanyDriver() {
        return companyDriverService.findAll(CompanyDriver.class);
    }

    @PostMapping(path = "/transport/companyDriver/changeRequest")
    public ChangeRequestDto changeRequestCompanyDriver(@RequestBody ChangeRequestDto changeRequestDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        companyDriverService.changeRequestDriver(CommonUtils.getToken(request), changeRequestDto, uuid);
        return changeRequestDto;
    }

}
