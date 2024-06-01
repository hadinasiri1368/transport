package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.PlaqueDto;
import org.transport.model.Plaque;
import org.transport.service.GenericService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PlaqueAPI {
    @Autowired
    private GenericService<Plaque> service;

    @PostMapping(path = "/transport/plaque/add")
    public Long addPlaque(@RequestBody PlaqueDto plaqueDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        Plaque plaque = new Plaque();
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

    @PutMapping(path = "/transport/plaque/edit")
    public Long editPlaque(@RequestBody PlaqueDto plaqueDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        Plaque plaque = new Plaque();
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

    @DeleteMapping(path = "/transport/plaque/remove/{id}")
    public Long removePlaque(@PathVariable Long id) {
        service.delete(id, Plaque.class);
        return id;
    }

    @GetMapping(path = "/transport/plaque/{id}")
    public Plaque getPlaque(@PathVariable Long id) {
        return service.findOne(Plaque.class, id);
    }

    @GetMapping(path = "/transport/plaque")
    public Page<Plaque> listPlaque(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(Plaque.class, size, page);
    }
}
