package org.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.transport.dto.CarGroupDto;
import org.transport.dto.LoadingTypeDto;
import org.transport.dto.ParametersDto;

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

}
