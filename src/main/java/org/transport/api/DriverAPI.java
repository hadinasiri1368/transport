package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DriverDto;
import org.transport.model.Driver;
import org.transport.service.DriverSrvice;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class DriverAPI {

    private final DriverSrvice driverSrvice;

    public DriverAPI(DriverSrvice driverSrvice) {
        this.driverSrvice = driverSrvice;
    }

    @PostMapping(path = "/transport/driver/add")
    public Long addDriver(@RequestBody Driver driver, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        driverSrvice.insert(driver, userId);
        return driver.getId();
    }

    @PutMapping(path = "/transport/driver/edit")
    public Long editDriver(@RequestBody Driver driver, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        driverSrvice.update(driver, userId);
        return driver.getId();
    }

    @DeleteMapping(path = "/transport/driver/remove/{id}")
    public Long removeDriver(@PathVariable Long id) {
        return (long) driverSrvice.delete(id);
    }

    @GetMapping(path = "/transport/driver/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return driverSrvice.findOne(Driver.class, id);
    }

    @GetMapping(path = "/transport/driver")
    public Page<DriverDto> listDriver(HttpServletRequest request, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        String uuid = request.getHeader("X-UUID");
        String token = CommonUtils.getToken(request);
        return driverSrvice.findAll(page, size, uuid, token);
    }
}
