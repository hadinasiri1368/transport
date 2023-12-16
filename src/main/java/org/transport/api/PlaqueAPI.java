package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transport.common.CommonUtils;
import org.transport.dto.PlaqueDto;
import org.transport.dto.PlaqueTagPersianPartDto;
import org.transport.model.Plaque;
import org.transport.service.GenericService;

@RestController
public class PlaqueAPI {
    @Autowired
    private GenericService<Plaque> service;
    @PostMapping(path = "/api/plaque/add")
    public Long addPlaque(@RequestBody PlaqueDto plaqueDto , PlaqueTagPersianPartDto plaqueTagPersianPartDto, HttpServletRequest request){
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Plaque plaque =new Plaque();
        plaque.setId(plaqueDto.getId());
        plaque.setLeftPlaqueTag(plaqueDto.getLeftPlaqueTag());
        plaqueTagPersianPartDto.setId(plaqueDto.getPlaqueTagPersianPartId());
        plaque.setPlaqueTagPersianPartId(plaqueTagPersianPartDto.getId());
        plaque.setMiddlePlaqueTag(plaqueDto.getMiddlePlaqueTag());
        plaque.setRightPlaqueTag(plaqueDto.getRightPlaqueTag());
        plaque.setLeftPlaqueFreeZoneTag(plaqueDto.getLeftPlaqueFreeZoneTag());
        plaque.setRightPlaqueFreeZoneTag(plaqueDto.getRightPlaqueFreeZoneTag());
        service.insert(plaque, userId);
        return plaque.getId();
    }
}
