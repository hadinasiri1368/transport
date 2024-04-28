package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.PlaqueDto;
import org.transport.model.Plaque;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PlaqueAPI {
    @Autowired
    private GenericService<Plaque> service;
    @PostMapping(path = "/api/plaque/add")
    public Long addPlaque(@RequestBody PlaqueDto plaqueDto , HttpServletRequest request) throws Exception{
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Plaque plaque =new Plaque();
        plaque.setId(plaqueDto.getId());
        plaque.setLeftPlaqueTag(plaqueDto.getLeftPlaqueTag());
        plaque.setPlaqueTagPersianPartId(plaqueDto.getPlaqueTagPersianPartId());
        plaque.setMiddlePlaqueTag(plaqueDto.getMiddlePlaqueTag());
        plaque.setRightPlaqueTag(plaqueDto.getRightPlaqueTag());
        plaque.setLeftPlaqueFreeZoneTag(plaqueDto.getLeftPlaqueFreeZoneTag());
        plaque.setRightPlaqueFreeZoneTag(plaqueDto.getRightPlaqueFreeZoneTag());
        service.insert(plaque, userId);
        return plaque.getId();
    }
    @PutMapping(path = "/api/plaque/edit")
    public Long editPlaque(@RequestBody PlaqueDto plaqueDto, HttpServletRequest request) throws Exception{
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Plaque plaque =new Plaque();
        plaque.setId(plaqueDto.getId());
        plaque.setLeftPlaqueTag(plaqueDto.getLeftPlaqueTag());
        plaque.setPlaqueTagPersianPartId(plaqueDto.getPlaqueTagPersianPartId());
        plaque.setMiddlePlaqueTag(plaqueDto.getMiddlePlaqueTag());
        plaque.setRightPlaqueTag(plaqueDto.getRightPlaqueTag());
        plaque.setLeftPlaqueFreeZoneTag(plaqueDto.getLeftPlaqueFreeZoneTag());
        plaque.setRightPlaqueFreeZoneTag(plaqueDto.getRightPlaqueFreeZoneTag());
        service.update(plaque, userId, Plaque.class);
        return plaque.getId();
    }

    @DeleteMapping(path = "/api/plaque/remove/{id}")
    public Long removePlaque(@PathVariable Long id) {
        service.delete(id, Plaque.class);
        return id;
    }

    @GetMapping(path = "/api/plaque/{id}")
    public Plaque getPlaque(@PathVariable Long id) {
        return service.findOne(Plaque.class, id);
    }

    @GetMapping(path = "/api/plaque")
    public List<Plaque> listPlaque() {
        return service.findAll(Plaque.class);
    }
}
