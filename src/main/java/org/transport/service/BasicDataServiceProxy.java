package org.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.transport.dto.ParametersDto;

import java.util.List;

@FeignClient("BASICDATA")
public interface BasicDataServiceProxy {
    @GetMapping(path = "/basicData/parameters")
    List<ParametersDto> listParameters(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

}
