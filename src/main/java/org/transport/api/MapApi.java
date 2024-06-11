package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.transport.dto.Response.NeshanElementDto;
import org.transport.model.Car;
import org.transport.service.GenericService;
import org.transport.service.NeshanMapServiceImpl;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class MapApi {
    @Autowired
    private NeshanMapServiceImpl neshanMapService;

    @GetMapping(path = "/transport/map/getAddress")
    public String getAddress(@RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude) throws Exception {
        return neshanMapService.getAddress(latitude, longitude);
    }

    @GetMapping(path = "/transport/map/getDistance")
    public NeshanElementDto getAddress(@RequestParam(value = "fromLatitude") double fromLatitude
            , @RequestParam(value = "fromLongitude") double fromLongitude
            , @RequestParam(value = "toLatitude") double toLatitude
            , @RequestParam(value = "toLongitude") double toLongitude
            , @RequestParam(value = "withTraffic") Boolean withTraffic) throws Exception {
        if (withTraffic)
            return neshanMapService.getDistanceWithTraffic(fromLatitude, fromLongitude, toLatitude, toLongitude);
        return neshanMapService.getDistanceWithoutTraffic(fromLatitude, fromLongitude, toLatitude, toLongitude);
    }
}
