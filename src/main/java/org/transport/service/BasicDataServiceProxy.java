package org.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import org.transport.dto.CarCapacityDto;
import org.transport.dto.LoadingTypeDto;
import org.transport.dto.ParametersDto;
import org.transport.dto.CarGroupDto;

import java.util.List;

@FeignClient("BASICDATA")
public interface BasicDataServiceProxy {
    @GetMapping(path = "/basicData/carGroupValue")
    CarGroupDto carGroupValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                              @RequestParam Long carTypeId, @RequestParam Long carCapacityId, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/loadingTypeValue")
    LoadingTypeDto loadingTypeValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                                    @RequestParam Long code, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/paramCodeValue")
    ParametersDto parametersValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                                  @RequestParam String paramCode, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/carCapacity")
    Page<CarCapacityDto> listCarCapacity(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

}
