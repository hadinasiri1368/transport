package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.PlaqueDto;
import org.transport.model.Plaque;
import org.transport.service.PlaqueService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PlaqueAPI {

    private final PlaqueService plaqueService;

    public PlaqueAPI(PlaqueService plaqueService) {
        this.plaqueService = plaqueService;
    }

    @PostMapping(path = "/transport/plaque/add")
    public Long addPlaque(@RequestBody Plaque plaque, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        plaqueService.insert(plaque, userId);
        return plaque.getId();
    }

    @PutMapping(path = "/transport/plaque/edit")
    public Long editPlaque(@RequestBody Plaque plaque, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        plaqueService.update(plaque, userId);
        return plaque.getId();
    }

    @DeleteMapping(path = "/transport/plaque/remove/{id}")
    public Long removePlaque(@PathVariable Long id) {
        return (long) plaqueService.delete(id);
    }

    @GetMapping(path = "/transport/plaque/{id}")
    public Plaque getPlaque(@PathVariable Long id) {
        return plaqueService.findOne(Plaque.class, id);
    }

    @GetMapping(path = "/transport/plaque")
    public Page<PlaqueDto> listPlaque(HttpServletRequest request, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        String uuid = request.getHeader("X-UUID");
        String token = CommonUtils.getToken(request);
        return plaqueService.findAll( size, page , token,uuid);
    }
}
