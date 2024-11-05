package org.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import org.transport.dto.CarCapacityDto;
import org.transport.dto.LoadingTypeCompanyDto;
import org.transport.dto.ParametersDto;
import org.transport.dto.CarGroupDto;
import org.transport.dto.Response.*;

@FeignClient("BASICDATA")
public interface BasicDataServiceProxy {
    @GetMapping(path = "/basicData/carGroupValue")
    CarGroupDto carGroupValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                              @RequestParam Long carTypeId, @RequestParam Long carCapacityId, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/loadingTypeCompanyValue")
    LoadingTypeCompanyDto listLoadingTypeValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                                           @RequestParam Long loadingTypeId, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/paramCodeValue")
    ParametersDto parametersValue(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid,
                                  @RequestParam String paramCode, @RequestParam Long companyId);

    @GetMapping(path = "/basicData/carCapacity")
    Page<CarCapacityDto> listCarCapacity(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/orderStatus")
    Page<OrderStatusDto> listOrderStatus(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/carType")
    Page<CarTypeDto> listCarType(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/baseInfoGood")
    Page<BaseInfoGoodDto> listBaseInfoGood(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/packingType")
    Page<PackingTypeDto> listPackingType(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/loadingType")
    Page<LoadingTypeDto> listLoadingType(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/basicData/plaqueTagPersianPart")
    Page<PlaqueTagPersianPartDto> listPlaqueTagPersianPart (@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

}
